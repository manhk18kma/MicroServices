package TTCS.PostService.Controller.Post;

import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.DTO.Post.Response.PostFriendsOrFollowingResponse;
import TTCS.PostService.DTO.Post.Response.PostResponse;
import TTCS.PostService.DTO.ResponseData;
import TTCS.PostService.Database.Service.PostService;
import com.google.api.Page;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class PostQueryController {
    PostService postService;

    @GetMapping
    public ResponseData<PageResponse<List<PostFriendsOrFollowingResponse>>> get(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
            ) throws ExecutionException, InterruptedException {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        String idProfileToken = authentication.getName();
        PageResponse<List<PostFriendsOrFollowingResponse>> response = postService.getPostOfFriendsOrFollowing("cf0976cf-ad34-4c01-82de-adf8da4a5fa2" , pageNo , pageSize );
        return new ResponseData<>(HttpStatus.OK.value(),
                "Posts",
                new Date(),
                response);
    }

}
