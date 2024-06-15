package TTCS.NotificationService.application.Exception.AxonException;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AxonErrorCode {
    UNCATEGORIZED_EXCEPTION(2999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);


    AxonErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}