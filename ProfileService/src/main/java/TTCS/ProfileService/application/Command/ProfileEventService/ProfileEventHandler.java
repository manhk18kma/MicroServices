package TTCS.ProfileService.application.Command.ProfileEventService;

import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowCreateEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendCreateEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Profile.ProfileUpdateEvent;
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
    final FriendRepository friendRepository;

//    @EventHandler
//    public void on(ProfileCreateEvent profileCreateEvent){
//        System.out.println("ProfileCreateEvent - EventHandler");
//        Profile profile = Profile.builder()
//                .idProfile(profileCreateEvent.getIdProfile())
//                .fullName(profileCreateEvent.getFullName())
//                .urlProfilePicture(profileCreateEvent.getUrlProfilePicture())
//                .biography(profileCreateEvent.getBiography())
//                .gender(profileCreateEvent.getGender())
//                .dateOfBirth(profileCreateEvent.getDateOfBirth())
//                .updateAt(new Date())
//                .idAccount(profileCreateEvent.getIdAccount())
//                        .build();
////        profileRepository.deleteAll();
//        profileRepository.save(profile);
//    }


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
        profileRepository.customQuery(
                friendCreateEvent.getFriend().getProfile2().getIdProfile(),
                friendCreateEvent.getFriend().getProfile1().getIdProfile()
        );

        Optional<Profile> optionalProfile1 = profileRepository.findById(friendCreateEvent.getFriend().getProfile1().getIdProfile());
        Optional<Profile> optionalProfile2 = profileRepository.findById(friendCreateEvent.getFriend().getProfile2().getIdProfile());

        if (optionalProfile1.isPresent() && optionalProfile2.isPresent()) {
            Profile profile1 = optionalProfile1.get();
            Profile profile2 = optionalProfile2.get();
            Friend friend = friendCreateEvent.getFriend();
            friend.setProfile1(profile1);
            friend.setProfile2(profile2);
            friend.setSince(friendCreateEvent.getExecuteAt());

            friend.setIdProfile1(profile1.getIdProfile());
            friend.setIdProfile2(profile2.getIdProfile());
            friend.setFullNameProfile1(profile1.getFullName());
            friend.setFullNameProfile2(profile2.getFullName());


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
        System.out.println(followCreateEvent.toString());
        Profile profileFollower = followCreateEvent.getProfileFollower();
        Profile profileTarget = followCreateEvent.getProfileTarget();
        profileFollower.getFollowing().add(profileTarget);
        profileRepository.save(profileFollower);
        profileRepository.save(profileTarget);
    }


    @EventHandler
    public void on(FollowRemoveEvent event) {
        profileRepository.customQuery(
                event.getProfileFollower().getIdProfile(),
                event.getProfileTarget().getIdProfile()
        );
    }

}
