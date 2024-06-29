package TTCS.PostService.DTO.Comment.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor // Thêm constructor mặc định
public class DeleteCommentRequest implements Serializable {

    @JsonProperty("idProfile")
    String idProfile;

    @JsonCreator
    public DeleteCommentRequest(@JsonProperty("idProfile") String idProfile) {
        this.idProfile = idProfile;
    }
}
