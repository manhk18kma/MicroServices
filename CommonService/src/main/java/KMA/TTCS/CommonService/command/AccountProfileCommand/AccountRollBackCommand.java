package KMA.TTCS.CommonService.command.AccountProfileCommand;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AccountRollBackCommand {
    @TargetAggregateIdentifier
    String idAccount;

    public AccountRollBackCommand(String idAccount) {
        this.idAccount = idAccount;
    }

    public AccountRollBackCommand() {
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
