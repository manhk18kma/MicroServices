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
public class OTPResponse {
//    int otp;
    Date createAt;
    Date expiredAt;
    String email;
}
