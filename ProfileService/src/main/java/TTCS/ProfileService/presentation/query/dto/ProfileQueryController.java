package TTCS.ProfileService.presentation.query.dto;

import TTCS.ProfileService.application.Query.QueryService.ProfileQueryService;
import TTCS.ProfileService.application.Query.Response.FollowersDetailResponse;
import TTCS.ProfileService.application.Query.Response.FollowingDetailResponse;
import TTCS.ProfileService.application.Query.Response.FriendDetailResponse;
import TTCS.ProfileService.application.Query.Response.ProfileDetailResponse;
import TTCS.ProfileService.infranstructure.persistence.Service.ProfileQuery;
import TTCS.ProfileService.presentation.command.dto.response.ResponseData;
import TTCS.ProfileService.presentation.query.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileQueryController {

    final ProfileQueryService profileQueryService;
    final ProfileQuery profileQuery;

//    @GetMapping("")
//    public ResponseData<PageResponse> getAll(
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize,
//            @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
//        CompletableFuture<PageResponse> response = profileQueryService.getProfileWithSortBy(pageNo, pageSize);
//        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseData<Profile> getProfileById(@PathVariable("userId")  String id) throws ExecutionException, InterruptedException {
//        CompletableFuture<Profile> profileCompletableFuture = profileQueryService.getById(id);
//        return new ResponseData<Profile>(HttpStatus.OK.value(), "accounts", profileCompletableFuture.get());
//    }


//    @GetMapping("/{idProfile}/friends")
//    public ResponseData<PageResponse> getFriends(
//            @PathVariable String idProfile,
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize
//    ) throws ExecutionException, InterruptedException {
//        CompletableFuture<PageResponse<List<FriendDetailResponse>>> future = profileQueryService.getAllFriends(idProfile, pageNo, pageSize);
//        PageResponse<List<FriendDetailResponse>> pageResponse = future.get();
//        return new ResponseData<>(HttpStatus.OK.value(), "Success", pageResponse);
//    }
//
//
//    @GetMapping("/{idProfile}/followers")
//    public ResponseData<PageResponse> getFollowers(
//            @PathVariable String idProfile,
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize
//    ) throws ExecutionException, InterruptedException {
//        CompletableFuture<PageResponse<List<FollowersDetailResponse>>> future = profileQueryService.getAllFollowers(idProfile, pageNo, pageSize);
//        PageResponse<List<FollowersDetailResponse>> pageResponse = future.get();
//        return new ResponseData<>(HttpStatus.OK.value(), "Success", pageResponse);
//    }
//
//
//    @GetMapping("/followings")
//    public ResponseData<?> getAllFollowings(
//            @RequestParam String idProfile ,
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "10") int pageSize
//    ) throws ExecutionException, InterruptedException {
//        CompletableFuture<?> future = profileQueryService.getAllFollowings(idProfile , pageNo , pageSize);
//        return new ResponseData<>(HttpStatus.OK.value(), "accounts", future.get());
//    }
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseData<PageResponse> getAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
        CompletableFuture<PageResponse> response = profileQueryService.getProfileWithSortBy(pageNo, pageSize);
        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
    }


    @GetMapping("/{userId}")
    public ResponseData<ProfileDetailResponse> getProfileById(@PathVariable("userId")  String id) throws ExecutionException, InterruptedException {
        ProfileDetailResponse response = profileQuery.getById(id);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Profile",
                new Date(),
                response);
    }



    @GetMapping("/{idProfile}/followings")
    public ResponseData<PageResponse> getAllFollowings(
            @PathVariable String idProfile,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        PageResponse<List<FollowingDetailResponse>> response = profileQuery.getAllFollowings(idProfile, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Followings",
                new Date(),
                response);
    }

    @GetMapping("/{idProfile}/followers")
    public ResponseData<PageResponse> getFollowers(
            @PathVariable String idProfile,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        PageResponse<List<FollowersDetailResponse>> response = profileQuery.getAllFollowers(idProfile, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Followers",
                new Date(),
                response);    }


    @GetMapping("/{idProfile}/friends")
    public ResponseData<PageResponse<List<FriendDetailResponse>>> getFriends(
            @PathVariable String idProfile,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ExecutionException, InterruptedException {
        PageResponse<List<FriendDetailResponse>> response = profileQuery.getAllFriends(idProfile, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Friends",
                new Date(),
                response);
    }

}
