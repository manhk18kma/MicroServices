package TTCS.ProfileService.application.Command.CommandEvent.Event.Profile;

import KMA.TTCS.CommonService.enumType.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateEvent {
    String idProfile;
    String fullName;
    String  urlProfilePicture;
    String  biography;
    Gender gender;
    String idAccount;
}
