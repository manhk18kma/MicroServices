package TTCS.PostService.Controller.Like;

import TTCS.PostService.DTO.Like.Request.PostLikeRequest;
import TTCS.PostService.DTO.Like.Response.LikeResponse;
import TTCS.PostService.DTO.Like.Response.PostLikeDetail;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.PostLikeService;
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
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Like Command Controller", description = "Endpoints for managing post likes")
public class LikeCommandController {

    PostLikeService postLikeService;

    @Operation(
            summary = "Like a post",
            description = "Like a post based on the provided request"
    )
    @PostMapping
    public ResponseData<PostLikeDetail> like(
            @RequestBody @Valid PostLikeRequest likeRequest
    ) throws ExecutionException, InterruptedException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String idProfileToken = authentication.getName();
        PostLikeDetail result = postLikeService.like(likeRequest, idProfileToken);
        return ResponseData.<PostLikeDetail>builder()
                .status(HttpStatus.CREATED.value())
                .message("Post liked successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Unlike a post",
            description = "Unlike a post based on the provided request"
    )
    @DeleteMapping
    public ResponseData<String> unLike(
            @RequestBody @Valid PostLikeRequest postLikeRequest
    ) throws ExecutionException, InterruptedException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String idProfileToken = authentication.getName();
        postLikeService.unLike(postLikeRequest, idProfileToken);
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Post unliked successfully")
                .timestamp(new Date())
                .build();
    }
}
