package TTCS.NotificationService.presentation.DTO;

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
    private String notificationType;
    private Date timestamp;
    private String message;
    private String urlAvtSender;
    private Boolean isChecked;
}
