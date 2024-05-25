package KMA.TTCS.CommonService;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
public class ResponseData<T> implements Serializable {
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

}
