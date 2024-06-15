package TTCS.MessagingService.Presentation.DTO.Response;

import TTCS.MessagingService.Domain.Model.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisconnectResponse {
    String idProfile;
    Status status;
}
