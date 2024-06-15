package TTCS.MessagingService.Presentation.DTO.Response;

import TTCS.MessagingService.Domain.Model.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class FriendsResponse {
    String idProfile;
    String idChatProfile;
    String fullName;
    String urlProfilePicture;
    Status status;


}
