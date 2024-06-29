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
public class FollowRemoveRequest {
//    @NotBlank(message = "idProfileFollower must not be blank")
    String idProfileFollower;

    @NotBlank(message = "idProfileTarget must not be blank")
    String idProfileTarget;
}
