package TTCS.PostService.DTO.Like.Request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("idPost")
    String idPost;


    @JsonCreator
    public PostLikeRequest(@JsonProperty("idPost") String idPost) {
        this.idPost = idPost;
    }
}
