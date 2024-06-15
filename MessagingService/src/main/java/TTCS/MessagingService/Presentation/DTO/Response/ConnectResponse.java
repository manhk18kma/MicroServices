package TTCS.MessagingService.Presentation.DTO.Response;

import TTCS.MessagingService.Domain.Model.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnectResponse {
    String idProfile;
    String idChatProfile;
    String fulName;
    String urlAvtPicture;
    Status status;
}
