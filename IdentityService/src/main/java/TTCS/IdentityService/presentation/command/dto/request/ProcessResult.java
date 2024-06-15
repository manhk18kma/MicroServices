package TTCS.IdentityService.presentation.command.dto.request;

public class ProcessResult {
    private String processId;
    private String status;

    // Constructor
    public ProcessResult() {
    }

    public ProcessResult(String processId, String status) {
        this.processId = processId;
        this.status = status;
    }

    // Getters and Setters
    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
