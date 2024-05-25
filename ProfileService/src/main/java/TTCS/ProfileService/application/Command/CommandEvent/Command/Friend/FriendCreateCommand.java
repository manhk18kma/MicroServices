package TTCS.ProfileService.application.Command.CommandEvent.Command.Friend;

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
public class FriendCreateCommand {
    @TargetAggregateIdentifier
    String idProfile1;
    String idProfile2;
    Date executeAt;
}
