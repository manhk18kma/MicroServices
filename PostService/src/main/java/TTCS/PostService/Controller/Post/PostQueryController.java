package TTCS.PostService.Controller.Post;

import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.DTO.Post.Response.PostFriendsOrFollowingResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Tag(name = "Post Query Controller", description = "Endpoints for retrieving posts")
public class PostQueryController {
    PostService postService;

    @Operation(
            summary = "Get posts of friends or following",
            description = "Retrieve posts of friends or accounts followed by the current user"
    )
    @GetMapping
    public ResponseData<PageResponse<List<PostFriendsOrFollowingResponse>>> getPostFriendsOrFollowing(
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String idProfileToken = authentication.getName();
        PageResponse<List<PostFriendsOrFollowingResponse>> response = postService.getPostOfFriendsOrFollowing(idProfileToken, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Posts of friends or following",
                new Date(),
                response);
    }

    @Operation(
            summary = "Get posts of a profile",
            description = "Retrieve posts of a specific user profile"
    )
    @GetMapping("/profile")
    public ResponseData<PageResponse<List<PostFriendsOrFollowingResponse>>> getPostOfProfile(
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "ID of the profile")
            @RequestParam(required = true) String idProfile
    ) throws ExecutionException, InterruptedException {
        PageResponse<List<PostFriendsOrFollowingResponse>> response = postService.getPostOfProfile(idProfile, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Posts of profile",
                new Date(),
                response);
    }

    @Operation(
            summary = "Get a post by ID",
            description = "Retrieve a post by its unique identifier"
    )
    @GetMapping("/{idPost}")
    public ResponseData<PostFriendsOrFollowingResponse> getById(
            @Parameter(description = "ID of the post")
            @PathVariable String idPost
    ) throws ExecutionException, InterruptedException {
        PostFriendsOrFollowingResponse response = postService.getPostByID(idPost);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Post retrieved successfully",
                new Date(),
                response);
    }
}
