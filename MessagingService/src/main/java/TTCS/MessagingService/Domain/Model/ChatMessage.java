package TTCS.MessagingService.Domain.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {
    @Id
     String idChatMessage;
     String content;
     String idChatRoomChatDual;
     String idChatProfileSender;
     Date timeStamp;
}
