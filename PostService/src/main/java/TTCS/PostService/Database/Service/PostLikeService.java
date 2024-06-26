package TTCS.PostService.Database.Service;

import TTCS.PostService.DTO.Like.Request.PostLikeRequest;
import TTCS.PostService.DTO.Like.Response.LikeResponse;
import TTCS.PostService.Database.Repository.PostLikeRepository;
import TTCS.PostService.Database.Repository.PostRepository;
import TTCS.PostService.Entity.Comment;
import TTCS.PostService.Entity.Post;
import TTCS.PostService.Entity.PostLike;
import TTCS.PostService.Exception.AppException.AppErrorCode;
import TTCS.PostService.Exception.AppException.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostLikeService {
    PostLikeRepository postLikeRepository;
    PostRepository postRepository;
    public LikeResponse like(PostLikeRequest likeRequest) {
        Post post = postRepository.findById(likeRequest.getIdPost())
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

        boolean isAlreadyLiked = postLikeRepository.existsByPost_IdPostAndIdProfile(
                likeRequest.getIdPost(),
                likeRequest.getIdProfile()
        );
        if (isAlreadyLiked) {
            throw new AppException(AppErrorCode.LIKE_EXISTED);
        }

        PostLike postLike = PostLike.builder()
                .idLike(UUID.randomUUID().toString())
                .createAt(new Date())
                .post(post)
                .idProfile(likeRequest.getIdProfile())
                .build();

        postLikeRepository.save(postLike);

        return LikeResponse.builder()
                .idPostLike(postLike.getIdLike())
                .idPost(post.getIdPost())
                .createAt(postLike.getCreateAt())
                .idProfile(likeRequest.getIdProfile())
                .build();
    }


    public String unLike(String idPostLike) {

        PostLike postLike = postLikeRepository.findById(idPostLike)
                .orElseThrow(() -> new AppException(AppErrorCode.LIKE_NOT_EXISTED));
        postLikeRepository.deleteById(idPostLike);
        return idPostLike;

    }
}
