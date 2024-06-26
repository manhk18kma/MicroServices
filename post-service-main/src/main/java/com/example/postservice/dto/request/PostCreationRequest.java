package com.example.postservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreationRequest {
    String userId;
    String caption;
    LocalDate createdAt;
    List<String> likes;
    List<String> dataBase64;
}
