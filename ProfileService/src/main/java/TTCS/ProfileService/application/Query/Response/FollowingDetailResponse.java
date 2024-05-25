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
public class FollowingDetailResponse {
    String idProfileTarget;
    String fullNameProfileTarget;
}
