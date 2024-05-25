package TTCS.IdentityService.application.Command.CommandEvent.Event;

import TTCS.IdentityService.domain.enumType.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateEvent {
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
