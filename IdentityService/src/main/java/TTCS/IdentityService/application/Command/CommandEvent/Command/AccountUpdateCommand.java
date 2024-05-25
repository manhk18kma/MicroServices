package TTCS.IdentityService.application.Command.CommandEvent.Command;

import TTCS.IdentityService.domain.enumType.Gender;
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
public class AccountUpdateCommand {
    @TargetAggregateIdentifier
    String idAccount;
    String username;
    String password;
    String email;
    String fullName;
    String urlProfilePicture;
    String biography;
    Gender gender;
    Date dateOfBirth;
    Date executeAt;


}
