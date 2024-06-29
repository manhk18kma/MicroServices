package TTCS.NotificationService.infrastructure.messaging;

import KMA.TTCS.CommonService.command.PostNotification.RemoveNotificationCommand;
import KMA.TTCS.CommonService.model.ProfileNotificationResponse;
import KMA.TTCS.CommonService.notification.NotificationInfor;
import KMA.TTCS.CommonService.query.ProfileNotificationQuery;
import TTCS.NotificationService.Domain.Model.NotificationProfile;
import TTCS.NotificationService.Domain.Model.NotificationType;
import TTCS.NotificationService.infrastructure.persistence.Repository.NotificationProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class HandleRemoveDLQ {
    final NotificationProfileRepository notificationProfileRepository;

    @KafkaListener(topics = "remove_notification_topic", id = "notification_info_remove")
    public void receiveNotificationInfoFollow(RemoveNotificationCommand removeNotificationCommand) {

        notificationProfileRepository.deleteAllByNotificationIdToRemove(removeNotificationCommand.getNotificationIdToRemove());
    }
}
