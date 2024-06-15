package TTCS.ProfileService.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
@Node
@NoArgsConstructor @AllArgsConstructor
public class Follow {
    @Id
    String idFollow;
    String idProfileFollower;
    String idProfileTarget;
    Date since;
}
