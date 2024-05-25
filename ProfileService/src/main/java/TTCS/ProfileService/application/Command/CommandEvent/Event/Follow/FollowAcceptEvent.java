package TTCS.ProfileService.application.Command.CommandEvent.Event.Follow;

import TTCS.ProfileService.domain.model.Profile;
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
public class FollowAcceptEvent {
    Profile profileFollower;
    Profile profileTarget;
    Date executeAt;

}
