package TTCS.PostService.Database.Service;

import KMA.TTCS.CommonService.model.FriendsOrFollowingResponse;
import KMA.TTCS.CommonService.model.ProfileMessageResponse;
import KMA.TTCS.CommonService.query.FriendsOrFollowingQuery;
import KMA.TTCS.CommonService.query.ProfileMessageQuery;
import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.DTO.Post.Request.CreatePostRequest;
import TTCS.PostService.DTO.Post.Request.DeletePostRequest;
import TTCS.PostService.DTO.Post.Request.UpdatePostRequest;
import TTCS.PostService.DTO.Post.Response.PostFriendsOrFollowingResponse;
import TTCS.PostService.DTO.Post.Response.PostResponse;
import TTCS.PostService.Database.Messaging.MessagingService;
import TTCS.PostService.Database.Repository.ImageRepository;
import TTCS.PostService.Database.Repository.PostRepository;
import TTCS.PostService.Entity.Image;
import TTCS.PostService.Entity.Post;
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
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    ImageService imageService;
    PostRepository postRepository;
    ImageRepository imageRepository;
    UploadImageServiceImpl uploadImageService;
    QueryGateway queryGateway;
    MessagingService messagingService;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public PostResponse createPost(CreatePostRequest createPostRequest, String idProfileToken) {
        ProfileMessageResponse profileMessageResponse;
            ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(idProfileToken, -1, -1);
            CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
            profileMessageResponse = future.join();
        if(profileMessageResponse==null){
             throw  new AppException(AppErrorCode.PROFILE_NOT_EXISTED);
        }
        Post post = Post.builder()
                .idPost(UUID.randomUUID().toString())
                .caption(createPostRequest.getCaption())
                .updateAt(new Date())
                .idProfile(idProfileToken)
                .comments(new ArrayList<>())
                .images(new ArrayList<>())
                .likes(new ArrayList<>())
                .build();

        List<String> base64Images = createPostRequest.getBase64();
        List<Image> images = base64Images.stream()
                .map(base64 -> {
                    String idImage = UUID.randomUUID().toString();
                    String imageUrl = imageService.getUrl(base64, idImage);
                    return Image.builder()
                            .idImage(idImage)
                            .urlImage(imageUrl)
                            .post(post)
                            .build();
                })
                .collect(Collectors.toList());

        post.setImages(images);

        postRepository.save(post);

        List<String> imageUrls = images.stream()
                .map(Image::getUrlImage)
                .collect(Collectors.toList());


        String message = "nameTarget just posted something new! Let's interact together.";
        messagingService.sendToKafka(message , idProfileToken,null , post.getIdPost(),"create_post_topic" , post.getIdPost());






        return PostResponse.builder()
                .idPost(post.getIdPost())
                .caption(post.getCaption())
                .updateAt(post.getUpdateAt())
                .idProfile(post.getIdProfile())
                .images(imageUrls)
                .fullName(profileMessageResponse.getFullName())
                .urlAvt(profileMessageResponse.getUrlProfilePicture())
                .build();
    }


    @Transactional
    @PreAuthorize("#post.idProfile == authentication.name and hasRole('USER')")
    public void deletePost(Post post) {
        messagingService.sendDataToServiceB(post.getIdPost());
        postRepository.delete(post);
    }


    @PreAuthorize("#post.idProfile == authentication.name and hasRole('USER')")
    public PostResponse updatePost(UpdatePostRequest updatePostRequest, Post post) {


        post.setUpdateAt(new Date());
        post.setCaption(updatePostRequest.getCaption());

        List<Image> oldImages = post.getImages();

        List<String> base64OrUrl = updatePostRequest.getBase64OrUrl();

        List<Image> notRemovedImages = oldImages.stream()
                .filter(image -> base64OrUrl.contains(image.getUrlImage()))
                .collect(Collectors.toList());

        List<Image> removedImages = oldImages.stream()
                .filter(image -> !base64OrUrl.contains(image.getUrlImage()))
                .collect(Collectors.toList());

        List<Image> newImages = base64OrUrl.stream()
                .filter(s -> !s.startsWith("http"))
                .map(s -> {
                    String idImage = UUID.randomUUID().toString();
                    String imageUrl = imageService.getUrl(s, idImage);
                    return Image.builder()
                            .idImage(idImage)
                            .urlImage(imageUrl)
                            .post(post)
                            .build();
                })
                .collect(Collectors.toList());

        removedImages.forEach(image -> uploadImageService.deleteImage(image.getUrlImage()));

        // Update post's images
        post.getImages().clear();
        post.getImages().addAll(notRemovedImages);
        post.getImages().addAll(newImages);

        // Save the updated post
        postRepository.save(post);
        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(post.getIdProfile(), -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();
        // Build and return the response
        return PostResponse.builder()
                .idPost(post.getIdPost())
                .caption(post.getCaption())
                .updateAt(post.getUpdateAt())
                .idProfile(post.getIdProfile())
                .images(post.getImages().stream().map(Image::getUrlImage).collect(Collectors.toList()))
                .fullName(profileMessageResponse.getFullName())
                .urlAvt(profileMessageResponse.getUrlProfilePicture())
                .build();
    }


    public Post prevCheck(String idPost){
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));
        return post;
    }




    public PageResponse<List<PostFriendsOrFollowingResponse>> getPostOfFriendsOrFollowing(String idProfileToken, int pageNo, int pageSize) {
        CompletableFuture<List<FriendsOrFollowingResponse>> future = queryGateway.query(
                new FriendsOrFollowingQuery(idProfileToken),
                ResponseTypes.multipleInstancesOf(FriendsOrFollowingResponse.class)
        );
        List<FriendsOrFollowingResponse> friendsOrFollowingResponses = future.join();

        List<String> idProfiles = friendsOrFollowingResponses.stream()
                .map(FriendsOrFollowingResponse::getIdProfile)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findByIdProfileInOrderByUpdateAtDesc(idProfiles, pageable);

        List<PostFriendsOrFollowingResponse> responses = posts.getContent().stream().map(post -> {
            Optional<FriendsOrFollowingResponse> optionalResponse = friendsOrFollowingResponses.stream()
                    .filter(f -> f.getIdProfile().equals(post.getIdProfile()))
                    .findFirst();

            String fullName = optionalResponse.map(FriendsOrFollowingResponse::getFullName).orElse(null);
            String urlAvt = optionalResponse.map(FriendsOrFollowingResponse::getUrlProfilePicture).orElse(null);

            return PostFriendsOrFollowingResponse.builder()
                    .idPost(post.getIdPost())
                    .caption(post.getCaption())
                    .updateAt(post.getUpdateAt())
                    .images(post.getImages().stream().map(Image::getUrlImage).collect(Collectors.toList()))
                    .idProfile(post.getIdProfile())
                    .fullName(fullName)
                    .urlAvt(urlAvt)
                    .build();
        }).collect(Collectors.toList());

        return PageResponse.<List<PostFriendsOrFollowingResponse>>builder()
                .size(pageSize)
                .totalElements((int) posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .number(pageNo)
                .items(responses)
                .build();
    }

    public PostFriendsOrFollowingResponse getPostByID(String idPost) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery( post.getIdProfile(), -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();

        return PostFriendsOrFollowingResponse.builder()
                .idPost(post.getIdPost())
                .caption(post.getCaption())
                .updateAt(post.getUpdateAt())
                .images(post.getImages().stream().map(Image::getUrlImage).collect(Collectors.toList()))
                .idProfile(post.getIdProfile())
                .fullName(profileMessageResponse.getFullName())
                .urlAvt(profileMessageResponse.getUrlProfilePicture())
                .build();
    }

    public PageResponse<List<PostFriendsOrFollowingResponse>> getPostOfProfile(String idProfile, int pageNo, int pageSize) {
        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(idProfile, -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findByIdProfileOrderByUpdateAtDesc(idProfile, pageable);

        List<PostFriendsOrFollowingResponse> responses = posts.getContent().stream()
                .map(post -> PostFriendsOrFollowingResponse.builder()
                        .idPost(post.getIdPost())
                        .caption(post.getCaption())
                        .updateAt(post.getUpdateAt())
                        .images(post.getImages().stream().map(Image::getUrlImage).collect(Collectors.toList()))
                        .idProfile(post.getIdProfile())
                        .fullName(profileMessageResponse.getFullName())
                        .urlAvt(profileMessageResponse.getUrlProfilePicture())
                        .build())
                .collect(Collectors.toList());

        return PageResponse.<List<PostFriendsOrFollowingResponse>>builder()
                .size(pageSize)
                .totalElements((int) posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .number(pageNo)
                .items(responses)
                .build();
    }

}
