package TTCS.ProfileService.infranstructure.persistence;

import TTCS.ProfileService.domain.model.Follow;
import TTCS.ProfileService.domain.model.Friend;
import TTCS.ProfileService.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {

    int countAllBy();

    Page<Profile> findByFullNameContaining(String name, Pageable pageable);

//    List<Profile> findByFollowing_IdProfileAndIdAccount(String followedProfileId, String userId);
//    Page<Profile> findAllByFollowing_IdProfile(String id , Pageable pageable );


}



