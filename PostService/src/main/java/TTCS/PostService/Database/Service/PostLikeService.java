package TTCS.PostService.Database.Service;

import KMA.TTCS.CommonService.model.ProfileMessageResponse;
import KMA.TTCS.CommonService.query.ProfileMessageQuery;
import TTCS.PostService.DTO.Like.Request.PostLikeRequest;
import TTCS.PostService.DTO.Like.Response.LikeResponse;
import TTCS.PostService.DTO.Like.Response.PostLikeDetail;
import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.Database.Messaging.MessagingService;
import TTCS.PostService.Database.Repository.PostLikeRepository;
import TTCS.PostService.Database.Repository.PostRepository;
import TTCS.PostService.Entity.Post;
import TTCS.PostService.Entity.PostLike;
import TTCS.PostService.Exception.AppException.AppErrorCode;
import TTCS.PostService.Exception.AppException.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostLikeService {
    PostLikeRepository postLikeRepository;
    PostRepository postRepository;
    QueryGateway queryGateway;
    MessagingService messagingService;

    @PreAuthorize("hasRole('USER')")
    public PostLikeDetail like(PostLikeRequest likeRequest, String idProfileToken) {
        Post post = postRepository.findById(likeRequest.getIdPost())
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

        boolean isAlreadyLiked = postLikeRepository.existsByPost_IdPostAndIdProfile(
                likeRequest.getIdPost(),
                idProfileToken
        );
        if (isAlreadyLiked) {
            throw new AppException(AppErrorCode.LIKE_EXISTED);
        }
        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(idProfileToken, -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();
        PostLike postLike = PostLike.builder()
                .idLike(UUID.randomUUID().toString())
                .createAt(new Date())
                .post(post)
                .idProfile(idProfileToken)
                .build();

        postLikeRepository.save(postLike);

        String message = "nameTarget liked on your post!";
        messagingService.sendToKafka(message , idProfileToken,post.getIdProfile() , post.getIdPost(),"create_like_topic" , postLike.getIdLike());

        return PostLikeDetail.builder()
                .idPostLike(postLike.getIdLike())
                .idPost(post.getIdPost())
                .createAt(postLike.getCreateAt())
                .idProfile(idProfileToken)
                .fullName(profileMessageResponse.getFullName())
                .urlAvt(profileMessageResponse.getUrlProfilePicture())
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    public void unLike(PostLikeRequest postLikeRequest , String idProfileToken) {
        System.out.println(idProfileToken);
        PostLike postLike = postLikeRepository.findByPost_IdPostAndIdProfile(
                        postLikeRequest.getIdPost(), idProfileToken);
        if(postLike == null){
            throw new AppException(AppErrorCode.LIKE_NOT_EXISTED);
        }
        messagingService.sendDataToServiceB(postLike.getIdLike());
        postLikeRepository.deleteById(postLike.getIdLike());

    }


    public PageResponse<List<PostLikeDetail>> likeDetailOfPost(String idPost, int pageNo, int pageSize) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

        Pageable pageable = PageRequest.of(pageNo , pageSize);

        Page<PostLike> page = postLikeRepository.findByPost_IdPost(post.getIdPost() , pageable);

        List<PostLikeDetail> response = page.getContent().stream().map(postLike -> {

            ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(postLike.getIdProfile(), -1, -1);
            CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
            ProfileMessageResponse profileMessageResponse = future.join();

            return PostLikeDetail.builder()
                    .idPostLike(postLike.getIdLike())
                    .idPost(post.getIdPost())
                    .createAt(postLike.getCreateAt())
                    .idProfile(postLike.getIdProfile())
                    .fullName(profileMessageResponse.getFullName())
                    .urlAvt(profileMessageResponse.getUrlProfilePicture())
                    .build();
        }).collect(Collectors.toList());


        return PageResponse.<List<PostLikeDetail>>builder()
                .size(pageSize)
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(pageNo)
                .items(response)
                .build();
    }


    public LikeResponse countLike(String idPost) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));
        int count = postLikeRepository.countAllByPost_IdPost(post.getIdPost());
        return LikeResponse.builder().count(count).build();
    }
}
