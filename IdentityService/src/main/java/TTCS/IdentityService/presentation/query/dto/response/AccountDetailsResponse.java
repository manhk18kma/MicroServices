package TTCS.IdentityService.presentation.query.dto.response;

import TTCS.IdentityService.domain.enumType.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class AccountDetailsResponse implements Serializable {
    String id;
    String username;
    String email;
    UserStatus status;
    String idProfile;
}
