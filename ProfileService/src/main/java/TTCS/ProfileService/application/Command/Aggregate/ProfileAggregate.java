package TTCS.ProfileService.application.Command.Aggregate;


import TTCS.ProfileService.application.Command.CommandEvent.Command.Follow.FollowAcceptCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Follow.FollowAcceptRemoveCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Follow.FollowCreateCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Follow.FollowRemoveCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Friend.FriendAcceptCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Friend.FriendAcceptRemoveCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Friend.FriendCreateCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Friend.FriendRemoveCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Profile.ProfileCreateAggregate;
import TTCS.ProfileService.application.Command.CommandEvent.Command.Profile.ProfileUpdateCommand;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowAcceptEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowAcceptRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowCreateEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Follow.FollowRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendAcceptEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendAcceptRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendCreateEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Friend.FriendRemoveEvent;
import TTCS.ProfileService.application.Command.CommandEvent.Event.Profile.ProfileUpdateEvent;
import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.domain.model.Friend;

import KMA.TTCS.CommonService.enumType.Gender;
import KMA.TTCS.CommonService.event.AccountProfile.ProfileCreateEvent;

import TTCS.ProfileService.application.Command.CommandEvent.Command.*;
import TTCS.ProfileService.infranstructure.persistence.FriendRepository;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Aggregate
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileAggregate {
    @AggregateIdentifier
    String idProfile;
    String fullName;
    String urlProfilePicture;
    String biography;
    Gender gender;
    Date dateOfBirth;
    String idAccount;
    Set<Profile> following= new HashSet<>();
    Set<Profile> followers= new HashSet<>();
    @AggregateMember
    Set<Friend> friendShip = new HashSet<>();
    public ProfileAggregate() {
    }
    @CommandHandler
    public ProfileAggregate(ProfileCreateAggregate profileCreateAggregate) {
        ProfileCreateEvent profileCreateEvent = new ProfileCreateEvent();
        BeanUtils.copyProperties(profileCreateAggregate , profileCreateEvent);
        AggregateLifecycle.apply(profileCreateEvent);
    }
    @EventSourcingHandler
    public void on(ProfileCreateEvent profileCreateEvent) {
        this.idProfile = profileCreateEvent.getIdProfile();
        this.fullName = profileCreateEvent.getFullName();
        this.urlProfilePicture = profileCreateEvent.getUrlProfilePicture();
        this.biography = profileCreateEvent.getBiography();
        this.gender = profileCreateEvent.getGender();
        this.dateOfBirth = profileCreateEvent.getDateOfBirth();
        this.idAccount = profileCreateEvent.getIdAccount();
        this.friendShip = new HashSet<>();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();

    }
    @CommandHandler
    public void handle(ProfileUpdateCommand command) {
        ProfileUpdateEvent event = new ProfileUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);

    }
    @EventSourcingHandler
    public void on(ProfileUpdateEvent event) {
        this.idProfile = event.getIdProfile();
        this.fullName = event.getFullName();
        this.urlProfilePicture = event.getUrlProfilePicture();
        this.biography = event.getBiography();
        this.gender = event.getGender();
    }



//    Create Follow
   @CommandHandler
   public void handle(FollowCreateCommand command, ProfileRepository profileRepository, CommandGateway commandGateway) {
    Optional<Profile> profileFollowerOpt = profileRepository.findById(command.getIdProfileFollower());
    Optional<Profile> profileTargetOpt = profileRepository.findById(command.getIdProfileTarget());

    if (profileFollowerOpt.isPresent() && profileTargetOpt.isPresent()) {
        Profile profileFollower = profileFollowerOpt.get();
        Profile profileTarget = profileTargetOpt.get();

        AtomicBoolean isFollowing = new AtomicBoolean(false);
        AtomicBoolean isFollower = new AtomicBoolean(false);

        // Kiểm tra xem profileFollower đã theo dõi profileTarget hay chưa
        Iterator<Profile> followingIterator = this.following.iterator();
        while (followingIterator.hasNext()) {
            Profile profile = followingIterator.next();
            if (profile.getIdProfile().equals(command.getIdProfileTarget())) {
                System.out.println("Already following");
                return;
            }
        }


        Iterator<Profile> followersIterator = this.followers.iterator();
        while (followersIterator.hasNext()) {
            Profile profile = followersIterator.next();
            if (profile.getIdProfile().equals(command.getIdProfileTarget())) {
                isFollower.set(true);
                break;
            }
        }

        if (isFollower.get()) {
            FriendCreateCommand friendCreateCommand = FriendCreateCommand.builder()
                    .idProfile1(command.getIdProfileFollower())
                    .idProfile2(command.getIdProfileTarget())
                    .executeAt(new Date())
                    .build();
            commandGateway.send(friendCreateCommand);
        } else {
            FollowCreateEvent event = new FollowCreateEvent();
            event.setProfileFollower(profileFollower);
            event.setProfileTarget(profileTarget);

            FollowAcceptCommand followAcceptCommand = FollowAcceptCommand.builder()
                    .idProfileFollower(command.getIdProfileFollower())
                    .idProfileTarget(command.getIdProfileTarget())
                    .build();
            commandGateway.send(followAcceptCommand);
            AggregateLifecycle.apply(event);
        }
    } else {
        System.out.println("Either the follower or the target profile does not exist");
    }
}
    @EventSourcingHandler
    public void on(FollowCreateEvent event) {
        this.idProfile = event.getProfileFollower().getIdProfile();
        this.following.add(event.getProfileTarget());
    }
    @CommandHandler
    public void handle(FollowAcceptCommand command , ProfileRepository profileRepository ) {
        Optional<Profile> profileFollower = profileRepository.findById(command.getIdProfileFollower());
        Optional<Profile> profileTarget = profileRepository.findById(command.getIdProfileTarget());
        FollowAcceptEvent event = new FollowAcceptEvent();
        event.setProfileFollower(profileFollower.get());
        event.setProfileTarget(profileTarget.get());
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(FollowAcceptEvent event) {
        this.idProfile = event.getProfileTarget().getIdProfile();
        this.followers.add(event.getProfileFollower());
    }




//    Remove follow
    public void handle(FollowRemoveCommand command, ProfileRepository profileRepository, CommandGateway commandGateway) {
    Optional<Profile> profileFollower = profileRepository.findById(command.getIdProfileFollower());
    Optional<Profile> profileTarget = profileRepository.findById(command.getIdProfileTarget());

    if (profileFollower.isPresent() && profileTarget.isPresent()) {
        FollowRemoveEvent event = new FollowRemoveEvent();
        event.setProfileFollower(profileFollower.get());
        event.setProfileTarget(profileTarget.get());
        FollowAcceptRemoveCommand followAcceptRemoveCommand = FollowAcceptRemoveCommand.builder()
                .idProfileFollower(command.getIdProfileFollower())
                .idProfileTarget(command.getIdProfileTarget()).build();
        commandGateway.send(followAcceptRemoveCommand);
        AggregateLifecycle.apply(event);
    } else {
        // Xử lý trường hợp không tìm thấy Profile
        // Ví dụ: throw một exception hoặc log thông báo lỗi
    }
}
    @EventSourcingHandler
    public void on(FollowRemoveEvent event) {
        this.idProfile = event.getProfileFollower().getIdProfile();
        Iterator<Profile> iterator = this.following.iterator();
        while (iterator.hasNext()) {
            Profile profile = iterator.next();
            if (profile.getIdProfile().equals(event.getProfileTarget().getIdProfile())) {
                iterator.remove();
                System.out.println("Following removed: " + profile.getIdProfile());
            }
        }
    }
    @CommandHandler
    public void handle(FollowAcceptRemoveCommand command , ProfileRepository profileRepository ) {
    Optional<Profile> profileFollower = profileRepository.findById(command.getIdProfileFollower());
    Optional<Profile> profileTarget = profileRepository.findById(command.getIdProfileTarget());
    FollowAcceptRemoveEvent event = new FollowAcceptRemoveEvent();
    event.setProfileFollower(profileFollower.get());
    event.setProfileTarget(profileTarget.get());
    AggregateLifecycle.apply(event);
}
    @EventSourcingHandler
    public void on(FollowAcceptRemoveEvent event) {
        this.idProfile = event.getProfileTarget().getIdProfile();
        Iterator<Profile> iterator = this.followers.iterator();
        while (iterator.hasNext()) {
            Profile profile = iterator.next();
            if (profile.getIdProfile().equals(event.getProfileFollower().getIdProfile())) {
                iterator.remove();
                System.out.println("Follower removed: " + profile.getIdProfile());
            }
        }
    }


//Create Friend
    @CommandHandler
    public void handle(FriendCreateCommand command , ProfileRepository profileRepository , CommandGateway commandGateway) {
        Optional<Profile> profile1 = profileRepository.findById(command.getIdProfile1());
        if(profile1==null){

        }
        Optional<Profile> profile2 = profileRepository.findById(command.getIdProfile2());
        if (profile2==null){

        }
        Friend friend = Friend.builder()
                .profile1(profile1.get())
                .profile2(profile2.get())
                .since(command.getExecuteAt())
                .idFriend(UUID.randomUUID().toString())
                .build();

        FriendCreateEvent event = FriendCreateEvent.builder()
                .friend(friend)
                .executeAt(command.getExecuteAt())
                .build();
        FriendAcceptCommand friendAcceptCommand = FriendAcceptCommand.builder()
                        .idProfile2(command.getIdProfile2())
                                .friend(friend)
                                        .executeAt(command.getExecuteAt()).build();
        commandGateway.send(friendAcceptCommand);
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(FriendCreateEvent event) {
        this.idProfile = event.getFriend().getProfile1().getIdProfile();
        Iterator<Profile> followersIterator = this.followers.iterator();
        while (followersIterator.hasNext()) {
            Profile profile = followersIterator.next();
            if (profile.getIdProfile().equals(event.getFriend().getProfile2().getIdProfile())) {
                followersIterator.remove();
                System.out.println("removing : FriendCreateEvent");
                break;
            }
        }
        this.friendShip.add(event.getFriend());
    }
    @CommandHandler
    public void handle(FriendAcceptCommand command) {
        FriendAcceptEvent event = FriendAcceptEvent.builder()
                .friend(command.getFriend())
                .executeAt(command.getExecuteAt())
                .build();
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(FriendAcceptEvent event) {
        this.idProfile = event.getFriend().getProfile2().getIdProfile();
        Iterator<Profile> followingIterator = this.following.iterator();
        while (followingIterator.hasNext()) {
            Profile profile = followingIterator.next();
            if (profile.getIdProfile().equals(event.getFriend().getProfile1().getIdProfile())) {
                followingIterator.remove();
                System.out.println("removing : FriendAcceptEvent");
                break;
            }
        }
        this.friendShip.add( event.getFriend());
    }



//    Remove Friend
    @CommandHandler
    public void handle(FriendRemoveCommand command ,
                       ProfileRepository profileRepository ,
                       CommandGateway commandGateway,
                       FriendRepository friendRepository) {
    Optional<Profile> profile = profileRepository.findById(command.getIdProfile());
    if(profile==null){

    }
    Optional<Friend> friend = friendRepository.findById(command.getIdFriend());
    if (friend==null){

    }
        FriendRemoveEvent event = FriendRemoveEvent.builder()
            .friend(friend.get())
            .executeAt(command.getExecuteAt())
            .idProfile(command.getIdProfile())
                .idProfileTarget(command.getIdProfileTarget())
            .build();
        FriendAcceptRemoveCommand friendAcceptRemoveCommand = FriendAcceptRemoveCommand.builder()
            .idProfileTarget(command.getIdProfileTarget())
            .friend(friend.get())
            .executeAt(command.getExecuteAt()).build();
    commandGateway.send(friendAcceptRemoveCommand);
    AggregateLifecycle.apply(event);
}
    @EventSourcingHandler
    public void on(FriendRemoveEvent event) {
        this.idProfile = event.getIdProfile();
        Iterator<Friend> friendIterator = this.friendShip.iterator();
        while (friendIterator.hasNext()) {
            Friend friend = friendIterator.next();
            if (friend.getIdFriend().equals(event.getFriend().getIdFriend())) {
                friendIterator.remove();
                System.out.println("removing : FriendRemoveEvent");
                break;
            }
        }
    }
    @CommandHandler
    public void handle(FriendAcceptRemoveCommand command) {
        FriendAcceptRemoveEvent event = FriendAcceptRemoveEvent.builder()
                .idProfileTarget(command.getIdProfileTarget())
                .friend(command.getFriend())
                .executeAt(command.getExecuteAt())
                .build();
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(FriendAcceptRemoveEvent event) {
        this.idProfile = event.getIdProfileTarget();
        Iterator<Friend> friendIterator = this.friendShip.iterator();
        while (friendIterator.hasNext()) {
            Friend friend = friendIterator.next();
            if (friend.getIdFriend().equals(event.getFriend().getIdFriend())) {
                friendIterator.remove();
                System.out.println("removing : FriendAcceptRemoveEvent");
                break;
            }
        }
    }



    @CommandHandler void handle(TestCommand command){
        log.info("Profile ID: " + idProfile);
        log.info("Full Name: " + fullName);
        log.info("URL Profile Picture: " + urlProfilePicture);
        log.info("Biography: " + biography);
        log.info("Gender: " + gender);
        log.info("Date of Birth: " + dateOfBirth);
        log.info("Account ID: " + idAccount);
        log.info("Following Profiles:");
        log.info(String.valueOf(this.following.size()));
        following.forEach(profile -> log.info(profile.toString()));
        log.info("Followers Profiles:");
        log.info(String.valueOf(this.followers.size()));
        followers.forEach(profile -> log.info(profile.toString()));
        log.info("Friend Profiles:");
        log.info(String.valueOf(this.friendShip.size()));
        friendShip.forEach((s) -> System.out.println(s.toString()));


    }

}
