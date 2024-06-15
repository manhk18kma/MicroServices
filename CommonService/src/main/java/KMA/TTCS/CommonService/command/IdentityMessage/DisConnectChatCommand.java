package KMA.TTCS.CommonService.command.IdentityMessage;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DisConnectChatCommand {

    @TargetAggregateIdentifier
    String idChatProfile;

    public DisConnectChatCommand(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }

    public String getIdChatProfile() {
        return idChatProfile;
    }

    public void setIdChatProfile(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }
}
