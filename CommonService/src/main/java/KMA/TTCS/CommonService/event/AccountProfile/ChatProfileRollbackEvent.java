package KMA.TTCS.CommonService.event.AccountProfile;

public class ChatProfileRollbackEvent {
    String idChatProfile;

    public ChatProfileRollbackEvent() {
    }

    public ChatProfileRollbackEvent(String idProfile) {
        this.idChatProfile = idProfile;
    }

    public String getIdChatProfile() {
        return idChatProfile;
    }

    public void setIdChatProfile(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }
}
