package TTCS.ProfileService.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Date;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter @Builder @ToString
@Node
@NoArgsConstructor @AllArgsConstructor
public class Friend {
    @Id
    String idFriend;
    @TargetNode
    Profile profile1;
    String idProfile1;
    String fullNameProfile1;
    @TargetNode
    Profile profile2;
    String idProfile2;
    String fullNameProfile2;
    Date since;
}