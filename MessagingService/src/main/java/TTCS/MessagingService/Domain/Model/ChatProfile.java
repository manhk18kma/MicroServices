package TTCS.MessagingService.Domain.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatProfile {
     @Id
      String idChatProfile;
     String idProfile;
     Status status;
     Map<String, Date> chatRoomLastUsed = new HashMap<>();
    Map<String, Boolean> chatRoomChecked = new HashMap<>();


}
