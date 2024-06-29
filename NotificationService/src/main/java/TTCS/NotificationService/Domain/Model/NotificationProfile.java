package TTCS.NotificationService.Domain.Model;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "NotificationProfile")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationProfile {
    @Id
    private String notificationId;
    private String profileSenderId;
    private String profileReceiverId;
    private NotificationType notificationType;
    private String idTarget;
    private Date timestamp;
    private String message;
    private Boolean isChecked;
    private String notificationIdToRemove;


}
