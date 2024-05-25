package TTCS.IdentityService.application.Exception.AppException;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AppErrorCode {
    UNCATEGORIZED_EXCEPTION(1999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_EXISTED(1001, "Email has been existed", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1002, "Username has been existed", HttpStatus.BAD_REQUEST),
    ACCOUNT_ACTIVE(1003,"Account has ben active",HttpStatus.BAD_REQUEST ),
    INVALID_OTP(1004,"Invalid OTP",HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_EXISTED(1005,"Account not existed" , HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_INVALID(1006,"Old password invalid" , HttpStatus.BAD_REQUEST);



    AppErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
