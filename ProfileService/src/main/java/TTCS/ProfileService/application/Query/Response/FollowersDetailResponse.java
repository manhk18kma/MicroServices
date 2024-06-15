package TTCS.ProfileService.application.Query.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FollowersDetailResponse {
    String idFollow;
    String idProfileFollower;
    String fullNameFollower;
    String urlProfilePictureFollower;
    Date since;
}
