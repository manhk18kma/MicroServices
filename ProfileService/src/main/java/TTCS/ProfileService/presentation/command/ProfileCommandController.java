package TTCS.ProfileService.presentation.command;

import TTCS.ProfileService.application.Command.CommandEvent.Command.TestCommand;
import TTCS.ProfileService.application.Command.ProfileCommandService.DTO.ProfileCommandResponse;
import TTCS.ProfileService.application.Command.ProfileCommandService.ProfileCommandService;
import TTCS.ProfileService.infranstructure.persistence.FriendRepository;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import TTCS.ProfileService.presentation.command.dto.request.*;
import TTCS.ProfileService.presentation.command.dto.response.FriendCreateResponse;
import TTCS.ProfileService.presentation.command.dto.response.ResponseData;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/profileCommand")
public class ProfileCommandController {

    final ProfileRepository profileRepository;
    final ProfileCommandService profileCommandService;
    final FriendRepository friendRepository;
    final CommandGateway commandGateway;

    @GetMapping("/resetDatabase")
    public String resetDatabase() {
        profileRepository.deleteAll();
        return "Reset";
    }


    @PostMapping("/updateProfile")
    public ResponseData<ProfileCommandResponse> updateProfile(@RequestBody @Valid ProfileUpdateRequest profileUpdateRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<ProfileCommandResponse> response = profileCommandService.updateProfile(profileUpdateRequest);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Profile updated successfully", response.get());
    }

    @PostMapping("/createFollow")
    public ResponseData<?> createFollow(@RequestBody @Valid FollowCreateRequest followCreateRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<ProfileCommandResponse> response = profileCommandService.createFollow(followCreateRequest);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Follow updated successfully", response.get());
    }

    @PostMapping("/removeFollow")
    public ResponseData<?> removeFollow(@RequestBody @Valid FollowRemoveRequest followRemoveRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<ProfileCommandResponse> response = profileCommandService.removeFollow(followRemoveRequest);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Follow updated successfully", response.get());
    }

//    @PostMapping("/createFriend")
//    public ResponseData<?> createFriend(@RequestBody @Valid FriendCreateRequest friendCreateRequest) throws ExecutionException, InterruptedException {
//        CompletableFuture<FriendCreateResponse> response = profileCommandService.createFriend(friendCreateRequest);
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Friend create successfully", response.get());
//    }

    @PostMapping("/removeFriend")
    public ResponseData<?> removeFriend(@RequestBody @Valid FriendRemoveRequest friendRemoveRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<FriendCreateResponse> response = profileCommandService.removeFriend(friendRemoveRequest);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Friend create successfully", response.get());
    }

    @PostMapping("/test")
    public String test(@RequestParam String id1, @RequestParam String id2) {
        TestCommand testCommand = new TestCommand();
        testCommand.setId(id1);
        commandGateway.send(testCommand);

//        Profile profile = profileRepository.findById(id1).orElse(null);
//        log.info(profile.toString());
//        Friend friend = friendRepository.findById(id2).orElse(null);
//        log.info(friend.toString());

//        System.out.println(friendRepository.findFriendByIdProfile1AndIdProfile2(id1 , id2));

        return "ok";
    }
}

