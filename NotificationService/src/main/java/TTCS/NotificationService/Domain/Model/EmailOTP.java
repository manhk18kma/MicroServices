package TTCS.NotificationService.Domain.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "EmailOTP")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailOTP {
    @Id
    String idOTP;
    String to;
    String otp;
    Date executeAt;
    Date expiredAt;
    String idAccount;
    boolean status;


}
