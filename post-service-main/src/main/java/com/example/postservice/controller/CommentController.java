package com.example.postservice.controller;

import com.example.postservice.dto.ApiResponse;
import com.example.postservice.dto.request.CommentCreationRequest;
import com.example.postservice.dto.response.CommentResponse;
import com.example.postservice.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentService commentService;

    @GetMapping("/getComment/{commentId}")
    public ApiResponse<List<CommentResponse>> getAllComment(@PathVariable String commentId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getAllCommentByCommentId(commentId))
                .build();
    }

    @PostMapping("/addComment")
    public ApiResponse<CommentResponse> createComment(@RequestBody CommentCreationRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }
}
