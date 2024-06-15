package TTCS.MessagingService.Presentation.DTO.Response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class ChatMessageResponse {
    String idChatMessage;
    String content;
    String idChatProfileSender;
    String fullNameSender;
    String urlAvtSender;
    Date timeStamp;
}
