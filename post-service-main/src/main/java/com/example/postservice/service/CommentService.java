package com.example.postservice.service;

import com.example.postservice.dto.request.CommentCreationRequest;
import com.example.postservice.dto.response.CommentResponse;
import com.example.postservice.entity.Comment;
import com.example.postservice.entity.Post;
import com.example.postservice.mapper.CommentMapper;
import com.example.postservice.repository.CommentRepository;
import com.example.postservice.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;

    public CommentResponse createComment(CommentCreationRequest request){
        Comment comment = CommentMapper.toComment(request);
        comment = commentRepository.save(comment);
        if (comment.getReplyToCommentId() == null){
            Post post = postRepository.findById(comment.getPostId()).orElseThrow(() -> new RuntimeException("aaaa"));
            List<String> list_comment = new ArrayList<>();
            list_comment.add(comment.getId());
            post.setCommentId(list_comment);
            postRepository.save(post);
        }
        return CommentMapper.toCommentResponse(comment);
    }

    public List<CommentResponse> getAllCommentByCommentId(String commentId){
        List<Comment> list_comment = commentRepository.findCommentsByReplyToCommentIdOrderByCreatedAtAsc(commentId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("bbbb"));
        list_comment.addFirst(comment);
        return list_comment.stream().map(CommentMapper::toCommentResponse).toList();
    }
}
