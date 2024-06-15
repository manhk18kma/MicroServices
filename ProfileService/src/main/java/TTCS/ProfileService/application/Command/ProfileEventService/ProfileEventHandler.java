package TTCS.ProfileService.application.Command.ProfileEventService;

import KMA.TTCS.CommonService.event.AccountProfile.ProfileCreateEvent;
import KMA.TTCS.CommonService.event.AccountProfile.ProfileRollBackEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowCreateEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendCreateEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Profile.ProfileUpdateEvent;
import TTCS.ProfileService.domain.model.Follow;

import TTCS.ProfileService.infranstructure.persistence.FollowRepository;
import TTCS.ProfileService.infranstructure.persistence.FriendRepository;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.*;

import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEventHandler {
   final ProfileRepository profileRepository;
   final FollowRepository followRepository;
   final FriendRepository friendRepository;

    @EventHandler
    public void on(ProfileCreateEvent profileCreateEvent){
        Profile profile = Profile.builder()
                .idProfile(profileCreateEvent.getIdProfile())
                .fullName(profileCreateEvent.getFullName())
                .urlProfilePicture(profileCreateEvent.getUrlProfilePicture())
                .biography(profileCreateEvent.getBiography())
                .gender(profileCreateEvent.getGender())
                .dateOfBirth(profileCreateEvent.getDateOfBirth())
                .updateAt(new Date())
                .idAccount(profileCreateEvent.getIdAccount())
                .friendShip(new HashSet<>())
                .build();
        profileRepository.save(profile);
    }


    @EventHandler
    public void on(ProfileUpdateEvent profileUpdateEvent){
        Optional<Profile> profile = profileRepository.findById(profileUpdateEvent.getIdProfile());
        profile.get().setFullName(profileUpdateEvent.getFullName());
        profile.get().setUrlProfilePicture(profileUpdateEvent.getUrlProfilePicture());
        profile.get().setBiography(profileUpdateEvent.getBiography());
        profile.get().setGender(profile.get().getGender());
        profile.get().setUpdateAt(new Date());
        profileRepository.save(profile.get());
    }

    @EventHandler
    public void on(FriendRemoveEvent friendRemoveEvent){
       friendRepository.deleteById(friendRemoveEvent.getFriend().getIdFriend());
    }

    @EventHandler
    public void on(FriendCreateEvent friendCreateEvent){
        followRepository.deleteByIdProfileFollowerAndIdProfileTarget(
                friendCreateEvent.getFriend().getIdProfile1(),
                friendCreateEvent.getFriend().getIdProfile2()
        );
        followRepository.deleteByIdProfileFollowerAndIdProfileTarget(
                friendCreateEvent.getFriend().getIdProfile2(),
                friendCreateEvent.getFriend().getIdProfile1()
        );
        Optional<Profile> optionalProfile1 = profileRepository.findById(friendCreateEvent.getFriend().getIdProfile1());
        Optional<Profile> optionalProfile2 = profileRepository.findById(friendCreateEvent.getFriend().getIdProfile2());
        if (optionalProfile1.isPresent() && optionalProfile2.isPresent()) {
            Profile profile1 = optionalProfile1.get();
            Profile profile2 = optionalProfile2.get();
            Friend friend = friendCreateEvent.getFriend();
            friend.setSince(friendCreateEvent.getExecuteAt());
            friend.setIdProfile1(profile1.getIdProfile());
            friend.setIdProfile2(profile2.getIdProfile());


            if (profile1.getFriendShip() == null) {
                profile1.setFriendShip(new HashSet<>());
            }
            profile1.getFriendShip().add(friend);

            if (profile2.getFriendShip() == null) {
                profile2.setFriendShip(new HashSet<>());
            }
            profile2.getFriendShip().add(friend);

            profileRepository.save(profile1);
            profileRepository.save(profile2);
            friendRepository.save(friend);
        } else {
            System.err.println("Profile1 hoặc Profile2 không tồn tại.");
        }
    }


    @EventHandler
    public void on(FollowCreateEvent followCreateEvent) {
        Profile profileFollower = followCreateEvent.getProfileFollower();
        Profile profileTarget = followCreateEvent.getProfileTarget();
        Follow follow = Follow.builder()
                        .idFollow(UUID.randomUUID().toString())
                .idProfileFollower(followCreateEvent.getProfileFollower().getIdProfile())
                .idProfileTarget(followCreateEvent.getProfileTarget().getIdProfile())
                .since(new Date())
                .build();
        profileFollower.getFollowing().add(follow);
        profileTarget.getFollower().add(follow);
        followRepository.save(follow);
        profileRepository.save(profileFollower);
        profileRepository.save(profileTarget);
    }


    @EventHandler
    public void on(FollowRemoveEvent event) {
        followRepository.deleteByIdProfileFollowerAndIdProfileTarget(
                event.getProfileFollower().getIdProfile(),
                event.getProfileTarget().getIdProfile()
        );

        followRepository.deleteByIdProfileFollowerAndIdProfileTarget(
                event.getProfileTarget().getIdProfile(),
                event.getProfileFollower().getIdProfile()
        );
    }


    @EventHandler
    public void  on(ProfileRollBackEvent event){
        profileRepository.deleteById(event.getIdProfile());
    }

}
