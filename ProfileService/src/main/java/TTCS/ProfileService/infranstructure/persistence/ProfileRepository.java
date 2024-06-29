package TTCS.ProfileService.infranstructure.persistence;

import TTCS.ProfileService.domain.model.Follow;
import TTCS.ProfileService.domain.model.Friend;
import TTCS.ProfileService.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {

    int countAllBy();

    @Query(value = "MATCH (p:Profile) WHERE toLower(p.fullName) CONTAINS toLower($name) RETURN p SKIP $skip LIMIT $limit",
            countQuery = "MATCH (p:Profile) WHERE toLower(p.fullName) CONTAINS toLower($name) RETURN count(p)")
    Page<Profile> searchProfilesByName(@Param("name") String name, Pageable pageable);

}



