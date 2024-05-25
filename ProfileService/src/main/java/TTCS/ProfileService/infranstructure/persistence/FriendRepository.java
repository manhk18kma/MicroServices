package TTCS.ProfileService.infranstructure.persistence;

import TTCS.ProfileService.domain.model.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends Neo4jRepository<Friend, String> {

    Friend findFriendByIdProfile1AndIdProfile2(String idProfile1 , String idProfile2);

    Page<Friend> findByIdProfile1OrIdProfile2(String idProfile1 , String idProfile2,  Pageable pageable );

    int countAllByIdProfile1OrIdProfile2(String id , String id2);

}
