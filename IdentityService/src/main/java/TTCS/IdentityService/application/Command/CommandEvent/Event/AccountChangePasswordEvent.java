package TTCS.IdentityService.application.Command.CommandEvent.Event;

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
public class AccountChangePasswordEvent {
    String idAccount;
    int otp;
    String oldPassword;
    String newPassword;
    Date executeAt;
    String email;

}
