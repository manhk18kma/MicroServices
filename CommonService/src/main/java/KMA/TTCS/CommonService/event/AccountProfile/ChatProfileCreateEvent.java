package KMA.TTCS.CommonService.event.AccountProfile;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ChatProfileCreateEvent {
    String idChatProfile;
    private String idProfile;
    String idAccount;
    String email;


    public ChatProfileCreateEvent() {
    }

    public ChatProfileCreateEvent(String idChatProfile, String idProfile, String idAccount, String email) {
        this.idChatProfile = idChatProfile;
        this.idProfile = idProfile;
        this.idAccount = idAccount;
        this.email = email;
    }

    public String getIdChatProfile() {
        return idChatProfile;
    }

    public void setIdChatProfile(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
