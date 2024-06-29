package TTCS.PostService.Controller.Comment;

import TTCS.PostService.DTO.Comment.Response.CommentResponse;
import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comment Query Controller", description = "Endpoints for retrieving comments")
public class CommentQueryController {
    CommentService commentService;

    @Operation(
            summary = "Get all comments of a post",
            description = "Retrieve all comments associated with a specific post"
    )
    @GetMapping
    public ResponseData<PageResponse> getAll(
            @Parameter(description = "ID of the post")
            @RequestParam(required = true) String idPost,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        PageResponse response = commentService.getAllCommentsOfPost(pageNo, pageSize, idPost);
        return new ResponseData<>(HttpStatus.OK.value(), "Comments retrieved successfully", new Date(), response);
    }

    @Operation(
            summary = "Get a comment by ID",
            description = "Retrieve a comment by its unique identifier"
    )
    @GetMapping("/{idComment}")
    public ResponseData<CommentResponse> getById(
            @Parameter(description = "ID of the comment")
            @PathVariable String idComment
    ) throws ExecutionException, InterruptedException {
        CommentResponse response = commentService.getCommentById(idComment);
        return new ResponseData<>(HttpStatus.OK.value(), "Comment retrieved successfully", new Date(), response);
    }
}
