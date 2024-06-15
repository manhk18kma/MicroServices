package KMA.TTCS.CommonService.command.AccountProfileCommand;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ProfileRollBackCommand {
    @TargetAggregateIdentifier
    String idProfile;

    public ProfileRollBackCommand() {
    }

    public ProfileRollBackCommand(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }
}
