package TTCS.ProfileService.presentation.query.dto;

import TTCS.ProfileService.application.Query.QueryService.ProfileQueryService;
import TTCS.ProfileService.application.Query.Response.FollowersDetailResponse;
import TTCS.ProfileService.application.Query.Response.FollowingDetailResponse;
import TTCS.ProfileService.application.Query.Response.FriendDetailResponse;
import TTCS.ProfileService.application.Query.Response.ProfileDetailResponse;
import TTCS.ProfileService.infranstructure.persistence.Service.ProfileQuery;
import TTCS.ProfileService.presentation.command.dto.response.ResponseData;
import TTCS.ProfileService.presentation.query.dto.response.PageResponse;
import TTCS.ProfileService.presentation.query.dto.response.SearchProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profile Query Controller", description = "Endpoints for querying profiles")
public class ProfileQueryController {

    final ProfileQueryService profileQueryService;
    final ProfileQuery profileQuery;

//    @Operation(
//            summary = "Get all profiles",
//            description = "Retrieve a list of profiles with pagination and optional sorting"
//    )
//    @GetMapping("")    public ResponseData<PageResponse> getAll(
//            @Parameter(description = "Page number (default: 0)")
//            @RequestParam(defaultValue = "0") int pageNo,
//            @Parameter(description = "Page size (default: 10)")
//            @RequestParam(defaultValue = "10") int pageSize,
//            @Parameter(description = "Field to sort by (optional)")
//            @RequestParam(required = false) String sortBy) throws ExecutionException, InterruptedException {
//        CompletableFuture<PageResponse> response = profileQueryService.getProfileWithSortBy(pageNo, pageSize);
//        return new ResponseData<PageResponse>(HttpStatus.OK.value(), "accounts", response.get());
//    }


    @Operation(
            summary = "Get profile by ID",
            description = "Retrieve profile details by specifying the user ID"
    )
    @GetMapping("/{idProfile}")
    public ResponseData<ProfileDetailResponse> getProfileById(
            @Parameter(description = "User ID of the profile to retrieve", required = true)
            @PathVariable("idProfile") String idProfile) throws ExecutionException, InterruptedException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String idProfileToken = authentication.getName();
        ProfileDetailResponse response = profileQuery.getById(idProfile , idProfileToken);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Profile",
                new Date(),
                response);
    }



    @Operation(
            summary = "Get all followings of a profile",
            description = "Retrieve a paginated list of profiles that the specified profile is following"
    )
    @GetMapping("/{idProfile}/followings")
    public ResponseData<PageResponse<List<FollowingDetailResponse>>> getAllFollowings(
            @Parameter(description = "Profile ID of the follower", required = true)
            @PathVariable String idProfile,
            @Parameter(description = "Page number (default: 0)")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int pageSize) throws ExecutionException, InterruptedException {
        PageResponse<List<FollowingDetailResponse>> response = profileQuery.getAllFollowings(idProfile, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Followings",
                new Date(),
                response);
    }



    @Operation(
            summary = "Get all followers of a profile",
            description = "Retrieve a paginated list of profiles that follow the specified profile"
    )
    @GetMapping("/{idProfile}/followers")
    public ResponseData<PageResponse<List<FollowersDetailResponse>>> getFollowers(
            @Parameter(description = "Profile ID of the followed profile", required = true)
            @PathVariable String idProfile,
            @Parameter(description = "Page number (default: 0)")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int pageSize) throws ExecutionException, InterruptedException {
        PageResponse<List<FollowersDetailResponse>> response = profileQuery.getAllFollowers(idProfile, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Followers",
                new Date(),
                response);    }


    @Operation(
            summary = "Get all friends of a profile",
            description = "Retrieve a paginated list of profiles that are friends with the specified profile"
    )
    @GetMapping("/{idProfile}/friends")
    public ResponseData<PageResponse<List<FriendDetailResponse>>> getFriends(
            @Parameter(description = "Profile ID of the user", required = true)
            @PathVariable String idProfile,
            @Parameter(description = "Page number (default: 0)")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int pageSize) throws ExecutionException, InterruptedException {
        PageResponse<List<FriendDetailResponse>> response = profileQuery.getAllFriends(idProfile, pageNo, pageSize);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Friends",
                new Date(),
                response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search profiles by name", description = "Returns a paginated list of profiles matching the specified name.")
    public ResponseData<PageResponse<List<SearchProfileResponse>>> search(
            @Parameter(description = "Name to search for", required = true)
            @RequestParam String name,
            @Parameter(description = "Page number (default: 0)")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int pageSize) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String idProfile = authentication.getName();
        PageResponse<List<SearchProfileResponse>> response = profileQuery.searchByName(name, pageNo, pageSize , idProfile);
        return new ResponseData<>(HttpStatus.OK.value(),
                "Searches",
                new Date(),
                response);
    }

}




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