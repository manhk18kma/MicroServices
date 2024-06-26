package com.example.postservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "comments")
public class Comment {
    @Id
    String id;
    String userId;
    String postId;
    LocalDate createdAt;
    String content;
    String replyToCommentId;
}
