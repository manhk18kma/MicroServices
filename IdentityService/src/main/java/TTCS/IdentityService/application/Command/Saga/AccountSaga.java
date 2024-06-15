package TTCS.IdentityService.application.Command.Saga;

import KMA.TTCS.CommonService.command.AccountProfileCommand.*;
import KMA.TTCS.CommonService.event.AccountProfile.*;

import TTCS.IdentityService.application.Command.Aggregate.FutureTracker;
import TTCS.IdentityService.application.Command.CommandEvent.Event.AccountChangePasswordEvent;
import TTCS.IdentityService.application.Command.CommandEvent.Event.AccountCreateEvent;
import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;
import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;
import TTCS.IdentityService.application.Exception.AppException.AppException;
import TTCS.IdentityService.application.Exception.AxonException.AxonErrorCode;
import TTCS.IdentityService.application.Exception.AxonException.AxonException;
import TTCS.IdentityService.application.Query.Query.AccountQueryGetById;

import TTCS.IdentityService.domain.model.Account;
import TTCS.IdentityService.infrastructure.persistence.service.OTPService;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.NoHandlerForCommandException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.*;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@Saga
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSaga {

    public static final Map<String, CompletableFuture<String>> sagaFutures = new ConcurrentHashMap<>();


    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;
    @Autowired
    private transient OTPService otpService;
    @Autowired
    private transient SimpMessagingTemplate simpMessagingTemplate;

    @StartSaga
    @SagaEventHandler(associationProperty = "idAccount")
    private void handle(AccountCreateEvent event) {
        AccountQueryGetById accountQueryGetById = new AccountQueryGetById(event.getIdAccount());
        try {
            SagaLifecycle.associateWith("idProfile", event.getIdProfile());
            ProfileCreateCommand command = createProfileCommandFromEvent(event);
            commandGateway.sendAndWait(command);
        } catch (NoHandlerForCommandException e) {
            AccountRollBackCommand accountRollBackCommand = new AccountRollBackCommand(event.getIdAccount());
            commandGateway.sendAndWait(accountRollBackCommand);
            CompletableFuture<OTPResponse> future = FutureTracker.futuresOTP.remove(event.getIdAccount());
            if (future != null) {
                SagaLifecycle.end();
                future.completeExceptionally( new AxonException(AxonErrorCode.PROFILE_SERVICE_IS_DEATH));
            }
        }
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "idAccount")
    private void handle(AccountGenerateOTPEvent event) {
        try {
            SagaLifecycle.associateWith("email", event.getIdAccount());
            SendEmailOTPCommand command = new SendEmailOTPCommand();
            command.setOtp(String.valueOf(event.getOtp()));
            command.setIdOTP(UUID.randomUUID().toString());
            command.setEmail(event.getEmail());
            command.setIdAccount(event.getIdAccount());
            command.setExecuteAt(new Date());
            command.setExpiredAt(event.getExpiredAt());
            commandGateway.sendAndWait(command);

            CompletableFuture<OTPResponse> future = FutureTracker.futuresOTP.remove(event.getIdAccount());
            if (future != null) {
                future.complete(OTPResponse.builder()
                                .idAccount(event.getIdAccount())
                                .email(event.getEmail())
                                .createAt(event.getCreateAt())
                                .expiredAt(event.getExpiredAt())
                        .build());
            }
        } catch (NoHandlerForCommandException e) {
            CompletableFuture<OTPResponse> future = FutureTracker.futuresOTP.remove(event.getIdAccount());
            if (future != null) {
                SagaLifecycle.end();
                future.completeExceptionally( new AxonException(AxonErrorCode.NOTIFICATION_IS_DEATH));
            }
        }
    }

    @SagaEventHandler(associationProperty = "idProfile")
    private void handle(ProfileCreateEvent event) {
        try {
            AccountQueryGetById accountQueryGetById = new AccountQueryGetById(event.getIdAccount());
            Account account = queryGateway.query(accountQueryGetById, ResponseTypes.instanceOf(Account.class)).join();
            ChatProfileCreateCommand command =  new ChatProfileCreateCommand();
            command.setIdChatProfile(account.getIdChatProfile());
            command.setIdAccount(event.getIdAccount());
            command.setEmail(account.getEmail());
            command.setIdProfile(event.getIdProfile());
            commandGateway.sendAndWait(command);
        } catch (NoHandlerForCommandException e) {
            AccountRollBackCommand accountRollBackCommand = new AccountRollBackCommand(event.getIdAccount());
            commandGateway.sendAndWait(accountRollBackCommand);
            ProfileRollBackCommand command = new ProfileRollBackCommand(event.getIdProfile());
            commandGateway.sendAndWait(command);
            CompletableFuture<OTPResponse> future = FutureTracker.futuresOTP.remove(event.getIdAccount());
            if (future != null) {
                SagaLifecycle.end();
                future.completeExceptionally( new AxonException(AxonErrorCode.MESSAGING_SERVICE_IS_DEATH));
            }
        }
    }


    @SagaEventHandler(associationProperty = "idProfile")
    private void handle(ChatProfileCreateEvent event) {
        try {
            SendEmailOTPCommand command = sendEmailOTPCommand(event);
            commandGateway.sendAndWait(command);
        } catch (NoHandlerForCommandException e) {
            AccountRollBackCommand accountRollBackCommand = new AccountRollBackCommand(event.getIdAccount());
            commandGateway.sendAndWait(accountRollBackCommand);
            ProfileRollBackCommand command = new ProfileRollBackCommand(event.getIdProfile());
            commandGateway.sendAndWait(command);
            ChatProfileRollbackCommand rollbackCommand = new ChatProfileRollbackCommand(event.getIdChatProfile());
            commandGateway.sendAndWait(rollbackCommand);
            CompletableFuture<OTPResponse> future = FutureTracker.futuresOTP.remove(event.getIdAccount());
            if (future != null) {
                SagaLifecycle.end();
                future.completeExceptionally( new AxonException(AxonErrorCode.NOTIFICATION_IS_DEATH));
            }
        }
    }



    @EndSaga
    @SagaEventHandler(associationProperty = "idAccount")
    private void handle(OTPEmailSentEvent event) {
        CompletableFuture<OTPResponse> future = FutureTracker.futuresOTP.remove(event.getIdAccount());
        if (future != null) {
            future.complete(OTPResponse.builder()
                            .idAccount(event.getIdAccount())
                    .email(event.getEmail())
                    .createAt(event.getExecuteAt())
                    .expiredAt(event.getExpiredAt())
                    .build());
        }
        SagaLifecycle.end();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "idAccount")
    private void handle(OTPEmailSendFailedEvent event) {
        AccountRollBackCommand accountRollBackCommand = new AccountRollBackCommand(event.getIdAccount());
        commandGateway.sendAndWait(accountRollBackCommand);
        ProfileRollBackCommand command = new ProfileRollBackCommand(event.getIdProfile());
        commandGateway.sendAndWait(command);
        ChatProfileRollbackCommand rollbackCommand = new ChatProfileRollbackCommand(event.getIdProfile());
        commandGateway.sendAndWait(rollbackCommand);
        SagaLifecycle.end();
        CompletableFuture<OTPResponse> future = FutureTracker.futuresOTP.remove(event.getIdAccount());
        if (future != null) {
            SagaLifecycle.end();
            future.completeExceptionally( new AxonException(AxonErrorCode.NOTIFICATION_IS_DEATH));
        }
    }

    private ProfileCreateCommand createProfileCommandFromEvent(AccountCreateEvent event) {
        ProfileCreateCommand command = new ProfileCreateCommand();
        command.setIdAccount(event.getIdAccount());
        command.setIdProfile(event.getIdProfile());
        command.setFullName(event.getFullName());
        command.setUrlProfilePicture(event.getUrlProfilePicture());
        command.setBiography(event.getBiography());
        command.setDateOfBirth(event.getDateOfBirth());

        switch(event.getGender()) {
            case MALE:
                command.setGender(KMA.TTCS.CommonService.enumType.Gender.MALE);
                break;
            case FEMALE:
                command.setGender(KMA.TTCS.CommonService.enumType.Gender.FEMALE);
                break;
            default:
                command.setGender(KMA.TTCS.CommonService.enumType.Gender.OTHER);
                break;
        }
        return command;
    }
    private SendEmailOTPCommand sendEmailOTPCommand(ChatProfileCreateEvent event) {
        long expirationTimeMillis = System.currentTimeMillis() + (60 * 1000);
        Date expirationTime = new Date(expirationTimeMillis);
            AccountQueryGetById accountQueryGetById = new AccountQueryGetById(event.getIdAccount());
            Account account = queryGateway.query(accountQueryGetById, ResponseTypes.instanceOf(Account.class)).join();
            int otp = otpService.generateOTP(account.getSecretKey());
            SendEmailOTPCommand sendEmailOTPCommand = new SendEmailOTPCommand();
        sendEmailOTPCommand.setOtp(String.valueOf(otp));
        sendEmailOTPCommand.setIdOTP(UUID.randomUUID().toString());
        sendEmailOTPCommand.setEmail(account.getEmail());
        sendEmailOTPCommand.setIdAccount(account.getIdAccount());
        sendEmailOTPCommand.setIdProfile(event.getIdProfile());
        sendEmailOTPCommand.setExecuteAt(new Date());
        sendEmailOTPCommand.setExpiredAt(expirationTime);
        return sendEmailOTPCommand;
    }
    private void socketResponse(ResponseData responseData , String idAccount){
        simpMessagingTemplate.convertAndSend("/topic/notification/"+idAccount,
                responseData
        );
    }
}
