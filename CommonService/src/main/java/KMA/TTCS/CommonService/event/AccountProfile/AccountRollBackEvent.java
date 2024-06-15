package KMA.TTCS.CommonService.event.AccountProfile;



public class AccountRollBackEvent {
    String idAccount;

    public AccountRollBackEvent(String idAccount) {
        this.idAccount = idAccount;
    }

    public AccountRollBackEvent() {
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
