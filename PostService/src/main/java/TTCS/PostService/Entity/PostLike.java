package TTCS.PostService.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PostLike {
    @Id
    String idLike;
    Date createAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    String idProfile;

}