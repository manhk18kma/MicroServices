package KMA.TTCS.CommonService.model;

public class FriendsResponseCom {
    String idProfile;
    String fullName;
    String urlProfilePicture;

    public FriendsResponseCom() {
    }

    public FriendsResponseCom(String idProfile, String fullName, String urlProfilePicture) {
        this.idProfile = idProfile;
        this.fullName = fullName;
        this.urlProfilePicture = urlProfilePicture;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrlProfilePicture() {
        return urlProfilePicture;
    }

    public void setUrlProfilePicture(String urlProfilePicture) {
        this.urlProfilePicture = urlProfilePicture;
    }

    @Override
    public String toString() {
        return "FriendsResponse{" +
                "idProfile='" + idProfile + '\'' +
                ", fullName='" + fullName + '\'' +
                ", urlProfilePicture='" + urlProfilePicture + '\'' +
                '}';
    }
}
