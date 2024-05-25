package TTCS.ProfileService.application.Query.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FollowersDetailResponse {
    String idProfileTarget;
    String fullNameProfileTarget;
}
