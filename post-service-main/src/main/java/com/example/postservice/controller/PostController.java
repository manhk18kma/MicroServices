package com.example.postservice.controller;

import com.example.postservice.dto.ApiResponse;
import com.example.postservice.dto.request.PostCreationRequest;
import com.example.postservice.dto.response.PostResponse;
import com.example.postservice.entity.Post;
import com.example.postservice.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @GetMapping("{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable String postId) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.getPost(postId))
                .build();
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostCreationRequest request) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }
}
