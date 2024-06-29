package TTCS.PostService.Controller.Like;

import TTCS.PostService.DTO.Like.Response.LikeResponse;
import TTCS.PostService.DTO.Like.Response.PostLikeDetail;
import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.PostLikeService;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Like Query Controller", description = "Endpoints for retrieving like information")
public class LikeQueryController {
    PostLikeService postLikeService;

    @Operation(
            summary = "Count likes of a post",
            description = "Count the number of likes for a specific post"
    )
    @GetMapping("/count-like")
    public ResponseData<LikeResponse> countLike(
            @Parameter(description = "ID of the post")
            @RequestParam(required = true) String idPost
    ) throws ExecutionException, InterruptedException {
        LikeResponse response = postLikeService.countLike(idPost);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Number of likes for the post",
                new Date(),
                response);
    }

    @Operation(
            summary = "Get detailed likes of a post",
            description = "Retrieve detailed information about likes for a specific post"
    )
    @GetMapping("/like-detail")
    public ResponseData<PageResponse<List<PostLikeDetail>>> postLikeDetail(
            @Parameter(description = "ID of the post")
            @RequestParam(required = true) String idPost,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        PageResponse<List<PostLikeDetail>> response = postLikeService.likeDetailOfPost(idPost, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Detailed likes for the post",
                new Date(),
                response);
    }
}
