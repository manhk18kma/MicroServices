package KMA.TTCS.CommonService.command.AccountProfileCommand;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ChatProfileRollbackCommand {
    @TargetAggregateIdentifier
    String idChatProfile;

    public ChatProfileRollbackCommand() {
    }

    public ChatProfileRollbackCommand(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }

    public String getIdChatProfile() {
        return idChatProfile;
    }

    public void setIdChatProfile(String idChatProfile) {
        this.idChatProfile = idChatProfile;
    }
}
