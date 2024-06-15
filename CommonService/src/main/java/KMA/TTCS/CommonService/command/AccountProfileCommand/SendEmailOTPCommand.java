package KMA.TTCS.CommonService.command.AccountProfileCommand;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

public class SendEmailOTPCommand {
    @TargetAggregateIdentifier
    String idOTP;
    String idAccount;
    String idProfile;
    String otp;
    String email;

    Date executeAt;
    Date expiredAt;

    public SendEmailOTPCommand() {
    }

    public SendEmailOTPCommand(String idOTP, String idAccount, String idProfile, String otp, String email, Date executeAt) {
        this.idOTP = idOTP;
        this.idAccount = idAccount;
        this.idProfile = idProfile;
        this.otp = otp;
        this.email = email;
        this.executeAt = executeAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Date getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Date executeAt) {
        this.executeAt = executeAt;
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
