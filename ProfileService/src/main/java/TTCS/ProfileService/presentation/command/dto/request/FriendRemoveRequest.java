package TTCS.ProfileService.presentation.command.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FriendRemoveRequest {
//    @NotBlank(message = "idProfile1 must not be blank")
    String idProfile1;

    @NotBlank(message = "idProfile2 must not be blank")
    String idProfile2;
}
