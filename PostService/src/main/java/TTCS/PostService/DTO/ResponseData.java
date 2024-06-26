package TTCS.PostService.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Date;

@Builder
public class ResponseData<T> {
    private int status;
    private String message;
    private Date timestamp;
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

    public ResponseData(int status, String message, Date timestamp, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
    return data;
}

public void setData(T data) {
    this.data = data;
}
}
