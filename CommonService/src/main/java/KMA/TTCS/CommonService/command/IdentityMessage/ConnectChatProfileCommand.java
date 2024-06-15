package KMA.TTCS.CommonService.command.IdentityMessage;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ConnectChatProfileCommand {
    @TargetAggregateIdentifier
    String idChatProfile;

    public ConnectChatProfileCommand(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }

    public String getIdChatProfile() {
        return idChatProfile;
    }

    public void setIdChatProfile(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }
}
