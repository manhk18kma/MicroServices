package TTCS.MessagingService.Presentation.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnectRequest {
    String idProfile;
}
