package TTCS.IdentityService.presentation.command.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AccountChangePasswordRequest {
    String idAccount;
    int otp;
    String oldPassword;
    String newPassword;
}
