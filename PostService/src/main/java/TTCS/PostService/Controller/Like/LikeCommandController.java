package TTCS.PostService.Controller.Like;

import TTCS.PostService.DTO.Like.Request.PostLikeRequest;
import TTCS.PostService.DTO.Like.Request.UnLikeRequest;
import TTCS.PostService.DTO.Like.Response.LikeResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.PostLikeService;
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
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class LikeCommandController {

    PostLikeService postLikeService;

    @PostMapping
    public ResponseData<LikeResponse> like(
            @RequestBody @Valid PostLikeRequest likeRequest
    ) throws ExecutionException, InterruptedException {
        LikeResponse result = postLikeService.like(likeRequest);
        return ResponseData.<LikeResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @DeleteMapping("/{idPostLike}")
    public ResponseData<String> unLike(
            @PathVariable String idPostLike,
            @RequestBody @Valid UnLikeRequest unLikeRequest
            ) throws ExecutionException, InterruptedException {
        String result = postLikeService.unLike(idPostLike);
        return ResponseData.<String>builder()
                .status(HttpStatus.CREATED.value())
                .message("Comment created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }
}
