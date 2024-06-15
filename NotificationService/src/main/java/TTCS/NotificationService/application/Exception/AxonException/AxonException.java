package TTCS.NotificationService.application.Exception.AxonException;


public class AxonException extends RuntimeException {

    public AxonException(AxonErrorCode axonErrorCode) {
        super(axonErrorCode.getMessage());
        this.axonErrorCode = axonErrorCode;
    }

    private AxonErrorCode axonErrorCode;

    public AxonErrorCode getErrorCode() {
        return axonErrorCode;
    }

    public void setErrorCode(AxonErrorCode axonErrorCode) {
        this.axonErrorCode = axonErrorCode;
    }
}
