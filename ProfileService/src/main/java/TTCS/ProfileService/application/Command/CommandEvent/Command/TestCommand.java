package TTCS.ProfileService.application.Command.CommandEvent.Command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TestCommand {
    @TargetAggregateIdentifier
    String id;
}
