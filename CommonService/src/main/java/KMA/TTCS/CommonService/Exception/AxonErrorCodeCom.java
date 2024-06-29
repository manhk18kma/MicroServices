package KMA.TTCS.CommonService.Exception;

import org.springframework.http.HttpStatus;

public class AxonErrorCodeCom {
    public static final AxonErrorCodeCom UNCATEGORIZED_EXCEPTION =
            new AxonErrorCodeCom(2999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    public static final AxonErrorCodeCom AGGREGATE_NOT_FOUND_EXCEPTION =
            new AxonErrorCodeCom(2000, "The aggregate was not found in the event store", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    private AxonErrorCodeCom(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
