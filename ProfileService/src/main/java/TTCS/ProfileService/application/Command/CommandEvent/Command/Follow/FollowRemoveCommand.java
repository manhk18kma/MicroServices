package TTCS.ProfileService.application.Command.CommandEvent.Command.Follow;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;


@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FollowRemoveCommand {
    @TargetAggregateIdentifier
    String idProfileFollower;
    String idProfileTarget;
    Date executeAt;

}
