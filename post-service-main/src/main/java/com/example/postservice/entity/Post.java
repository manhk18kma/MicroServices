package com.example.postservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "posts")
public class Post {
    @Id
    String id;
    String userId;
    String caption;
    LocalDate createdAt;
    List<String> likes;
    List<String> dataBase64;
    List<String> commentId;
}
