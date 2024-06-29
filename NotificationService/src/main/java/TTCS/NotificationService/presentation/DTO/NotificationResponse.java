package TTCS.NotificationService.presentation.DTO;

import TTCS.NotificationService.Domain.Model.NotificationType;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class NotificationResponse {
    private String notificationId;
    private String profileSenderId;
    private String profileReceiverId;
    private String idTarget;
    private NotificationType notificationType;
    private Date timestamp;
    private String message;
    private String urlAvtSender;
    private Boolean isChecked;
}
