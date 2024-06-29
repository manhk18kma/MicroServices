package TTCS.PostService.DTO.Like.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class PostLikeDetail {
    String idPostLike;
    String idPost;
    Date createAt;
    String idProfile;
    String fullName;
    String urlAvt;
}
