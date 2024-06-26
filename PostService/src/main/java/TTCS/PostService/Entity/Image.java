package TTCS.PostService.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Image {
    @Id
    String idImage;
    String urlImage;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;



}