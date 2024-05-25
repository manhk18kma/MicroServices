package TTCS.IdentityService.infrastructure.persistence.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailService {
    final JavaMailSender javaMailSender;

    public void sendMessage(String from, String to, String subject, String text) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mailMessage);
    }

    public void sendOtpEmail(String toEmail, int otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ACTVN@email.com");
        message.setTo(toEmail);
        message.setSubject("ACTVN");
        message.setText("Your OTP code is: " + otpCode);
        javaMailSender.send(message);
    }
}
