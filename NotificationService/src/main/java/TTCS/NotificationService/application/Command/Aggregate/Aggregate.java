package TTCS.NotificationService.application.Command.Aggregate;

import KMA.TTCS.CommonService.command.AccountProfileCommand.SendEmailOTPCommand;
import KMA.TTCS.CommonService.event.AccountProfile.OTPEmailSendFailedEvent;
import KMA.TTCS.CommonService.event.AccountProfile.OTPEmailSentEvent;
import TTCS.NotificationService.infrastructure.persistence.Service.EmailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

import java.util.Date;


@Slf4j
@org.axonframework.spring.stereotype.Aggregate
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Aggregate {
    @AggregateIdentifier
    String idOTP;
    String idAccount;
    String idProfile;
    String otp;
    String email;
    boolean status;
    Date executeAt;
    Date expiredAt;



    public Aggregate() {
    }
    @CommandHandler
    public Aggregate(SendEmailOTPCommand sendEmailOTPCommand,
                     EmailService emailService) {
        OTPEmailSentEvent event = new OTPEmailSentEvent();
        event.setEmail(sendEmailOTPCommand.getEmail());
        event.setIdAccount(sendEmailOTPCommand.getIdAccount());
        event.setOtp(sendEmailOTPCommand.getOtp());
        event.setIdOTP(sendEmailOTPCommand.getIdOTP());
        event.setIdProfile(sendEmailOTPCommand.getIdProfile());
        event.setExecuteAt(sendEmailOTPCommand.getExecuteAt());
        event.setExpiredAt(sendEmailOTPCommand.getExpiredAt());
        event.setStatus(true);
        try {
            emailService.sendOtpEmail(sendEmailOTPCommand.getEmail() , Integer.parseInt(sendEmailOTPCommand.getOtp()));
        }catch (Exception e){
            OTPEmailSendFailedEvent event2 = new OTPEmailSendFailedEvent();
            event2.setEmail(sendEmailOTPCommand.getEmail());
            event2.setIdAccount(sendEmailOTPCommand.getIdAccount());
            event2.setOtp(sendEmailOTPCommand.getOtp());
            event2.setIdOTP(sendEmailOTPCommand.getIdOTP());
            event2.setIdProfile(sendEmailOTPCommand.getIdProfile());
            event2.setExecuteAt(sendEmailOTPCommand.getExecuteAt());
            event2.setStatus(false);
            AggregateLifecycle.apply(event2);
        }
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(OTPEmailSentEvent event) {
        this.idOTP = event.getIdOTP();
        this.email = event.getEmail();
        this.idAccount = event.getIdAccount();
        this.idProfile = event.getIdProfile();
        this.otp = event.getOtp();
        this.executeAt = event.getExecuteAt();
        this.expiredAt = event.getExpiredAt();
        this.status = event.isStatus();
    }
    @EventSourcingHandler
    public void on(OTPEmailSendFailedEvent event) {
        this.idOTP = event.getIdOTP();
        this.email = event.getEmail();
        this.idAccount = event.getIdAccount();
        this.idProfile = event.getIdProfile();
        this.otp = event.getOtp();
        this.executeAt = event.getExecuteAt();
        this.status = event.isStatus();
    }






}
