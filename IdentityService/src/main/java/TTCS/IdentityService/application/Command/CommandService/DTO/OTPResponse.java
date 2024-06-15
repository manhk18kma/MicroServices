package TTCS.IdentityService.application.Command.CommandService.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OTPResponse {
//    int otp;
    String idAccount;
    Date createAt;
    Date expiredAt;
    String email;
}
