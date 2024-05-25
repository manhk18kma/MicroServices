package TTCS.ProfileService.application.Command.CommandEvent.Event.Friend;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FriendAcceptEvent {
    Friend friend;
    Date executeAt;
}
