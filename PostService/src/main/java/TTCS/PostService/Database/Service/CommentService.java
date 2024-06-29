package TTCS.PostService.Database.Service;

import KMA.TTCS.CommonService.model.ProfileMessageResponse;
import KMA.TTCS.CommonService.query.ProfileMessageQuery;
import TTCS.PostService.DTO.Comment.Request.CreateCommentRequest;
import TTCS.PostService.DTO.Comment.Request.DeleteCommentRequest;
import TTCS.PostService.DTO.Comment.Request.UpdateCommentRequest;
import TTCS.PostService.DTO.Comment.Response.CommentResponse;
import TTCS.PostService.DTO.PageResponse;
import TTCS.PostService.DTO.Post.Response.PostFriendsOrFollowingResponse;
import TTCS.PostService.Database.Messaging.MessagingService;
import TTCS.PostService.Database.Repository.CommentRepository;
import TTCS.PostService.Database.Repository.PostRepository;
import TTCS.PostService.Entity.Comment;
import TTCS.PostService.Entity.Post;
import TTCS.PostService.Exception.AppException.AppErrorCode;
import TTCS.PostService.Exception.AppException.AppException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
public class CommentService {
    PostRepository postRepository;
    CommentRepository commentRepository;
    QueryGateway queryGateway;
    MessagingService messagingService;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public CommentResponse createComment(CreateCommentRequest createCommentRequest , String idProfileToken) {
        Post post = postRepository.findById(createCommentRequest.getIdPost())
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

        Comment comment = Comment.builder()
                .idComment(UUID.randomUUID().toString())
                .updateAt(new Date())
                .content(createCommentRequest.getContent())
                .idProfile(idProfileToken)
                .post(post)
                .build();

        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(idProfileToken, -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();
        if(profileMessageResponse==null){
            throw  new AppException(AppErrorCode.PROFILE_NOT_EXISTED);
        }
        commentRepository.save(comment);


        String message = "nameTarget commented on your post!";
        messagingService.sendToKafka(message , idProfileToken,post.getIdProfile() , post.getIdPost(),"create_comment_topic" , comment.getIdComment());

        return CommentResponse.builder()
                .idComment(comment.getIdComment())
                .updateAt(comment.getUpdateAt())
                .content(comment.getContent())
                .idPost(post.getIdPost())
                .idProfile(comment.getIdProfile())
                .fullName(profileMessageResponse.getFullName())
                .urlAvt(profileMessageResponse.getUrlProfilePicture())
                .build();
    }

    public Comment prevCheck(String idComment){
        Comment comment = commentRepository.findById(idComment).orElseThrow(
                ()->new AppException(AppErrorCode.COMMENT_NOT_EXISTED)
        );
        return comment;
    }



    @Transactional
    @PreAuthorize("#comment.idProfile == authentication.name and hasRole('USER')")
    public CommentResponse updateComment(UpdateCommentRequest updateCommentRequest, Comment comment) {


        comment.setContent(updateCommentRequest.getContent());
        comment.setUpdateAt(new Date());


        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(comment.getIdProfile(), -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();
        commentRepository.save(comment);
        return CommentResponse.builder()
                .idComment(comment.getIdComment())
                .updateAt(comment.getUpdateAt())
                .content(comment.getContent())
                .idPost(comment.getPost().getIdPost())
                .idProfile(comment.getIdProfile())
                .fullName(profileMessageResponse.getFullName())
                .urlAvt(profileMessageResponse.getUrlProfilePicture())
                .build();
    }

    @Transactional
    @PreAuthorize("#comment.idProfile == authentication.name and hasRole('USER')")
    public void deleteComment(Comment comment) {
        messagingService.sendDataToServiceB(comment.getIdComment());
        commentRepository.delete(comment);
    }

    public PageResponse getAllCommentsOfPost(int pageNo, int pageSize, String idPost) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<Comment> page = commentRepository.findByPost_IdPostOrderByUpdateAtDesc(idPost , pageable);

        List<CommentResponse> responses = page.getContent().stream().map(comment -> {
            ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(comment.getIdProfile(), -1, -1);
            CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
            ProfileMessageResponse profileMessageResponse = future.join();

            return CommentResponse.builder()
                    .idComment(comment.getIdComment())
                    .updateAt(comment.getUpdateAt())
                    .content(comment.getContent())
                    .idPost(post.getIdPost())
                    .idProfile(comment.getIdProfile())
                    .fullName(profileMessageResponse.getFullName())
                    .urlAvt(profileMessageResponse.getUrlProfilePicture())
                    .build();
        }).collect(Collectors.toList());


        return PageResponse.<List<CommentResponse>>builder()
                .size(pageSize)
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(pageNo)
                .items(responses)
                .build();
    }

    public CommentResponse getCommentById(String idComment) {

        Comment comment = commentRepository.findById(idComment)
                .orElseThrow(() -> new AppException(AppErrorCode.COMMENT_NOT_EXISTED));

        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(comment.getIdProfile(), -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();

        return CommentResponse.builder()
                .idComment(comment.getIdComment())
                .updateAt(comment.getUpdateAt())
                .content(comment.getContent())
                .idPost(comment.getPost().getIdPost())
                .idProfile(comment.getIdProfile())
                .fullName(profileMessageResponse.getFullName())
                .urlAvt(profileMessageResponse.getUrlProfilePicture())
                .build();
    }
}
