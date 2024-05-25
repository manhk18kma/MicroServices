package TTCS.ProfileService.presentation.query.dto;

import TTCS.ProfileService.application.Query.QueryService.ProfileQueryService;
import TTCS.ProfileService.application.Query.Response.FollowersDetailResponse;
import TTCS.ProfileService.application.Query.Response.FollowingDetailResponse;
import TTCS.ProfileService.application.Query.Response.FriendDetailResponse;
import TTCS.ProfileService.presentation.command.dto.response.ResponseData;
import TTCS.ProfileService.presentation.query.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/profileQuery")
public class ProfileQueryController {

    final ProfileQueryService profileQueryService;


    @GetMapping("/getAll")
    public ResponseData<PageResponse> getAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse> response = profileQueryService.getProfileWithSortBy(pageNo, pageSize);
        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
    }

    @GetMapping("/{userId}")
    public ResponseData<Profile> getProfileById(@PathVariable("userId")  String id) throws ExecutionException, InterruptedException {
        CompletableFuture<Profile> profileCompletableFuture = profileQueryService.getById(id);
        return new ResponseData<Profile>(HttpStatus.OK.value(), "accounts", profileCompletableFuture.get());
    }

    @GetMapping("/friends")
    public ResponseData<PageResponse> getAllFriends(
            @RequestParam String idProfile,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
            CompletableFuture<PageResponse<List<FriendDetailResponse>>> future = profileQueryService.getAllFriends(idProfile, pageNo, pageSize);
            PageResponse<List<FriendDetailResponse>> pageResponse = future.get();
            return new ResponseData<>(HttpStatus.OK.value(), "Success", pageResponse);

    }
    @GetMapping("/followers")
    public ResponseData<PageResponse> getAllFollowers(
            @RequestParam String idProfile,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse<List<FollowersDetailResponse>>> future = profileQueryService.getAllFollowers(idProfile, pageNo, pageSize);
        PageResponse<List<FollowersDetailResponse>> pageResponse = future.get();
        return new ResponseData<>(HttpStatus.OK.value(), "Success", pageResponse);

    }

//    @GetMapping("/followings")
//    public ResponseData<?> getAllFollowings(
//            @RequestParam String idProfile ,
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize
//    ) throws ExecutionException, InterruptedException {
//        CompletableFuture<?> future = profileQueryService.getAllFollowings(idProfile , pageNo , pageSize);
//        return new ResponseData<>(HttpStatus.OK.value(), "accounts", future.get());
//    }

    @GetMapping("/followings")
    public ResponseData<PageResponse> getAllFollowings(
            @RequestParam String idProfile ,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse<List<FollowingDetailResponse>>> future = profileQueryService.getAllFollowings(idProfile, pageNo, pageSize);
        PageResponse<List<FollowingDetailResponse>> pageResponse = future.get();
        return new ResponseData<>(HttpStatus.OK.value(), "Success", pageResponse);
    }

}
