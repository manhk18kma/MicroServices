package TTCS.ProfileService.presentation.command.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FriendRemoveRequest {
    String idProfile;
    String idProfileTarget;
    String idFriend;
}