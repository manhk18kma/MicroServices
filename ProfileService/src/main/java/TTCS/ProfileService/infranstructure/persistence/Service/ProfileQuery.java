package TTCS.ProfileService.infranstructure.persistence.Service;
import TTCS.ProfileService.application.Exception.AppException.AppErrorCode;
import TTCS.ProfileService.application.Exception.AppException.AppException;
import TTCS.ProfileService.application.Query.Response.FollowersDetailResponse;
import TTCS.ProfileService.application.Query.Response.FollowingDetailResponse;
import TTCS.ProfileService.application.Query.Response.FriendDetailResponse;
import TTCS.ProfileService.application.Query.Response.ProfileDetailResponse;
import TTCS.ProfileService.domain.model.Follow;
import TTCS.ProfileService.domain.model.Friend;
import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.infranstructure.persistence.FollowRepository;
import TTCS.ProfileService.infranstructure.persistence.FriendRepository;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import TTCS.ProfileService.presentation.query.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProfileQuery {
    final ProfileRepository profileRepository;
    final FriendRepository friendRepository;
     final FollowRepository followRepository;

    public PageResponse<List<FriendDetailResponse>> getAllFriends(String idProfile , int pageNo , int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Friend> page = friendRepository.findByIdProfile1OrIdProfile2(
                idProfile,
                idProfile,
                pageable
        );

        List<FriendDetailResponse> friendDetailResponses = page.getContent().stream().map(friend -> {
            String idProfileTarget = friend.getIdProfile1().equals(idProfile) ?
                    friend.getIdProfile2() : friend.getIdProfile1();
            Profile profile = profileRepository.findById(idProfileTarget).get();
            return FriendDetailResponse.builder()
                    .idFriend(friend.getIdFriend())
                    .idProfileTarget(idProfileTarget)
                    .fullNameProfileTarget(profile.getFullName())
                    .urlProfilePicture(profile.getUrlProfilePicture())
                    .since(friend.getSince())
                    .build();
        }).collect(Collectors.toList());

        return PageResponse.<List<FriendDetailResponse>>builder()
                .size(pageSize)
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(pageNo)
                .items(friendDetailResponses)
                .build();
    }

    public PageResponse<List<FollowingDetailResponse>> getAllFollowings(String idProfile, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Follow> page = followRepository.findByIdProfileFollower(idProfile, pageable);

        List<FollowingDetailResponse> followingDetailResponses = page.getContent().stream().map(follow -> {
            Profile profile = profileRepository.findById(follow.getIdProfileTarget()).orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

            return FollowingDetailResponse.builder()
                    .idFollow(follow.getIdFollow())
                    .idProfileFollowing(profile.getIdProfile())
                    .fullNameProfileFollowing(profile.getFullName())
                    .urlProfilePicture(profile.getUrlProfilePicture())
                    .since(follow.getSince())
                    .build();
        }).collect(Collectors.toList());

        return PageResponse.<List<FollowingDetailResponse>>builder()
                .size(pageSize)
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(pageNo)
                .items(followingDetailResponses)
                .build();
    }

    public PageResponse<List<FollowersDetailResponse>> getAllFollowers(String idProfile, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Follow> page = followRepository.findByIdProfileTarget(idProfile, pageable);

        List<FollowersDetailResponse> followersDetailResponses = page.getContent().stream().map(follow -> {
            Profile profile = profileRepository.findById(follow.getIdProfileFollower()).orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

            return FollowersDetailResponse.builder()
                    .idFollow(follow.getIdFollow())
                    .idProfileFollower(profile.getIdProfile())
                    .fullNameFollower(profile.getFullName())
                    .urlProfilePictureFollower(profile.getUrlProfilePicture())
                    .since(follow.getSince())
                    .build();
        }).collect(Collectors.toList());

        return PageResponse.<List<FollowersDetailResponse>>builder()
                .size(pageSize)
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(pageNo)
                .items(followersDetailResponses)
                .build();
    }

    public ProfileDetailResponse getById(String id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));
        ProfileDetailResponse profileDetailResponse = new ProfileDetailResponse();
        BeanUtils.copyProperties(profile , profileDetailResponse);
        return profileDetailResponse;

    }


//    public List<Profile> handle(ProfileQueryGetAllFollowings queryGetAllFollowings) {
//        Profile profile = profileRepository.findById(queryGetAllFollowings.getIdProfile()).orElseThrow(
//                ()->new AppException(AppErrorCode.ACCOUNT_NOT_EXISTED)
//        );
//        int pageNo = queryGetAllFollowings.getPageNo();
//        int pageSize = queryGetAllFollowings.getPageSize();
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<Follow> page = followRepository.findByIdProfileFollower(
//                queryGetAllFollowings.getIdProfile(),
//                pageable
//        );
//        List<Profile> profiles = page.getContent().stream().map(follow -> {
//            String idProfileTarget = follow.getIdProfileTarget();
//            return profileRepository.findById(idProfileTarget).get();
//        }).collect(Collectors.toList());
//        return profiles;
//    }
//
//
//    public List<Profile> handle(ProfileQueryGetAllFollowers queryGetAllFollowers) {
//        Profile profile = profileRepository.findById(queryGetAllFollowers.getIdProfile()).orElseThrow(
//                ()->new AppException(AppErrorCode.ACCOUNT_NOT_EXISTED)
//        );
//        int pageNo = queryGetAllFollowers.getPageNo();
//        int pageSize = queryGetAllFollowers.getPageSize();
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<Follow> page = followRepository.findByIdProfileTarget(
//                queryGetAllFollowers.getIdProfile(),
//                pageable
//        );
//        List<Profile> profiles = page.getContent().stream().map(follow -> {
//            String idProfileTarget = follow.getIdProfileFollower();
//            return profileRepository.findById(idProfileTarget).get();
//        }).collect(Collectors.toList());
//        return profiles;    }

}
