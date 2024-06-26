package TTCS.PostService.Database.Service;

import TTCS.PostService.DTO.Comment.Request.CreateCommentRequest;
import TTCS.PostService.DTO.Comment.Request.UpdateCommentRequest;
import TTCS.PostService.DTO.Comment.Response.CommentResponse;
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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {
    PostRepository postRepository;
    CommentRepository commentRepository;

    @Transactional
    public CommentResponse createComment(CreateCommentRequest createCommentRequest) {
        Post post = postRepository.findById(createCommentRequest.getIdPost())
                .orElseThrow(() -> new AppException(AppErrorCode.POST_NOT_EXISTED));

        Comment comment = Comment.builder()
                .idComment(UUID.randomUUID().toString())
                .updateAt(new Date())
                .content(createCommentRequest.getContent())
                .idProfile(createCommentRequest.getIdProfile())
                .post(post)
                .build();

        commentRepository.save(comment);
        return CommentResponse.builder()
                .idComment(comment.getIdComment())
                .updateAt(comment.getUpdateAt())
                .content(comment.getContent())
                .idPost(post.getIdPost())
                .idProfile(comment.getIdProfile())
                .build();
    }

    @Transactional
    public CommentResponse updateComment(UpdateCommentRequest updateCommentRequest, String idComment) {
        Comment comment = commentRepository.findById(idComment).orElseThrow(
                ()->new AppException(AppErrorCode.COMMENT_NOT_EXISTED)
        );


        comment.setContent(updateCommentRequest.getContent());
        comment.setUpdateAt(new Date());


        commentRepository.save(comment);
        return CommentResponse.builder()
                .idComment(comment.getIdComment())
                .updateAt(comment.getUpdateAt())
                .content(comment.getContent())
                .idPost(comment.getPost().getIdPost())
                .idProfile(comment.getIdProfile())
                .build();
    }

    @Transactional
    public String deleteComment(String idComment) {
        Comment comment = commentRepository.findById(idComment).orElseThrow(
                ()->new AppException(AppErrorCode.COMMENT_NOT_EXISTED)
        );
        commentRepository.deleteById(idComment);
        return idComment;
    }
}
