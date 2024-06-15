package KMA.TTCS.CommonService.event.AccountProfile;

public class ProfileRollBackEvent {
    String idProfile;

    public ProfileRollBackEvent() {
    }

    public ProfileRollBackEvent(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }
}
