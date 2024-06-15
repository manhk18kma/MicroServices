package TTCS.ProfileService.infranstructure.persistence.Service;

import TTCS.ProfileService.application.Exception.AppException.AppErrorCode;
import TTCS.ProfileService.application.Exception.AppException.AppException;
import TTCS.ProfileService.domain.model.Follow;
import TTCS.ProfileService.domain.model.Friend;
import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.infranstructure.persistence.FollowRepository;
import TTCS.ProfileService.infranstructure.persistence.FriendRepository;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import TTCS.ProfileService.presentation.command.dto.request.FollowCreateRequest;
import TTCS.ProfileService.presentation.command.dto.request.FollowRemoveRequest;
import TTCS.ProfileService.presentation.command.dto.request.FriendRemoveRequest;
import TTCS.ProfileService.presentation.command.dto.request.ProfileUpdateRequest;
import TTCS.ProfileService.presentation.command.dto.response.FollowCreateResponse;
import TTCS.ProfileService.presentation.command.dto.response.ProfileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProfileService {
    final ProfileRepository profileRepository;
    final FollowRepository followRepository;
    final FriendRepository friendRepository;

//    @PreAuthorize("#profileUpdateRequest.idProfile == authentication.principal.claims['idAccount']  and hasRole('USER')")
    @PreAuthorize("#profileUpdateRequest.idProfile == authentication.name and hasRole('USER')")
    public ProfileResponse updateProfile(ProfileUpdateRequest profileUpdateRequest) {
        Optional<Profile> optionalProfile = profileRepository.findById(profileUpdateRequest.getIdProfile());
        if (!optionalProfile.isPresent()) {
            throw new AppException(AppErrorCode.PROFILE_NOT_EXISTED);
        }

        Profile profileExisted = optionalProfile.get();
        profileExisted.setFullName(profileUpdateRequest.getFullName());
        profileExisted.setUrlProfilePicture(profileUpdateRequest.getUrlProfilePicture());
        profileExisted.setBiography(profileUpdateRequest.getBiography());
        profileExisted.setUpdateAt(new Date());
        profileExisted.setGender(profileUpdateRequest.getGender());

        profileRepository.save(profileExisted);

        return ProfileResponse.builder()
                .idProfile(profileExisted.getIdProfile())
                .build();
    }

//    public FollowCreateResponse createFollow(FollowCreateRequest followCreateRequest) {
//        Optional<Profile> optionalProfileFollower = profileRepository.findById(followCreateRequest.getIdProfileFollower());
//        if (!optionalProfileFollower.isPresent()) {
//            throw new AppException(AppErrorCode.PROFILE_NOT_EXISTED);
//        }
//
//        Optional<Profile> optionalProfileTarget = profileRepository.findById(followCreateRequest.getIdProfileTarget());
//        if (!optionalProfileTarget.isPresent()) {
//            throw new AppException(AppErrorCode.PROFILE_NOT_EXISTED);
//        }
//        Profile profileFollower = optionalProfileFollower.get();
//        Profile profileTarget = optionalProfileTarget.get();
//
//        Follow follow1 = followRepository.findByIdProfileFollowerAndIdProfileTarget(
//                profileFollower.getIdProfile(),
//                profileTarget.getIdProfile()
//        );
//        if(follow1!=null){
//            throw new AppException(AppErrorCode.FOLLOW_EXISTED);
//        }
//
//        Follow follow2 = followRepository.findByIdProfileFollowerAndIdProfileTarget(
//                profileTarget.getIdProfile(),
//                profileFollower.getIdProfile()
//                );
//
//        if (follow2 == null) {
//            return handleNotFollow(followCreateRequest, profileFollower, profileTarget);
//        } else {
//            return  handleFollow(followCreateRequest , profileFollower , profileTarget , follow2);
//        }
//
//
//    }
//
//    public FollowCreateResponse handleNotFollow(FollowCreateRequest followCreateRequest, Profile profileFollower, Profile profileTarget) {
//
//        Follow follow = Follow.builder()
//                .idFollow(UUID.randomUUID().toString())
//                .idProfileFollower(profileFollower.getIdProfile())
//                .idProfileTarget(profileTarget.getIdProfile())
//                .since(new Date())
//                .build();
//        profileFollower.getFollowing().add(follow);
//        profileTarget.getFollower().add(follow);
//        followRepository.save(follow);
//        profileRepository.save(profileFollower);
//        profileRepository.save(profileTarget);
//
//
//        return FollowCreateResponse.builder()
//                .idProfileFollower(profileFollower.getIdProfile())
//                .idProfileTarget(profileTarget.getIdProfile())
//                .build();
//    }
//    public FollowCreateResponse handleFollow(FollowCreateRequest followCreateRequest, Profile profileFollower, Profile profileTarget, Follow follow) {
//        followRepository.delete(follow);
//
//        Friend friend = Friend.builder()
//                .idFriend(UUID.randomUUID().toString())
//                .idProfile1(profileFollower.getIdProfile())
//                .idProfile2(profileTarget.getIdProfile())
//                .since(new Date())
//                .build();
//        profileFollower.getFriendShip().add(friend);
//        profileTarget.getFriendShip().add(friend);
//
//        friendRepository.save(friend);
//        profileRepository.save(profileFollower);
//        profileRepository.save(profileTarget);
//
//
//        return FollowCreateResponse.builder()
//                .idProfileFollower(profileFollower.getIdProfile())
//                .idProfileTarget(profileTarget.getIdProfile())
//                .build();
//    }
    @PreAuthorize("#followCreateRequest.idProfileFollower == authentication.name and hasRole('USER')")
    public FollowCreateResponse createFollow(FollowCreateRequest followCreateRequest) {
    Profile profileFollower = profileRepository.findById(followCreateRequest.getIdProfileFollower())
            .orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

    Profile profileTarget = profileRepository.findById(followCreateRequest.getIdProfileTarget())
            .orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

    Follow existingFollow = followRepository.findByIdProfileFollowerAndIdProfileTarget(
            profileFollower.getIdProfile(),
            profileTarget.getIdProfile()
    );
    Friend existingFriend = friendRepository.findFriendByIdProfile1AndIdProfile2(
            profileFollower.getIdProfile(),
            profileTarget.getIdProfile()
    );
    if (existingFriend != null) {
        throw new AppException(AppErrorCode.FRIEND_EXISTED);
    }
    existingFriend = friendRepository.findFriendByIdProfile1AndIdProfile2(
            profileTarget.getIdProfile(),
            profileFollower.getIdProfile()
            );
    if (existingFriend != null) {
        throw new AppException(AppErrorCode.FRIEND_EXISTED);
    }

    if (existingFollow != null) {
        throw new AppException(AppErrorCode.FOLLOW_EXISTED);
    }


    Follow mutualFollow = followRepository.findByIdProfileFollowerAndIdProfileTarget(
            profileTarget.getIdProfile(),
            profileFollower.getIdProfile()
    );

    if (mutualFollow == null) {
        return handleNotFollow(profileFollower, profileTarget);
    } else {
        return handleFollow(profileFollower, profileTarget, mutualFollow);
    }
}

    private FollowCreateResponse handleNotFollow(Profile profileFollower, Profile profileTarget) {
        System.out.println("1");
        Follow follow = Follow.builder()
                .idFollow(UUID.randomUUID().toString())
                .idProfileFollower(profileFollower.getIdProfile())
                .idProfileTarget(profileTarget.getIdProfile())
                .since(new Date())
                .build();

        profileFollower.getFollowing().add(follow);
        profileTarget.getFollower().add(follow);

        followRepository.save(follow);
        profileRepository.save(profileFollower);
        profileRepository.save(profileTarget);

        return FollowCreateResponse.builder()
                .idProfileFollower(profileFollower.getIdProfile())
                .idProfileTarget(profileTarget.getIdProfile())
                .build();
    }

    private FollowCreateResponse handleFollow(Profile profileFollower, Profile profileTarget, Follow existingFollow) {
        System.out.println("2");
        Friend friend = Friend.builder()
                .idFriend(UUID.randomUUID().toString())
                .idProfile1(profileFollower.getIdProfile())
                .idProfile2(profileTarget.getIdProfile())
                .since(new Date())
                .build();

        profileFollower.getFriendShip().add(friend);
        profileTarget.getFriendShip().add(friend);


        friendRepository.save(friend);
        profileRepository.save(profileFollower);
        profileRepository.save(profileTarget);
        followRepository.delete(existingFollow);


        return FollowCreateResponse.builder()
                .idProfileFollower(profileFollower.getIdProfile())
                .idProfileTarget(profileTarget.getIdProfile())
                .build();
    }

    @PreAuthorize("#followRemoveRequest.idProfileFollower == authentication.name and hasRole('USER')")
    public void removeFollow(FollowRemoveRequest followRemoveRequest) {
        Profile profileFollower = profileRepository.findById(followRemoveRequest.getIdProfileFollower())
                .orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

        Profile profileTarget = profileRepository.findById(followRemoveRequest.getIdProfileTarget())
                .orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

        Follow existingFollow = followRepository.findByIdProfileFollowerAndIdProfileTarget(
                profileFollower.getIdProfile(),
                profileTarget.getIdProfile()
        );
        if(existingFollow == null){
            throw new AppException(AppErrorCode.FOLLOW_NOT_EXISTED);
        }
        followRepository.deleteByIdProfileFollowerAndIdProfileTarget(
                profileFollower.getIdProfile(),
                profileTarget.getIdProfile()
        );
    }


    @PreAuthorize("#friendRemoveRequest.idProfile1 == authentication.name and hasRole('USER')")
    public void removeFriend(FriendRemoveRequest friendRemoveRequest) {
        Profile profileFollower = profileRepository.findById(friendRemoveRequest.getIdProfile1())
                .orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

        Profile profileTarget = profileRepository.findById(friendRemoveRequest.getIdProfile2())
                .orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_EXISTED));

        Friend existingFriend1 = friendRepository.findFriendByIdProfile1AndIdProfile2(
                profileFollower.getIdProfile(),
                profileTarget.getIdProfile()
        );

        Friend existingFriend2 = friendRepository.findFriendByIdProfile1AndIdProfile2(
                profileTarget.getIdProfile(),
                profileFollower.getIdProfile()
                );
        if(existingFriend1 == null && existingFriend2 == null){
            throw new AppException(AppErrorCode.FOLLOW_NOT_EXISTED);
        }
        String idFriend = existingFriend1 == null ? existingFriend2.getIdFriend() : existingFriend1.getIdFriend();
        friendRepository.deleteById(idFriend);
    }
}