package TTCS.IdentityService.application.Command.CommandEvent.Event;

import TTCS.IdentityService.domain.enumType.Gender;
import TTCS.IdentityService.domain.enumType.UserStatus;
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
public class AccountCreateEvent {
     String idAccount;
     String username;
     String password;
     String email;
     String fullName;
     String urlProfilePicture;
     String biography;
     Gender gender;
     Date dateOfBirth;
     String secretKey;
     String idProfile;
     Date createAt;
     Date executeAt;
     UserStatus status;





}
