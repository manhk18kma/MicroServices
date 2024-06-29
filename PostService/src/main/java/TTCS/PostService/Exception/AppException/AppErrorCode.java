package TTCS.PostService.Exception.AppException;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AppErrorCode {
//    ProfileController
    POST_NOT_EXISTED(5001, "Post does not exist", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_EXISTED(5002, "Comment does not exist", HttpStatus.BAD_REQUEST),
    LIKE_EXISTED(5003, "Already liked", HttpStatus.BAD_REQUEST),
    LIKE_NOT_EXISTED(5003, "Like does not exist", HttpStatus.BAD_REQUEST),

    PROFILE_NOT_EXISTED(5004, "Profile does not exist", HttpStatus.BAD_REQUEST),





    UNCATEGORIZED_EXCEPTION(1999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN);

    AppErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
