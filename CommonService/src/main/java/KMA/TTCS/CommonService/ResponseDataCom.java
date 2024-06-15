package KMA.TTCS.CommonService;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class ResponseDataCom<T> {
    private int status;
    private String message;
    private Date timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

public ResponseDataCom(int status, String message) {
    this.status = status;
    this.message = message;
}

public ResponseDataCom(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
}

    public ResponseDataCom(int status, String message, Date timestamp, T data) {
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
