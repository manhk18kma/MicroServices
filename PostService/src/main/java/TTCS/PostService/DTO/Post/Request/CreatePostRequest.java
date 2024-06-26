package TTCS.PostService.DTO.Post.Request;

import TTCS.PostService.Entity.Comment;
import TTCS.PostService.Entity.Image;
import TTCS.PostService.Entity.PostLike;
import jakarta.persistence.OneToMany;
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
public class CreatePostRequest {
    String caption;
    List<String> base64;
    String idProfile;
}
