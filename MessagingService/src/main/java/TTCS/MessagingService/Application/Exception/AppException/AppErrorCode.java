package TTCS.MessagingService.Application.Exception.AppException;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AppErrorCode {
//    ProfileController
    CHAT_PROFILE_NOT_EXISTED(3001, "Chat profile does not exist", HttpStatus.BAD_REQUEST),
    CHAT_NOT_EXISTED(3001, "Chat  does not exist", HttpStatus.BAD_REQUEST),
    FRIEND_EXISTED(3002, "Already friend", HttpStatus.BAD_REQUEST),





    FOLLOW_NOT_EXISTED(2004, "Follow does not existed", HttpStatus.BAD_REQUEST),
    FRIEND_NOT_EXISTED(2005, "Friend does not existed", HttpStatus.BAD_REQUEST),




    USERNAME_EXISTED(1002, "Username has been existed", HttpStatus.BAD_REQUEST),
    ACCOUNT_ACTIVE(1003,"Account has ben active",HttpStatus.BAD_REQUEST ),
    INVALID_OTP(1004,"Invalid OTP",HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_EXISTED(1005,"Account not existed" , HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_INVALID(1006,"Old password invalid" , HttpStatus.BAD_REQUEST),
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
