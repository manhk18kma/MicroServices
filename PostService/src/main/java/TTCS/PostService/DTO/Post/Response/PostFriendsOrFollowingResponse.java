package TTCS.PostService.DTO.Post.Response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PostFriendsOrFollowingResponse {
    String idPost;
    String caption;
    Date updateAt;
    List<String> images;
    String idProfile;
    String fullName;
    String urlAvt;
}
