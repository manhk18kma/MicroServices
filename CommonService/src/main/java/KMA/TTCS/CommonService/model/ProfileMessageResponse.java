package KMA.TTCS.CommonService.model;

import java.util.HashSet;
import java.util.Set;

public class ProfileMessageResponse {
    String idProfile;
    String fullName;
    String urlProfilePicture;

    Set<String> setIdFriends = new HashSet<>();


    public ProfileMessageResponse(String idProfile, String fullName, String urlProfilePicture, Set<String> setIdFriends) {
        this.idProfile = idProfile;
        this.fullName = fullName;
        this.urlProfilePicture = urlProfilePicture;
        this.setIdFriends = setIdFriends;
    }

    public Set<String> getSetIdFriends() {
        return setIdFriends;
    }

    public void setSetIdFriends(Set<String> setIdFriends) {
        this.setIdFriends = setIdFriends;
    }

    public String getIdProfile() {
        return idProfile;
    }



    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public ProfileMessageResponse() {
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
        return "ProfileMessageResponse{" +
                "fullName='" + fullName + '\'' +
                ", urlProfilePicture='" + urlProfilePicture + '\'' +
                '}';
    }
}
