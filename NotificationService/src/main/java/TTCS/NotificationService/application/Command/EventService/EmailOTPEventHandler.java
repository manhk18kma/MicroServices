package TTCS.NotificationService.application.Command.EventService;

import KMA.TTCS.CommonService.event.AccountProfile.OTPEmailSendFailedEvent;
import KMA.TTCS.CommonService.event.AccountProfile.OTPEmailSentEvent;
import TTCS.NotificationService.Domain.Model.EmailOTP;
import TTCS.NotificationService.infrastructure.persistence.Repository.EmailOTPRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailOTPEventHandler {
    final EmailOTPRepository emailOTPRepository;

    @EventHandler
    public void on(OTPEmailSentEvent otpEmailSentEvent){
        EmailOTP emailOTP = EmailOTP.builder()
                .idOTP(otpEmailSentEvent.getIdOTP())
                .to(otpEmailSentEvent.getEmail())
                .otp(otpEmailSentEvent.getOtp())
                .executeAt(otpEmailSentEvent.getExecuteAt())
                .idAccount(otpEmailSentEvent.getIdAccount())
                .status(otpEmailSentEvent.isStatus())
                .expiredAt(otpEmailSentEvent.getExpiredAt())
                .build();
        emailOTPRepository.save(emailOTP);

    }

//    @EventHandler
//    public void on(OTPEmailSendFailedEvent otpEmailSendFailedEvent){
//        EmailOTP emailOTP = EmailOTP.builder()
//                .idOTP(otpEmailSendFailedEvent.getIdOTP())
//                .to(otpEmailSendFailedEvent.getEmail())
//                .otp(otpEmailSendFailedEvent.getOtp())
//                .executeAt(otpEmailSendFailedEvent.getExecuteAt())
//                .idAccount(otpEmailSendFailedEvent.getIdAccount())
//                .status(otpEmailSendFailedEvent.isStatus())
//                .build();
//        emailOTPRepository.save(emailOTP);
//    }
}
