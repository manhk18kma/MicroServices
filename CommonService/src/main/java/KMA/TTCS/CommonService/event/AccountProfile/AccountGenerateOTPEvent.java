package KMA.TTCS.CommonService.event.AccountProfile;

import java.util.Date;


public class AccountGenerateOTPEvent {
    String idAccount;
    String email;
    Date createAt;
    Date expiredAt;
    int otp;

    public AccountGenerateOTPEvent() {
    }

    public AccountGenerateOTPEvent(String idAccount, String email, Date createAt, Date expiredAt, int otp) {
        this.idAccount = idAccount;
        this.email = email;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.otp = otp;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
