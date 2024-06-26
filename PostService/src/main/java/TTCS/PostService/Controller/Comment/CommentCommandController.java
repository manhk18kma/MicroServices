package TTCS.PostService.Controller.Comment;

import TTCS.PostService.DTO.Comment.Request.CreateCommentRequest;
import TTCS.PostService.DTO.Comment.Request.DeleteCommentRequest;
import TTCS.PostService.DTO.Comment.Request.UpdateCommentRequest;
import TTCS.PostService.DTO.Comment.Response.CommentResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class CommentCommandController {
    CommentService commentService;


    @PostMapping
    public ResponseData<CommentResponse> createComment(
            @RequestBody @Valid CreateCommentRequest createCommentRequest
    ) throws ExecutionException, InterruptedException {
        CommentResponse result = commentService.createComment(createCommentRequest);
        return ResponseData.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @PutMapping("/{idComment}")
    public ResponseData<CommentResponse> updateComment(
            @PathVariable String idComment,
            @RequestBody @Valid UpdateCommentRequest updateCommentRequest
    ) throws ExecutionException, InterruptedException {
        CommentResponse result = commentService.updateComment(updateCommentRequest , idComment);
        return ResponseData.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @DeleteMapping("/{idComment}")
    public ResponseData<String> deleteComment(
            @PathVariable String idComment,
            @RequestBody @Valid DeleteCommentRequest deleteCommentRequest
            ) throws ExecutionException, InterruptedException {
        String result = commentService.deleteComment(idComment);
        return ResponseData.<String>builder()
                .status(HttpStatus.CREATED.value())
                .message("Post created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }
}
