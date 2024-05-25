package TTCS.ProfileService.application.Query.QueryService;

import TTCS.ProfileService.application.Exception.AppException.AppErrorCode;
import TTCS.ProfileService.application.Exception.AppException.AppException;
import TTCS.ProfileService.application.Exception.AxonException.AxonErrorCode;
import TTCS.ProfileService.application.Exception.AxonException.AxonException;
import TTCS.ProfileService.application.Query.Query.*;
import TTCS.ProfileService.application.Query.Response.FollowersDetailResponse;
import TTCS.ProfileService.application.Query.Response.FollowingDetailResponse;
import TTCS.ProfileService.application.Query.Response.FriendDetailResponse;
import TTCS.ProfileService.domain.model.Friend;
import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.infranstructure.persistence.FriendRepository;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import TTCS.ProfileService.presentation.query.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileQueryService {
    final QueryGateway queryGateway;
    final ProfileRepository profileRepository;
    final FriendRepository friendRepository;
    public CompletableFuture<PageResponse> getProfileWithSortBy(int pageNo, int pageSize) {
        ProfileQueryGetAll profileQueryGetAll = new ProfileQueryGetAll(pageNo, pageSize);
        int totalElements = profileRepository.countAllBy();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        CompletableFuture future = queryGateway.query(profileQueryGetAll, ResponseTypes.multipleInstancesOf(Profile.class));
        List<Profile> list = (List<Profile>) future.join();
        List<Profile> accountDetailsResponses = list.stream().map(profile ->
                Profile.builder()
                        .idProfile(profile.getIdProfile())
                        .fullName(profile.getFullName())
                        .urlProfilePicture(profile.getUrlProfilePicture())
                        .biography(profile.getBiography())
                        .gender(profile.getGender())
                        .dateOfBirth(profile.getDateOfBirth())
                        .updateAt(profile.getUpdateAt())
                        .idAccount(profile.getIdAccount())
                        .build()).toList();
        return future.thenApply(result -> {
            return PageResponse.builder()
                    .size(pageSize)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .number(pageNo)
                    .items(accountDetailsResponses)
                    .build();
        })
                        .exceptionally(ex -> {
            throw new AxonException(AxonErrorCode.UNCATEGORIZED_EXCEPTION);
        })
                ;
    }

    public  CompletableFuture<Profile> getById(String id) {
        ProfileQueryGetById profileQueryGetById = new ProfileQueryGetById(id);
        CompletableFuture future = queryGateway.query(profileQueryGetById, ResponseTypes.instanceOf(Profile.class));
        Profile profile = (Profile) future.join();
        if(profile==null){
            throw new AppException(AppErrorCode.ACCOUNT_NOT_EXISTED);
        }
        return future.thenApply(result -> {
            return profile;
        }).exceptionally(ex -> {
            throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
        });
    }
    public CompletableFuture<PageResponse<List<FriendDetailResponse>>> getAllFriends(String id, int pageNo, int pageSize) {
        ProfileQueryGetAllFriend queryGetAllFriend = new ProfileQueryGetAllFriend(id, pageNo, pageSize);
        int totalElements = friendRepository.countAllByIdProfile1OrIdProfile2(id, id);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        List<FriendDetailResponse> friendDetailResponses = new ArrayList<>();

        return queryGateway.query(queryGetAllFriend, ResponseTypes.multipleInstancesOf(Friend.class))
                .thenApply(friends -> {
                    friends.forEach(friend -> {
                        if (friend.getIdProfile1().equals(id)) {
                            friendDetailResponses.add(
                                    FriendDetailResponse.builder()
                                            .idFriend(friend.getIdFriend())
                                            .idProfileTarget(friend.getIdProfile2())
                                            .fullNameProfileTarget(friend.getFullNameProfile2())
                                            .since(friend.getSince())
                                            .build()
                            );
                        } else {
                            friendDetailResponses.add(
                                    FriendDetailResponse.builder()
                                            .idFriend(friend.getIdFriend())
                                            .idProfileTarget(friend.getIdProfile1())
                                            .fullNameProfileTarget(friend.getFullNameProfile1())
                                            .since(friend.getSince())
                                            .build()
                            );
                        }
                    });
                    return PageResponse.<List<FriendDetailResponse>>builder()
                            .size(pageSize)
                            .totalElements(totalElements)
                            .totalPages(totalPages)
                            .number(pageNo)
                            .items(friendDetailResponses)
                            .build();
                })
                .exceptionally(ex -> {
                    log.error("Failed to retrieve friends for profile {}: {}", id, ex.getMessage());
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }
    public CompletableFuture<PageResponse<List<FollowersDetailResponse>>> getAllFollowers(String id, int pageNo, int pageSize) {
        ProfileQueryGetAllFollowers queryGetAllFollowers = new ProfileQueryGetAllFollowers(id, pageNo, pageSize);
        int totalElements = friendRepository.countAllByIdProfile1OrIdProfile2(id, id);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        List<FollowersDetailResponse> followersDetailResponses = new ArrayList<>();

        return queryGateway.query(queryGetAllFollowers, ResponseTypes.multipleInstancesOf(Profile.class))
                .thenApply(profiles -> {
                    profiles.forEach(profile -> {
                        FollowersDetailResponse followersDetailResponse = new FollowersDetailResponse();
                        followersDetailResponse.setIdProfileTarget(profile.getIdProfile());
                        followersDetailResponse.setFullNameProfileTarget(profile.getFullName());
                        followersDetailResponses.add(followersDetailResponse);
                    });
                    return PageResponse.<List<FollowersDetailResponse>>builder()
                            .size(pageSize)
                            .totalElements(totalElements)
                            .totalPages(totalPages)
                            .number(pageNo)
                            .items(followersDetailResponses)
                            .build();
                })
                .exceptionally(ex -> {
                    log.error("Failed to retrieve followers for profile {}: {}", id, ex.getMessage());
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }
    public CompletableFuture<PageResponse<List<FollowingDetailResponse>>> getAllFollowings(String id, int pageNo, int pageSize) {
        ProfileQueryGetAllFollowings queryGetAllFollowings = new ProfileQueryGetAllFollowings(id, pageNo, pageSize);
        int totalElements = friendRepository.countAllByIdProfile1OrIdProfile2(id, id);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        List<FollowingDetailResponse> followingDetailResponses = new ArrayList<>();

        return queryGateway.query(queryGetAllFollowings, ResponseTypes.multipleInstancesOf(Profile.class))
                .thenApply(profiles -> {
                    profiles.forEach(profile -> {
                        FollowingDetailResponse followingDetailResponse = new FollowingDetailResponse();
                        followingDetailResponse.setIdProfileTarget(profile.getIdProfile());
                        followingDetailResponse.setFullNameProfileTarget(profile.getFullName());
                        followingDetailResponses.add(followingDetailResponse);
                    });
                    return PageResponse.<List<FollowingDetailResponse>>builder()
                            .size(pageSize)
                            .totalElements(totalElements)
                            .totalPages(totalPages)
                            .number(pageNo)
                            .items(followingDetailResponses)
                            .build();
                })
                .exceptionally(ex -> {
                    log.error("Failed to retrieve followers for profile {}: {}", id, ex.getMessage());
                    throw new AxonException(AxonErrorCode.AGGREGATE_NOT_FOUND_EXCEPTION);
                });
    }

}
