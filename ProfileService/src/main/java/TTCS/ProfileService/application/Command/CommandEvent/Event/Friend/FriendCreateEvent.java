package TTCS.ProfileService.application.Command.CommandEvent.Event.Friend;

import lombok.*;
import lombok.experimental.FieldDefaults;

import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

import java.util.Date;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FriendCreateEvent {
    Friend friend;
    Date executeAt;

}
