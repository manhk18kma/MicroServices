package TTCS.IdentityService.application.Command.CommandService.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AccountCommandResponse {
    String idAccount;
}
