package TTCS.MessagingService.Domain.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatDual {
    @Id
     String idChatDual;
//     Set<String> idMessages = new HashSet<>();
     String idChatProfile1;
    String idChatProfile2;


}
