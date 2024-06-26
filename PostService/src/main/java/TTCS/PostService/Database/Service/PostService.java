package TTCS.PostService.Database.Service;

import KMA.TTCS.CommonService.model.FriendsOrFollowingResponse;
import KMA.TTCS.CommonService.query.FriendsOrFollowingQuery;
import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.DTO.Post.Request.CreatePostRequest;
import TTCS.PostService.DTO.Post.Request.UpdatePostRequest;
import TTCS.PostService.DTO.Post.Response.PostFriendsOrFollowingResponse;
import TTCS.PostService.DTO.Post.Response.PostResponse;
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

    @Transactional
    public PostResponse createPost(CreatePostRequest createPostRequest) {
        Post post = Post.builder()
                .idPost(UUID.randomUUID().toString())
                .caption(createPostRequest.getCaption())
                .updateAt(new Date())
                .idProfile(createPostRequest.getIdProfile())
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

        return PostResponse.builder()
                .idPost(post.getIdPost())
                .caption(post.getCaption())
                .updateAt(post.getUpdateAt())
                .idProfile(post.getIdProfile())
                .images(imageUrls)
                .build();
    }
    @Transactional
    public String deletePost(String idPost) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));;
        post.getImages().forEach(image -> uploadImageService.deleteImage(image.getUrlImage()));
        postRepository.deleteById(idPost);
        return idPost;
    }
    public PostResponse updatePost(UpdatePostRequest updatePostRequest, String idPost) {
        // Retrieve the post from repository
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

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

        // Build and return the response
        return PostResponse.builder()
                .idPost(post.getIdPost())
                .caption(post.getCaption())
                .updateAt(post.getUpdateAt())
                .idProfile(post.getIdProfile())
                .images(post.getImages().stream().map(Image::getUrlImage).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void deleteImage(String imageId) {
        imageRepository.deleteById(imageId);
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
                .size(responses.size())
                .totalElements((int) posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .number(pageNo)
                .items(responses)
                .build();
    }
}
