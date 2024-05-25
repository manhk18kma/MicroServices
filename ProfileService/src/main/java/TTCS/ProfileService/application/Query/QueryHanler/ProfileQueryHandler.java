package TTCS.ProfileService.application.Query.QueryHanler;

import TTCS.ProfileService.application.Query.Query.*;
import TTCS.ProfileService.domain.model.Friend;
import TTCS.ProfileService.domain.model.Profile;
import TTCS.ProfileService.infranstructure.persistence.FriendRepository;
import TTCS.ProfileService.infranstructure.persistence.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileQueryHandler {
    final ProfileRepository profileRepository;
    final FriendRepository friendRepository;

    @QueryHandler
    public List<Profile> handle(ProfileQueryGetAll profileQueryGetAll) {
        int pageNo = profileQueryGetAll.getPageNo();
        int pageSize = profileQueryGetAll.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Profile> page = profileRepository.findAll(pageable);
        return page.getContent();
    }
    @QueryHandler
    public List<Friend> handle(ProfileQueryGetAllFriend queryGetAllFriend) {
        System.out.println("ProfileQueryGetAllFriend QueryHandler");
        int pageNo = queryGetAllFriend.getPageNo();
        int pageSize = queryGetAllFriend.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Friend> page = friendRepository.findByIdProfile1OrIdProfile2(
                queryGetAllFriend.getIdProfile(),
                queryGetAllFriend.getIdProfile(),
                pageable
        );
        return page.getContent();
    }

    @QueryHandler
    public List<Profile> handle(ProfileQueryGetAllFollowings queryGetAllFollowings) {
        System.out.println("ProfileQueryGetAllFollowings QueryHandler");
        int pageNo = queryGetAllFollowings.getPageNo();
        int pageSize = queryGetAllFollowings.getPageSize();
        Profile profile = profileRepository.findById(queryGetAllFollowings.getIdProfile()).get();
        System.out.println(profile.toString());
        int startIndex = pageNo * pageSize;
        int endIndex = Math.min(startIndex + pageSize, profile.getFollowing().size());
        List<Profile> followingList = profile.getFollowing()
                .stream()
                .skip(startIndex)
                .limit(endIndex - startIndex)
                .collect(Collectors.toList());
        return followingList;
    }


    @QueryHandler
    public List<Profile> handle(ProfileQueryGetAllFollowers queryGetAllFollowers) {
        System.out.println("ProfileQueryGetAllFriend QueryHandler");
        int pageNo = queryGetAllFollowers.getPageNo();
        int pageSize = queryGetAllFollowers.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Profile> page = profileRepository.findAllByFollowing_IdProfile(
                queryGetAllFollowers.getIdProfile(),
                pageable
        );
        return page.getContent();
    }
    @QueryHandler
    public Optional<Profile> handle(ProfileQueryGetById profileQueryGetById) {
        return profileRepository.findById(profileQueryGetById.getId());
    }



}
