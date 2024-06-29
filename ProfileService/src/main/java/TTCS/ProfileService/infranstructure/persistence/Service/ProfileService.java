package TTCS.ProfileService.infranstructure.persistence.Service;

import KMA.TTCS.CommonService.notification.NotificationInfor;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    final  UploadImageServiceImpl uploadImageService;
    final KafkaTemplate<String , Object> kafkaTemplate;

    @PreAuthorize("#profileUpdateRequest.idProfile == authentication.name and hasRole('USER')")
    public ProfileResponse updateProfile(ProfileUpdateRequest profileUpdateRequest) {
        Optional<Profile> optionalProfile = profileRepository.findById(profileUpdateRequest.getIdProfile());
        if (!optionalProfile.isPresent()) {
            throw new AppException(AppErrorCode.PROFILE_NOT_EXISTED);
        }
        String urlAvt = profileUpdateRequest.getUrlProfilePicture();
        if(profileUpdateRequest.getUrlProfilePicture().length() > 0  && !profileUpdateRequest.getUrlProfilePicture().startsWith("http")){
            System.out.println("base64");
            urlAvt = updateAvt(urlAvt , optionalProfile.get().getIdProfile());
        }




        Profile profileExisted = optionalProfile.get();
        profileExisted.setFullName(profileUpdateRequest.getFullName());
        profileExisted.setUrlProfilePicture(profileUpdateRequest.getUrlProfilePicture());
        profileExisted.setBiography(profileUpdateRequest.getBiography());
        profileExisted.setUpdateAt(new Date());
        profileExisted.setGender(profileUpdateRequest.getGender());
        profileExisted.setUrlProfilePicture(urlAvt);

        profileRepository.save(profileExisted);

        return ProfileResponse.builder()
                .idProfile(profileExisted.getIdProfile())
                .build();
    }


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
        String message = "nameTarget has started following you! Stay tuned for their latest updates.";
        NotificationInfor notificationInfor = buildNotificationInfor(profileFollower , profileTarget , message);
        kafkaTemplate.send("create_follow_topic" , notificationInfor);

        return FollowCreateResponse.builder()
                .idProfileFollower(profileFollower.getIdProfile())
                .idProfileTarget(profileTarget.getIdProfile())
                .build();
    }

    private FollowCreateResponse handleFollow(Profile profileFollower, Profile profileTarget, Follow existingFollow) {
        Friend friend = Friend.builder()
                .idFriend(UUID.randomUUID().toString())
                .idProfile1(profileFollower.getIdProfile())
                .idProfile2(profileTarget.getIdProfile())
                .since(new Date())
                .build();

        profileFollower.getFriendShip().add(friend);
        profileTarget.getFriendShip().add(friend);

        String message = "You and nameTarget are now friends! Stay tuned for their latest updates.";
        NotificationInfor notificationInfor = buildNotificationInfor(profileFollower , profileTarget  , message);
        kafkaTemplate.send("create_friend_topic" , notificationInfor);

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


    private NotificationInfor buildNotificationInfor(Profile profileFollower, Profile profileTarget , String message){
        NotificationInfor notificationInfor = new NotificationInfor();
        notificationInfor.setProfileSenderId(profileFollower.getIdProfile());
        notificationInfor.setProfileReceiverId(profileTarget.getIdProfile());
        notificationInfor.setIdTarget(profileFollower.getIdProfile());
        notificationInfor.setTimestamp(new Date());
        notificationInfor.setMessage(message);
        return notificationInfor;

    }



    public String updateAvt(String urlAtv , String idProfile){
        String idImg = UUID.randomUUID().toString();
        String base64 = urlAtv;
            MultipartFile multipartFileAvt = Base64ToMultipartFileConverter.convert(base64);
            String urlAvt = uploadImageService.uploadImage(multipartFileAvt, "Image_" + idImg);
            return urlAvt;
    }
}