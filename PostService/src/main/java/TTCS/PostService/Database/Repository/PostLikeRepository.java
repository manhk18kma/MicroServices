package TTCS.PostService.Database.Repository;

import TTCS.PostService.Entity.PostLike;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface PostLikeRepository extends JpaRepository<PostLike, String> {
    PostLike findByPost_IdPostAndIdProfile(String idPost , String idProfile);

    Boolean existsByPost_IdPostAndIdProfile(String idPost , String idProfile);

    int countAllByPost_IdPost(String idPost);

    Page<PostLike> findByPost_IdPost(String idPost , Pageable pageable);
}
