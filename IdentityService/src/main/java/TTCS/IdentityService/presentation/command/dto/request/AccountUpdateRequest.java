package TTCS.IdentityService.presentation.command.dto.request;

import TTCS.IdentityService.domain.enumType.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountUpdateRequest {
    String username;
    String password;
    String email;
    String fullName;
    String urlProfilePicture;
    String biography;
    Gender gender;
    Date dateOfBirth;
}
