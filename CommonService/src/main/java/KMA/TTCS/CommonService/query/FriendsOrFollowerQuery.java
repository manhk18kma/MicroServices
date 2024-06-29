package KMA.TTCS.CommonService.query;

public class FriendsOrFollowerQuery {
    String idProfile;

    public FriendsOrFollowerQuery(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }
}
