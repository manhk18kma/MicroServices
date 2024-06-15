package TTCS.IdentityService.presentation.command.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessRequest {
    private String processId;
    private String callbackUrl;

    public ProcessRequest() {
    }

    public ProcessRequest(String processId, String callbackUrl) {
        this.processId = processId;
        this.callbackUrl = callbackUrl;
    }

    // Getters and Setters
    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
