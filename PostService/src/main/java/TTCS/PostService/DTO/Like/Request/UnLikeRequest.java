package TTCS.PostService.DTO.Like.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor // (Optional) If you also want a constructor with all fields
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UnLikeRequest {
    String idProfile;
}
