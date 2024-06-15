package TTCS.NotificationService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatProfile {
    @Id
    private String id;
    private String name;
    private Map<String, Date> chatRoomLastUsed = new HashMap<>();

}

