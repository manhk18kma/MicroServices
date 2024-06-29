package TTCS.PostService.DTO.Post.Request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;



@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor // Thêm constructor mặc định
public class DeletePostRequest implements Serializable {

    @JsonProperty("idProfile")
    String idProfile;

    @JsonCreator
    public DeletePostRequest(@JsonProperty("idProfile") String idProfile) {
        this.idProfile = idProfile;
    }
}
