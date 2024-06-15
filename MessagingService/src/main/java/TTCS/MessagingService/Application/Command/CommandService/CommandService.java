package TTCS.MessagingService.Application.Command.CommandService;

import KMA.TTCS.CommonService.command.IdentityMessage.ConnectChatProfileCommand;
import KMA.TTCS.CommonService.event.AccountProfile.ChatProfileCreateEvent;
import TTCS.MessagingService.Application.Command.CommandEvent.Command.ConnectCommand;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import TTCS.MessagingService.Domain.Model.Status;
import TTCS.MessagingService.infrastructure.persistence.Repository.ProfileChatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandService {

    final ProfileChatRepository profileChatRepository;
    final CommandGateway commandGateway;

}
