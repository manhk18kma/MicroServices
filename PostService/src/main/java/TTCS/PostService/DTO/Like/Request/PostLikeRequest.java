package TTCS.PostService.DTO.Like.Request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PostLikeRequest {
    String idPost;
    String idProfile;

}
