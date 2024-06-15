package TTCS.MessagingService.Presentation.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRoomChatRequest {
    String idChatProfileCreate;
    String chatRoomName;
    Set<String> idChatProfileMembers;

}
