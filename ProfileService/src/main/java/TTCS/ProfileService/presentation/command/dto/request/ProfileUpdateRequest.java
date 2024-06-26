package TTCS.ProfileService.presentation.command.dto.request;


import KMA.TTCS.CommonService.enumType.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProfileUpdateRequest {
    @NotBlank(message = "idProfile must not be blank")
    String idProfile;

    @NotBlank(message = "fullName must not be blank")
    @Size(max = 100, message = "fullName must be less than or equal to 100 characters")
    String fullName;

    String urlProfilePicture;

    @Size(max = 500, message = "biography must be less than or equal to 500 characters")
    String biography;

    @NotNull(message = "gender must not be null")
    Gender gender;
}
