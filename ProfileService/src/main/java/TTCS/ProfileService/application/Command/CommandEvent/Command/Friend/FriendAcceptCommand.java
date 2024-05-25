package TTCS.ProfileService.application.Command.CommandEvent.Command.Friend;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

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
public class FriendAcceptCommand {
    @TargetAggregateIdentifier
    String idProfile2;
    Friend friend;
    Date executeAt;
}
