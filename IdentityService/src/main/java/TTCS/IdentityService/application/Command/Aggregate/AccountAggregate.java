package TTCS.IdentityService.application.Command.Aggregate;

import KMA.TTCS.CommonService.command.AccountProfileCommand.AccountGenerateOTPCommand;
import KMA.TTCS.CommonService.command.AccountProfileCommand.AccountRollBackCommand;
import KMA.TTCS.CommonService.event.AccountProfile.AccountGenerateOTPEvent;
import KMA.TTCS.CommonService.event.AccountProfile.AccountRollBackEvent;
import TTCS.IdentityService.application.Command.CommandEvent.Command.*;
import TTCS.IdentityService.application.Command.CommandEvent.Event.*;
import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;
import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;

import TTCS.IdentityService.application.Exception.AppException.AppException;
import TTCS.IdentityService.domain.enumType.Gender;
import TTCS.IdentityService.domain.enumType.UserStatus;
import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.infrastructure.persistence.service.OTPService;
import TTCS.IdentityService.presentation.command.dto.request.ProcessResult;
import TTCS.IdentityService.presentation.command.dto.request.StartProcessCommand;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Date;
import java.util.concurrent.CompletableFuture;


@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    String idAccount;
    String username;
    String password;
    String email;

    UserStatus status;
    String secretKey;
    String idProfile;
    String idChatProfile;
    Date createAt;
    Date updateAt;




    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(AccountCreateCommand command) {
        AccountCreateEvent event = new AccountCreateEvent();
        BeanUtils.copyProperties(command, event);
        log.info(event.toString());
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(AccountCreateEvent event) {
        this.idAccount = event.getIdAccount();
        this.username = event.getUsername();
        this.password = event.getPassword();
        this.email = event.getEmail();
        this.status = event.getStatus();
        this.secretKey = event.getSecretKey();
        this.idProfile = event.getIdProfile();
        this.idChatProfile = event.getIdChatProfile();
        this.createAt = event.getCreateAt();
        this.updateAt = event.getExecuteAt();
    }

    @CommandHandler
    public void handle(AccountActiveCommand command, OTPService otpService) {
        if (this.status.equals(UserStatus.ACTIVE)) {
            CompletableFuture<String> future = FutureTracker.futures.remove(command.getIdAccount());
            if (future != null) {
                future.completeExceptionally( new AppException(AppErrorCode.ACCOUNT_ACTIVE));
                return;
            }
        }
        if (!otpService.verifyOTP(this.secretKey, command.getOtp())) {
            CompletableFuture<String> future = FutureTracker.futures.remove(command.getIdAccount());
            if (future != null) {
                future.completeExceptionally( new AppException(AppErrorCode.INVALID_OTP));
                return;
            }
        }
        System.out.println("end");
        AccountActiveEvent event = new AccountActiveEvent();
        BeanUtils.copyProperties(command, event);
        event.setStatus(UserStatus.ACTIVE);
        AggregateLifecycle.apply(event);
        CompletableFuture<String> future = FutureTracker.futures.remove(command.getIdAccount());
        if (future != null) {
            future.complete(String.valueOf(command.getIdAccount()));
        }
    }

    @EventSourcingHandler
    public void on(AccountActiveEvent event) {
        this.idAccount = event.getIdAccount();
        this.status = event.getStatus();
    }



    @CommandHandler
    public void handle(AccountGenerateOTPCommand command , OTPService otpService){
        int otp = otpService.generateOTP(this.secretKey);
        long expirationTimeMillis = System.currentTimeMillis() + (60 * 1000);
        Date expirationTime = new Date(expirationTimeMillis);
        AccountGenerateOTPEvent event = new  AccountGenerateOTPEvent();
        event.setIdAccount(this.idAccount);
        event.setEmail(this.email);
        event.setCreateAt(new Date());
        event.setExpiredAt(expirationTime);
        event.setOtp(otp);
        System.out.println(otp);
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(AccountGenerateOTPEvent event){
        this.idAccount = event.getIdAccount();
    }

    @CommandHandler
    public void handle(AccountChangePasswordCommand command, PasswordEncoder passwordEncoder, OTPService otpService) {
        System.out.println(command.toString());
        if (!passwordEncoder.matches(command.getOldPassword(), this.password)) {
            CompletableFuture<String> future = FutureTracker.futures.remove(command.getIdAccount());
            if (future != null) {
                future.completeExceptionally( new AppException(AppErrorCode.OLD_PASSWORD_INVALID));
            }
        }
        if (!otpService.verifyOTP(this.secretKey, command.getOtp())) {
            System.out.println("here");
            CompletableFuture<String> future = FutureTracker.futures.remove(command.getIdAccount());
            if (future != null) {
                future.completeExceptionally( new AppException(AppErrorCode.INVALID_OTP));
            }
        }
        AccountChangePasswordEvent event = new AccountChangePasswordEvent();
        BeanUtils.copyProperties(command, event);
        event.setNewPassword(passwordEncoder.encode(command.getNewPassword()));
        event.setEmail(this.email);
        AggregateLifecycle.apply(event);

        CompletableFuture<String> future = FutureTracker.futures.remove(command.getIdAccount());
        if (future != null) {
            future.complete(String.valueOf(command.getIdAccount()));
        }
    }
    @EventSourcingHandler
    public void on(AccountChangePasswordEvent event) {
        this.idAccount = event.getIdAccount();
        this.password = event.getNewPassword();
    }
    @CommandHandler
    public void handle(AccountRollBackCommand command) {
        AccountRollBackEvent event = new AccountRollBackEvent();
        event.setIdAccount(command.getIdAccount());
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(AccountRollBackEvent event) {
        this.idAccount = event.getIdAccount();
    }




//    @CommandHandler
//    public void handle(StartProcessCommand command) {
//        String processId = command.getProcessId();
//        String callbackUrl = command.getCallbackUrl() + "/identityCommand/callback";
//        new Thread(() -> {
//            try {
//                Thread.sleep(5000);
//                ProcessResult result = new ProcessResult(processId, "Completed");
//                sendCallback(callbackUrl, result);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//
//private void sendCallback(String callbackUrl, ProcessResult result) {
//    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//    HttpEntity<ProcessResult> entity = new HttpEntity<>(result, headers);
//    System.out.println(callbackUrl);
//
//    try {
//        restTemplate.postForEntity(callbackUrl, entity, String.class);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}





}
