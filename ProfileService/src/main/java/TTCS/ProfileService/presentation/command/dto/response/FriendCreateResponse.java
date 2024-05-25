package TTCS.ProfileService.presentation.command.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class FriendCreateResponse {
    String idProfile1;
    String idProfile2;
}
