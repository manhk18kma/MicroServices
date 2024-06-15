package TTCS.ProfileService.infranstructure.persistence;

import TTCS.ProfileService.domain.model.Follow;
import TTCS.ProfileService.domain.model.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FollowRepository extends Neo4jRepository<Follow, String> {
    void deleteByIdProfileFollowerAndIdProfileTarget(String idFollower , String idTarget);

    Page<Follow> findByIdProfileFollower(String id , Pageable pageable);

    Page<Follow> findByIdProfileTarget(String id , Pageable pageable);



    Follow findByIdProfileFollowerAndIdProfileTarget(String idFollower , String idTarget);

}
