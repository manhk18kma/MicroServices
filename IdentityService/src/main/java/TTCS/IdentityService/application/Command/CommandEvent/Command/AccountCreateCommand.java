package TTCS.IdentityService.application.Command.CommandEvent.Command;

import TTCS.IdentityService.domain.enumType.Gender;
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
public class AccountCreateCommand {
    @TargetAggregateIdentifier
    String idAccount;
    String username;
    String password;
    String email;
    String fullName;
    String urlProfilePicture;
    String biography;
    UserStatus status;
    Gender gender;
    Date dateOfBirth;
    String secretKey;
    String idProfile;
    Date createAt;
    Date executeAt;
}
