package TTCS.IdentityService.application.Command.Aggregate;

import TTCS.IdentityService.application.Command.CommandEvent.Command.*;
import TTCS.IdentityService.application.Command.CommandEvent.Event.*;
import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;
import TTCS.IdentityService.application.Exception.AppException.AppException;
import TTCS.IdentityService.application.Exception.AxonException.AxonErrorCode;
import TTCS.IdentityService.application.Exception.AxonException.AxonException;
import TTCS.IdentityService.domain.enumType.Gender;
import TTCS.IdentityService.domain.enumType.UserStatus;
import TTCS.IdentityService.infrastructure.persistence.service.OTPService;
import io.axoniq.axonserver.grpc.ErrorMessage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Aggregate
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountAggregate {

    @AggregateIdentifier
    String idAccount;
    String username;
    String password;
    String email;

    UserStatus status;
    String secretKey;
    String idProfile;
    Date createAt;
    Date updateAt;




    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(AccountCreateCommand command) {
//        System.out.println("CommandHandler AccountCreateCommand " + command.toString());
        AccountCreateEvent event = new AccountCreateEvent();
        BeanUtils.copyProperties(command, event);
        log.info(event.toString());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountCreateEvent event) {
//        System.out.println("EventSourcingHandler AccountCreateEvent " + event.toString());
        this.idAccount = event.getIdAccount();
        this.username = event.getUsername();
        this.password = event.getPassword();
        this.email = event.getEmail();
        this.status = event.getStatus();
        this.secretKey = event.getSecretKey();
        this.idProfile = event.getIdProfile();
        this.createAt = event.getCreateAt();
        this.updateAt = event.getExecuteAt();
    }

    @CommandHandler
    public void handle(AccountActiveCommand command, OTPService otpService) {
//        System.out.println("CommandHandler AccountActiveCommand " + command.toString());
        if (this.status.equals(UserStatus.ACTIVE)) {
            throw new CommandExecutionException(AppErrorCode.ACCOUNT_ACTIVE.getMessage(), null);
        }
        if (!otpService.verifyOTP(this.secretKey, command.getOtp())) {
            throw new CommandExecutionException(AppErrorCode.INVALID_OTP.getMessage(), null);
        }
        AccountActiveEvent event = new AccountActiveEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountActiveEvent event) {
//        System.out.println("EventSourcingHandler AccountActiveEvent " + event.toString());
        this.idAccount = event.getIdAccount();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(AccountChangePasswordCommand command, PasswordEncoder passwordEncoder, OTPService otpService) {
//        System.out.println("CommandHandler AccountChangePasswordCommand " + command.toString());
        if (!passwordEncoder.matches(command.getOldPassword(), this.password)) {
            throw new CommandExecutionException(AppErrorCode.OLD_PASSWORD_INVALID.getMessage(), null);
        }
        if (!otpService.verifyOTP(this.secretKey, command.getOtp())) {
            throw new CommandExecutionException(AppErrorCode.INVALID_OTP.getMessage(), null);
        }
        AccountChangePasswordEvent event = new AccountChangePasswordEvent();
        BeanUtils.copyProperties(command, event);
        event.setNewPassword(passwordEncoder.encode(command.getNewPassword()));
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountChangePasswordEvent event) {
//        System.out.println("EventSourcingHandler AccountChangePasswordEvent " + event.toString());
        this.idAccount = event.getIdAccount();
        this.password = event.getNewPassword();
    }
}
