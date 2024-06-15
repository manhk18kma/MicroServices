package TTCS.ProfileService.presentation.command.dto.request;

import KMA.TTCS.CommonService.enumType.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Setter
public class ProfileUpdateRequest {
    String idProfile;
    String fullName;
    String  urlProfilePicture;
    String  biography;
    Gender gender;
    String idAccount;
}
