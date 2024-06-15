package TTCS.IdentityService.infrastructure.persistence.service.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String idAccount;
    String idProfile;
    String idChatProfile;
    String token;
    boolean authenticated;
}
