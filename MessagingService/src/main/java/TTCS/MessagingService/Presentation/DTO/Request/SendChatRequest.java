package TTCS.MessagingService.Presentation.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendChatRequest {
     String idChat;
    String content;
    String idChatProfileSender;
    String idChatProfileReceiver;

}
