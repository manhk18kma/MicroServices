package TTCS.MessagingService.Application.Command.CommandEvent.Command;

import TTCS.MessagingService.Domain.Model.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ConnectCommand {
    @TargetAggregateIdentifier
    String idChatProfile;
    String idProfile;
    Status status;
    String idTracker;
}
