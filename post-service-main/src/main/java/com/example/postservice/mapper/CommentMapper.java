package com.example.postservice.mapper;

import com.example.postservice.dto.request.CommentCreationRequest;
import com.example.postservice.dto.response.CommentResponse;
import com.example.postservice.entity.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public static Comment toComment(CommentCreationRequest request) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(request, comment);
        return comment;
    }

    public static CommentResponse toCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(comment, commentResponse);
        return commentResponse;
    }
}
