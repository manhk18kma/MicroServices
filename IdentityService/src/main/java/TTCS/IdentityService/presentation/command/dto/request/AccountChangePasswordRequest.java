package TTCS.IdentityService.presentation.command.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Builder
public class AccountChangePasswordRequest {
    @NotNull @NotBlank(message = "ID must not be blank")
    String idAccount;
    @NotNull @NotBlank(message = "otp must not be blank")
    int otp;
    @NotNull @NotBlank(message = "oldPassword must not be blank")
    String oldPassword;
    @NotNull @NotBlank(message = "newPassword must not be blank")
    String newPassword;
}
