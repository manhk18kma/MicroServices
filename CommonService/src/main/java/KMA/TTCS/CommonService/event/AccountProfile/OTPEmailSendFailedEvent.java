package KMA.TTCS.CommonService.event.AccountProfile;

import java.util.Date;

public class OTPEmailSendFailedEvent {
    String idOTP;
    String idAccount;
    String idProfile;
    String otp;
    String email;
    Date executeAt;

    boolean status;



    public OTPEmailSendFailedEvent() {
    }

    public OTPEmailSendFailedEvent(String idOTP, String idAccount, String idProfile, String otp, String email, Date executeAt) {
        this.idOTP = idOTP;
        this.idAccount = idAccount;
        this.idProfile = idProfile;
        this.otp = otp;
        this.email = email;
        this.executeAt = executeAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdOTP() {
        return idOTP;
    }

    public void setIdOTP(String idOTP) {
        this.idOTP = idOTP;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Date executeAt) {
        this.executeAt = executeAt;
    }

    @Override
    public String toString() {
        return "SendEmailOTPCommand{" +
                "idOTP='" + idOTP + '\'' +
                ", idAccount='" + idAccount + '\'' +
                ", idProfile='" + idProfile + '\'' +
                ", otp='" + otp + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
