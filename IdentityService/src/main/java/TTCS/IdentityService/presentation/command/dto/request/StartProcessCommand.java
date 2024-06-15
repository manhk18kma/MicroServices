package TTCS.IdentityService.presentation.command.dto.request;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class StartProcessCommand {

    @TargetAggregateIdentifier
    private String processId;
    private String callbackUrl;

    // Constructor
    public StartProcessCommand(String processId, String callbackUrl) {
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
