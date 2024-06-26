package TTCS.PostService.Database.Repository;

import TTCS.PostService.Entity.Comment;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface CommentRepository extends JpaRepository<Comment , String> {
}
