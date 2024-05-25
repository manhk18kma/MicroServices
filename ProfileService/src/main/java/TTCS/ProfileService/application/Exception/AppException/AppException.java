package TTCS.ProfileService.application.Exception.AppException;

public class AppException extends RuntimeException {

    public AppException(AppErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private AppErrorCode errorCode;

    public AppErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(AppErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
