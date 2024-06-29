package KMA.TTCS.CommonService.Exception;


public class AxonExceptionCom extends RuntimeException {

    public AxonExceptionCom(AxonErrorCodeCom axonErrorCode) {
        super(axonErrorCode.getMessage());
        this.axonErrorCode = axonErrorCode;
    }


    private AxonErrorCodeCom axonErrorCode;

    public AxonErrorCodeCom getErrorCode() {
        return axonErrorCode;
    }

    public void setErrorCode(AxonErrorCodeCom axonErrorCode) {
        this.axonErrorCode = axonErrorCode;
    }
}
