package TTCS.ProfileService.application.Command.ProfileCommandService;

import KMA.TTCS.CommonService.command.AccountProfileCommand.ProfileCreateCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Follow.FollowAcceptCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Follow.FollowCreateCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Follow.FollowRemoveCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Friend.FriendCreateCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Friend.FriendRemoveCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Profile.ProfileCreateAggregate;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Profile.ProfileUpdateCommand;
import TTCS.ProfileService.application.Command.ProfileCommandService.DTO.ProfileCommandResponse;
import TTCS.ProfileService.application.Exception.AxonException.AxonErrorCode;
import TTCS.ProfileService.application.Exception.AxonException.AxonException;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import TTCS.ProfileService.presentation.command.dto.request.*;
import TTCS.ProfileService.presentation.command.dto.response.FriendCreateResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import TTCS.ProfileService.domain.model.Profile;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProfileCommandService {
    final ProfileRepository profileRepository;
    final CommandGateway commandGateway;
//    final FriendRepository friendRepository;

    public CompletableFuture<ProfileCommandResponse> updateProfile(ProfileUpdateRequest profileUpdateRequest) {
        ProfileUpdateCommand profileUpdateCommand = new ProfileUpdateCommand();
        BeanUtils.copyProperties(profileUpdateRequest , profileUpdateCommand);
        CompletableFuture future = commandGateway.send(profileUpdateCommand);
        return future
                .thenApply(result -> {
                    return new ProfileCommandResponse().builder()
                            .idProfile(profileUpdateCommand.getIdProfile())
                            .build();
                })
                .exceptionally(exception -> {
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }

    @CommandHandler
    public void createProfile(ProfileCreateCommand profileCreateCommand){
        System.out.println("CommandHandler - createProfile");
        Profile profile = Profile.builder()
                .idProfile(profileCreateCommand.getIdProfile())
                .fullName(profileCreateCommand.getFullName())
                .urlProfilePicture(profileCreateCommand.getUrlProfilePicture())
                .biography(profileCreateCommand.getBiography())
                .gender(profileCreateCommand.getGender())
                .dateOfBirth(profileCreateCommand.getDateOfBirth())
                .updateAt(new Date())
                .idAccount(profileCreateCommand.getIdAccount())
                .friendShip(new HashSet<>())
                .build();

        profileRepository.save(profile);
        ProfileCreateAggregate profileCreateAggregate = new ProfileCreateAggregate();
        BeanUtils.copyProperties(profileCreateCommand , profileCreateAggregate);
        commandGateway.send(profileCreateAggregate);
    }
    public CompletableFuture<ProfileCommandResponse> createFollow(FollowCreateRequest followCreateRequest) {
        FollowCreateCommand followCreateCommand = FollowCreateCommand.builder()
                .idProfileFollower(followCreateRequest.getIdProfileFollower())
                .idProfileTarget(followCreateRequest.getIdProfileTarget())
                .build();
        CompletableFuture future = commandGateway.send(followCreateCommand);
        return future
                .thenApply(result -> {
                    FollowAcceptCommand followAcceptCommand = FollowAcceptCommand.builder()
                            .idProfileFollower(followCreateCommand.getIdProfileFollower())
                            .idProfileTarget(followCreateRequest.getIdProfileTarget())
                            .build();
//                    commandGateway.send(followAcceptCommand);
                    return new ProfileCommandResponse().builder()
                            .idProfile(followCreateCommand.getIdProfileFollower())
                            .build();
                })
                .exceptionally(exception -> {
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }


    public CompletableFuture<ProfileCommandResponse> removeFollow(FollowRemoveRequest followRemoveRequest) {
        FollowRemoveCommand command = FollowRemoveCommand.builder()
                .idProfileFollower(followRemoveRequest.getIdProfileFollower())
                .idProfileTarget(followRemoveRequest.getIdProfileTarget())
                .build();


        CompletableFuture future = commandGateway.send(command);
        return future
                .thenApply(result -> {
                    FollowAcceptCommand followAcceptCommand = FollowAcceptCommand.builder()
                            .idProfileFollower(command.getIdProfileFollower())
                            .idProfileTarget(command.getIdProfileTarget())
                            .build();
//                    commandGateway.send(followAcceptCommand);
                    return new ProfileCommandResponse().builder()
                            .idProfile(command.getIdProfileFollower())
                            .build();
                })
                .exceptionally(exception -> {
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }
//    public CompletableFuture<ProfileCommandResponse> createFollow(FollowCreateRequest followCreateRequest) {
//        FollowCreateCommand followCreateCommand = FollowCreateCommand.builder()
//                .idProfileFollower(followCreateRequest.getIdProfileFollower())
//                .idProfileTarget(followCreateRequest.getIdProfileTarget())
//                .build();
//
//        CompletableFuture<ProfileCommandResponse> future = new CompletableFuture<>();
//        commandGateway.send(followCreateCommand)
//                .whenComplete((result, throwable) -> {
//                    if (throwable != null) {
//                        future.completeExceptionally(new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION));
//                        return;
//                    }
//
//                    FollowAcceptCommand followAcceptCommand = FollowAcceptCommand.builder()
//                            .idProfileFollower(followCreateCommand.getIdProfileFollower())
//                            .idProfileTarget(followCreateRequest.getIdProfileTarget())
//                            .build();
//
//                    commandGateway.send(followAcceptCommand)
//                            .whenComplete((result2, throwable2) -> {
//                                if (throwable2 != null) {
//                                    future.completeExceptionally(new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION));
//                                    return;
//                                }
//
//                                future.complete(new ProfileCommandResponse().builder()
//                                        .idProfile(followCreateCommand.getIdProfileFollower())
//                                        .build());
//                            });
//                });
//
//        return future;
//    }


    public CompletableFuture<FriendCreateResponse> createFriend(FriendCreateRequest friendCreateRequest) {
        FriendCreateCommand friendCreateCommand = FriendCreateCommand.builder()
                .idProfile1(friendCreateRequest.getIdProfile1())
                .idProfile2(friendCreateRequest.getIdProfile2())
                .executeAt(new Date())
                .build();
        CompletableFuture<FriendCreateResponse> future = commandGateway.send(friendCreateCommand);
        return future
                .thenApply(result -> {
                    return FriendCreateResponse.builder()
                            .idProfile1(friendCreateRequest.getIdProfile1())
                            .idProfile2(friendCreateRequest.getIdProfile2())
                            .build();
                })
                .exceptionally(exception -> {
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }


    public CompletableFuture<FriendCreateResponse> removeFriend(FriendRemoveRequest friendRemoveRequest) {

        FriendRemoveCommand friendRemoveCommand = FriendRemoveCommand.builder()
                .idProfile(friendRemoveRequest.getIdProfile())
                .idFriend(friendRemoveRequest.getIdFriend())
                .idProfileTarget(friendRemoveRequest.getIdProfileTarget())
                .executeAt(new Date())
                .build();
        CompletableFuture<FriendCreateResponse> future = commandGateway.send(friendRemoveCommand);
        return future
                .thenApply(result -> {
                    return FriendCreateResponse.builder()
                            .idProfile1("ok")
                            .idProfile2("ok")
                            .build();
                })
                .exceptionally(exception -> {
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }
}
