package KMA.TTCS.CommonService.query;

public class FriendsOrFollowingQuery {
    String idProfile;

    public FriendsOrFollowingQuery(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }
}
