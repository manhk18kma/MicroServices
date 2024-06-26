package TTCS.IdentityService.presentation.command.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountActiveRequest implements Serializable {
    @NotBlank(message = "otp must not be blank")
    @NotNull(message = "otp must not be null")
    @Size(min = 8, message = "otp must be at least 5 characters long")
    int otp;
}
