package TTCS.ProfileService.domain.model;

import KMA.TTCS.CommonService.enumType.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
@Node
public class Profile {
    @Id
    String idProfile;
    String fullName;
    String urlProfilePicture;
    String biography;
    Gender gender;
    Date dateOfBirth;
    Date updateAt;
    String idAccount;
    @Relationship(type = "FOLLOWING", direction = Relationship.Direction.OUTGOING)
    Set<Profile> following = new HashSet<>();
    @Relationship(type = "FRIEND", direction = Relationship.Direction.OUTGOING)
    Set<Friend> friendShip = new HashSet<>();



}