package TTCS.MessagingService.infrastructure.persistence.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationOnline {
    String id;
    String idProfile;
    String fullName;
    String urlProfilePicture;
}
