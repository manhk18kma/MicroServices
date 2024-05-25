package TTCS.ProfileService.application.Command.CommandEvent.Event.Friend;

import TTCS.ProfileService.domain.model.Friend;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FriendRemoveEvent {
    String idProfile;
    String idProfileTarget;
    Friend friend;
    Date executeAt;

}
