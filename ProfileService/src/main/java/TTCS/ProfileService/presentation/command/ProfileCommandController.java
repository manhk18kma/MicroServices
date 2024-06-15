package TTCS.ProfileService.presentation.command;


import TTCS.ProfileService.infranstructure.persistence.Service.ProfileService;
import TTCS.ProfileService.presentation.command.dto.request.*;
import TTCS.ProfileService.presentation.command.dto.response.FollowCreateResponse;
import TTCS.ProfileService.presentation.command.dto.response.ProfileResponse;
import TTCS.ProfileService.presentation.command.dto.response.ResponseData;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ProfileCommandController {

    final ProfileService profileService;

//    @PostAuthorize("returnObject.username==authentication.name")
//    @PutMapping("/{idProfile}")
////    public ResponseData<ProfileCommandResponse> updateProfile(@RequestBody @Valid ProfileUpdateRequest profileUpdateRequest) throws ExecutionException, InterruptedException {
////        CompletableFuture<ProfileCommandResponse> response = profileCommandService.updateProfile(profileUpdateRequest);
////        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Profile updated successfully", response.get());
////    }
//    public ResponseData<?> updateProfile(@PathVariable String idProfile,
//            @RequestBody @Valid ProfileUpdateRequest profileUpdateRequest
//    ) throws ExecutionException, InterruptedException {
//        var result = profileService.updateProfile(profileUpdateRequest);
//        return ResponseData.<ProfileResponse>builder()
//                .status(HttpStatus.CREATED.value())
//                .message("Update profile successfully")
//                .data(result)
//                .timestamp(new Date())
//                .build();
//    }
    @PutMapping("/{idProfile}")
    public ResponseData<?> updateProfile(@PathVariable String idProfile,
                                         @RequestBody @Valid ProfileUpdateRequest profileUpdateRequest
    ) throws ExecutionException, InterruptedException {
        profileUpdateRequest.setIdProfile(idProfile);
        var result = profileService.updateProfile(profileUpdateRequest);
        return ResponseData.<ProfileResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Update profile successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }


    @PostMapping("/{idProfile}/follows")
//    public ResponseData<?> createFollow(@RequestBody @Valid FollowCreateRequest followCreateRequest) throws ExecutionException, InterruptedException {
//        CompletableFuture<ProfileCommandResponse> response = profileCommandService.createFollow(followCreateRequest);
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Follow updated successfully", response.get());
//    }
        public ResponseData<?> createFollow(
                @PathVariable String idProfile,
                @RequestBody @Valid FollowCreateRequest followCreateRequest)
            throws ExecutionException, InterruptedException {
        followCreateRequest.setIdProfileFollower(idProfile);
        var result = profileService.createFollow(followCreateRequest);
        return ResponseData.<FollowCreateResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Update profile successfully")
                .data(result)
                .timestamp(new Date())
                .build();

    }

    @DeleteMapping("/{idProfile}/follows")
    public ResponseData<?> removeFollow(
            @PathVariable String idProfile,
            @RequestBody @Valid FollowRemoveRequest followRemoveRequest)
            throws ExecutionException, InterruptedException {
//        CompletableFuture<ProfileCommandResponse> response = profileCommandService.removeFollow(followRemoveRequest);
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Follow removed successfully", response.get());
        followRemoveRequest.setIdProfileFollower(idProfile);
        profileService.removeFollow(followRemoveRequest);
        return ResponseData.<FollowCreateResponse>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Follow removed successfully")
                .timestamp(new Date())
                .build();
    }


    @DeleteMapping("/{idProfile}/friends")
    public ResponseData<?> removeFriend(
            @PathVariable String idProfile,
            @RequestBody @Valid FriendRemoveRequest friendRemoveRequest) throws ExecutionException, InterruptedException {
//        CompletableFuture<FriendCreateResponse> response = profileCommandService.removeFriend(friendRemoveRequest);
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Friend removed successfully", response.get());
          friendRemoveRequest.setIdProfile1(idProfile);
        profileService.removeFriend(friendRemoveRequest);
        return ResponseData.<FollowCreateResponse>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Friend removed successfully")
                .timestamp(new Date())
                .build();
    }
}
