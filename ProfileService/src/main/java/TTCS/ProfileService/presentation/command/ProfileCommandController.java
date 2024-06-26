package TTCS.ProfileService.presentation.command;

import TTCS.ProfileService.infranstructure.persistence.Service.ProfileService;
import TTCS.ProfileService.presentation.command.dto.request.*;
import TTCS.ProfileService.presentation.command.dto.response.FollowCreateResponse;
import TTCS.ProfileService.presentation.command.dto.response.ProfileResponse;
import TTCS.ProfileService.presentation.command.dto.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profile Controller", description = "APIs for handling profiles, follows, and friends")
public class ProfileCommandController {

    final ProfileService profileService;

    @Operation(
            summary = "Update Profile",
            description = "Update the profile information for a given profile ID."
    )
    @PutMapping("/{idProfile}")
    public ResponseData<ProfileResponse> updateProfile(
            @Parameter(description = "ID of the profile to update", required = true)
            @PathVariable @NotBlank(message = "ID profile cannot be blank") String idProfile,
            @RequestBody @Valid ProfileUpdateRequest profileUpdateRequest
    ) throws ExecutionException, InterruptedException {
        profileUpdateRequest.setIdProfile(idProfile);
        var result = profileService.updateProfile(profileUpdateRequest);
        return ResponseData.<ProfileResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Profile updated successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Create Follow",
            description = "Create a follow relationship from the profile."
    )
    @PostMapping("/{idProfile}/follows")
    public ResponseData<FollowCreateResponse> createFollow(
            @Parameter(description = "ID of the profile to create the follow relationship", required = true)
            @PathVariable @NotBlank(message = "ID profile cannot be blank") String idProfile,
            @RequestBody @Valid FollowCreateRequest followCreateRequest
    ) throws ExecutionException, InterruptedException {
        followCreateRequest.setIdProfileFollower(idProfile);
        var result = profileService.createFollow(followCreateRequest);
        return ResponseData.<FollowCreateResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Follow created successfully")
                .data(result)
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Remove Follow",
            description = "Remove a follow relationship from the profile."
    )
    @DeleteMapping("/{idProfile}/follows")
    public ResponseData<Void> removeFollow(
            @Parameter(description = "ID of the profile to remove the follow relationship", required = true)
            @PathVariable @NotBlank(message = "ID profile cannot be blank") String idProfile,
            @RequestBody @Valid FollowRemoveRequest followRemoveRequest
    ) throws ExecutionException, InterruptedException {
        followRemoveRequest.setIdProfileFollower(idProfile);
        profileService.removeFollow(followRemoveRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Follow removed successfully")
                .timestamp(new Date())
                .build();
    }

    @Operation(
            summary = "Remove Friend",
            description = "Remove a friend relationship from the profile."
    )
    @DeleteMapping("/{idProfile}/friends")
    public ResponseData<Void> removeFriend(
            @Parameter(description = "ID of the profile to remove the friend relationship", required = true)
            @PathVariable @NotBlank(message = "ID profile cannot be blank") String idProfile,
            @RequestBody @Valid FriendRemoveRequest friendRemoveRequest
    ) throws ExecutionException, InterruptedException {
        friendRemoveRequest.setIdProfile1(idProfile);
        profileService.removeFriend(friendRemoveRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Friend removed successfully")
                .timestamp(new Date())
                .build();
    }
}
