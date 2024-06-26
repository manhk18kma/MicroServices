package TTCS.PostService.Database.Repository;

import TTCS.PostService.Entity.Image;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ImageRepository extends JpaRepository<Image , String> {
}
