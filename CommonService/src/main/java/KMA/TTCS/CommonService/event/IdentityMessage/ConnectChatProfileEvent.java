package KMA.TTCS.CommonService.event.IdentityMessage;

public class ConnectChatProfileEvent {
    String idChatProfile;
    String status;

    public ConnectChatProfileEvent(String idChatProfile, String status) {
        this.idChatProfile = idChatProfile;
        this.status = status;
    }

    public ConnectChatProfileEvent() {
    }

    public String getIdChatProfile() {
        return idChatProfile;
    }

    public void setIdChatProfile(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
