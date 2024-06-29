package TTCS.NotificationService.application.Command.CommandService;

import KMA.TTCS.CommonService.command.PostNotification.RemoveNotificationCommand;
import TTCS.NotificationService.infrastructure.persistence.Repository.NotificationProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommandService {
    final NotificationProfileRepository notificationProfileRepository;



    @CommandHandler
    public void handle(RemoveNotificationCommand command){
        System.out.println("Handle RemoveNotificationCommand");
        notificationProfileRepository.deleteAllByNotificationIdToRemove(command.getNotificationIdToRemove());
    }
}
