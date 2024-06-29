package TTCS.PostService.Controller.Post;

import TTCS.PostService.DTO.Post.Request.CreatePostRequest;
import TTCS.PostService.DTO.Post.Request.UpdatePostRequest;
import TTCS.PostService.DTO.Post.Response.PostResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.PostService;
import TTCS.PostService.Entity.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Post Command Controller", description = "Endpoints for managing posts")
public class PostCommandController {
    PostService postService;

    @Operation(
            summary = "Create a new post",
            description = "Create a new post based on the provided request"
    )
    @PostMapping
    public ResponseData<PostResponse> createPost(
            @RequestBody @Valid CreatePostRequest createPostRequest
    ) throws ExecutionException, InterruptedException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String idProfileToken = authentication.getName();
        PostResponse result = postService.createPost(createPostRequest, idProfileToken);
        return ResponseData.<PostResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Post created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Update an existing post",
            description = "Update an existing post identified by its ID"
    )
    @PutMapping("/{idPost}")
    public ResponseData<PostResponse> updatePost(
            @Parameter(description = "ID of the post to be updated")
            @PathVariable String idPost,
            @RequestBody @Valid UpdatePostRequest updatePostRequest
    ) throws ExecutionException, InterruptedException {
        Post post = postService.prevCheck(idPost);
        PostResponse result = postService.updatePost(updatePostRequest, post);
        return ResponseData.<PostResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Post updated successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Delete a post",
            description = "Delete a post identified by its ID"
    )
    @DeleteMapping("/{idPost}")
    public ResponseData<String> deletePost(
            @Parameter(description = "ID of the post to be deleted")
            @PathVariable String idPost
    ) throws ExecutionException, InterruptedException {
        Post post = postService.prevCheck(idPost);
        postService.deletePost(post);
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Post deleted successfully")
                .timestamp(new Date())
                .build();
    }
}
