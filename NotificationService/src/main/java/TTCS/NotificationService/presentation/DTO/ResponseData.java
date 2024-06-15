package TTCS.NotificationService.presentation.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;


public class ResponseData<T> {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private T data;

public ResponseData(int status, String message) {
    this.status = status;
    this.message = message;
}

public ResponseData(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
}

public int getStatus() {
    return status;
}

public void setStatus(int status) {
    this.status = status;
}

public String getMessage() {
    return message;
}

public void setMessage(String message) {
    this.message = message;
}

public T getData() {
    return data;
}

public void setData(T data) {
    this.data = data;
}
}
