package TTCS.IdentityService.application.Exception.AppException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AppErrorCode {

    EMAIL_EXISTED(1001, "Email has already existed", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1002, "Username has already existed", HttpStatus.BAD_REQUEST),

    ACCOUNT_NOT_EXISTED(1003, "Account does not exist", HttpStatus.BAD_REQUEST),
    ACCOUNT_ACTIVE(1004, "Account has already been activated", HttpStatus.BAD_REQUEST),

    OLD_PASSWORD_INVALID(1005, "Old password is invalid", HttpStatus.BAD_REQUEST),
    INVALID_OTP(1006, "Invalid OTP", HttpStatus.BAD_REQUEST),



    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),

    PASSWORD_INVALID(1009, "Password is invalid", HttpStatus.BAD_REQUEST),
    ACCOUNT_INACTIVE(1010, "Account is inactive", HttpStatus.BAD_REQUEST),


    UNCATEGORIZED_EXCEPTION(1999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    AppErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
