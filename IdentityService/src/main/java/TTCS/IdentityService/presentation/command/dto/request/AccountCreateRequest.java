package TTCS.IdentityService.presentation.command.dto.request;

import TTCS.IdentityService.domain.enumType.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public class AccountCreateRequest {

    @NotBlank(message = "username must not be blank")
    @NotNull(message = "username must not be null")
    @Size(min = 6, max = 12, message = "username must be between 6 and 12 characters")
    String username;

    @NotBlank(message = "password must not be blank")
    @NotNull(message = "password must not be null")
    @Size(min = 8, message = "password must be at least 8 characters long")
    String password;

    @NotBlank(message = "email must not be blank")
    @NotNull(message = "email must not be null")
    @Email(message = "email should be valid")
    String email;

    @NotBlank(message = "fullName must not be blank")
    @NotNull(message = "fullName must not be null")
    @Size(max = 100, message = "fullName must not exceed 100 characters")
    String fullName;


    @NotNull(message = "gender must not be null")
    Gender gender;

    @NotNull(message = "dateOfBirth must not be null")
    @Past(message = "dateOfBirth must be a past date")
    Date dateOfBirth;
}
