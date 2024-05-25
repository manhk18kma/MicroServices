package TTCS.IdentityService.application.Command.CommandEvent.Command;

import TTCS.IdentityService.domain.enumType.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AccountActiveCommand {
     @TargetAggregateIdentifier
     String idAccount;
     int otp;
     Date executeAt;
     UserStatus status;
}
