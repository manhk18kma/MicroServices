package TTCS.PostService.DTO.Comment.Request;

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
public class UpdateCommentRequest {
    @JsonProperty("content")
    String content;


    @JsonCreator
    public UpdateCommentRequest(@JsonProperty("content") String content) {
        this.content = content;
    }
}
