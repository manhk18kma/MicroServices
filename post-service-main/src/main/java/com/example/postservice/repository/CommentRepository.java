package com.example.postservice.repository;

import com.example.postservice.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findCommentsByReplyToCommentIdOrderByCreatedAtAsc(String replyToCommentId);
}
