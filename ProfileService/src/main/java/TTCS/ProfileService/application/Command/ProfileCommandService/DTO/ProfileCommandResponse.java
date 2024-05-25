package TTCS.ProfileService.application.Command.ProfileCommandService.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCommandResponse {
    String idProfile;
}
