package TTCS.PostService.Controller.Comment;

import TTCS.PostService.DTO.Comment.Request.CreateCommentRequest;
import TTCS.PostService.DTO.Comment.Request.UpdateCommentRequest;
import TTCS.PostService.DTO.Comment.Response.CommentResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.CommentService;
import TTCS.PostService.Entity.Comment;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Comment Command Controller", description = "Endpoints for managing comments")
public class CommentCommandController {
    CommentService commentService;

    @Operation(
            summary = "Create a new comment",
            description = "Create a new comment based on the provided request"
    )
    @PostMapping
    public ResponseData<CommentResponse> createComment(
            @RequestBody @Valid CreateCommentRequest createCommentRequest
    ) throws ExecutionException, InterruptedException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String idProfileToken = authentication.getName();
        CommentResponse result = commentService.createComment(createCommentRequest, idProfileToken);
        return ResponseData.<CommentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Update an existing comment",
            description = "Update an existing comment identified by its ID"
    )
    @PutMapping("/{idComment}")
    public ResponseData<CommentResponse> updateComment(
            @Parameter(description = "ID of the comment to be updated")
            @PathVariable String idComment,
            @RequestBody @Valid UpdateCommentRequest updateCommentRequest
    ) throws ExecutionException, InterruptedException {
        Comment comment = commentService.prevCheck(idComment);
        CommentResponse result = commentService.updateComment(updateCommentRequest, comment);
        return ResponseData.<CommentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Comment updated successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Delete a comment",
            description = "Delete a comment identified by its ID"
    )
    @DeleteMapping("/{idComment}")
    public ResponseData<String> deleteComment(
            @Parameter(description = "ID of the comment to be deleted")
            @PathVariable String idComment
    ) throws ExecutionException, InterruptedException {
        Comment comment = commentService.prevCheck(idComment);
        commentService.deleteComment(comment);
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Comment deleted successfully")
                .timestamp(new Date())
                .build();
    }
}
