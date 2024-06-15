package TTCS.ProfileService.presentation.command.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Setter
public class FriendRemoveRequest {
    String idProfile1;
    String idProfile2;


}
