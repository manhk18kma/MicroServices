package TTCS.IdentityService.application.Command.CommandEvent.Event;

import TTCS.IdentityService.domain.enumType.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AccountActiveEvent {
     String idAccount;
     int otp;
     Date executeAt;
     UserStatus status;
}
