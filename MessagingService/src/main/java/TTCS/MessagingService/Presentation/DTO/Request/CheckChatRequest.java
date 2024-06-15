package TTCS.MessagingService.Presentation.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckChatRequest {
    String idChat;
    String idProfile;
}
