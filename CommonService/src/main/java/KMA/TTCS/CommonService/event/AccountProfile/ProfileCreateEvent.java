package KMA.TTCS.CommonService.event.AccountProfile;

import KMA.TTCS.CommonService.enumType.Gender;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileCreateEvent {

    private String idProfile;
    private String fullName;
    private String urlProfilePicture;
    private String biography;
    private Gender gender;
    private Date dateOfBirth;
    private String idAccount;


    public ProfileCreateEvent() {
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public ProfileCreateEvent(String idProfile, String fullName, String urlProfilePicture, String biography, Gender gender, Date dateOfBirth, String idAccount) {
        this.idProfile = idProfile;
        this.fullName = fullName;
        this.urlProfilePicture = urlProfilePicture;
        this.biography = biography;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.idAccount = idAccount;
    }

    @Override
    public String toString() {
        return "ProfileCreateEvent{" +
                "idProfile='" + idProfile + '\'' +
                ", fullName='" + fullName + '\'' +
                ", urlProfilePicture='" + urlProfilePicture + '\'' +
                ", biography='" + biography + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", idAccount='" + idAccount + '\'' +
                '}';
    }
}
