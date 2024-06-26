package TTCS.PostService.DTO.Comment.Response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CommentResponse {
    String idComment;
    Date updateAt;
    String content;
    String idPost;
    String idProfile;
}
