package TTCS.ProfileService.application.Command.CommandEvent.Event.Follow;

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
public class FollowCreateEvent {
    Profile profileFollower;
    Profile profileTarget;
    Date executeAt;

}
