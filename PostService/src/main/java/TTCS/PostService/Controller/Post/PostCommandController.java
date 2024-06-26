package TTCS.PostService.Controller.Post;

import TTCS.PostService.DTO.Post.Request.CreatePostRequest;
import TTCS.PostService.DTO.Post.Request.DeletePostRequest;
import TTCS.PostService.DTO.Post.Request.UpdatePostRequest;
import TTCS.PostService.DTO.Post.Response.PostResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Repository.ImageRepository;
import TTCS.PostService.Database.Repository.PostRepository;
import TTCS.PostService.Database.Service.PostService;
import TTCS.PostService.Database.Service.UploadImageServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class PostCommandController {
    PostService postService;


    PostRepository postRepository;
    ImageRepository imageRepository;
    UploadImageServiceImpl uploadImageService;



    @PostMapping
    public ResponseData<PostResponse> createPost(
            @RequestBody @Valid CreatePostRequest createPostRequest
    ) throws ExecutionException, InterruptedException {
        PostResponse result = postService.createPost(createPostRequest);
        return ResponseData.<PostResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Post created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @PutMapping("/{idPost}")
    public ResponseData<PostResponse> updatePost(
            @PathVariable String idPost,
            @RequestBody @Valid UpdatePostRequest updatePostRequest
    ) throws ExecutionException, InterruptedException {
        PostResponse result = postService.updatePost(updatePostRequest , idPost);
        return ResponseData.<PostResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Post updated successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @DeleteMapping("/{idPost}")
    public ResponseData<String> deletePost(
            @PathVariable String idPost ,
            @RequestBody @Valid DeletePostRequest deletePostRequest
            ) throws ExecutionException, InterruptedException {
        String result = postService.deletePost(idPost);
        return ResponseData.<String>builder()
                .status(HttpStatus.CREATED.value())
                .message("Post created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }



}
