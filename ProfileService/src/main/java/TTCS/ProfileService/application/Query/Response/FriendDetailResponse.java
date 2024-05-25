package TTCS.ProfileService.application.Query.Response;

import TTCS.ProfileService.domain.model.Profile;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Date;
@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FriendDetailResponse {
    String idFriend;
    String idProfileTarget;
    String fullNameProfileTarget;
    Date since;
}
