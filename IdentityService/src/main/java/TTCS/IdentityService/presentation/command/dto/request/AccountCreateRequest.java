package TTCS.IdentityService.presentation.command.dto.request;

import TTCS.IdentityService.domain.enumType.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Date;
@Getter
public class AccountCreateRequest {
    @NotBlank(message = "firstName must be not blank")
    String username;
    String password;
    String email;
    String fullName;
    String urlProfilePicture;
    String biography;
    Gender gender;
    Date dateOfBirth;
}
