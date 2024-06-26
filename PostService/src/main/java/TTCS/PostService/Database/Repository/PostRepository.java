package TTCS.PostService.Database.Repository;

import TTCS.PostService.Entity.Image;
import TTCS.PostService.Entity.Post;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT p FROM Post p WHERE p.idProfile IN :idProfiles ORDER BY p.updateAt DESC")
    Page<Post> findByIdProfileInOrderByUpdateAtDesc(@Param("idProfiles") List<String> idProfiles, Pageable pageable);

}
