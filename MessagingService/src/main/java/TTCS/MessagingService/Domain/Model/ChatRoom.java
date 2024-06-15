package TTCS.MessagingService.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatRoom {
    @Id
    private String idChatRoom;
    private String chatRoomName;
    private String urlImageChatRoom;
//    private Set<String> idMessages = new HashSet<>();
    private Set<String>  idChatProfiles = new HashSet<>();

}