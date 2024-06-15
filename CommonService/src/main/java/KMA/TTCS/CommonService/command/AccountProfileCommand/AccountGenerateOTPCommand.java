package KMA.TTCS.CommonService.command.AccountProfileCommand;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
public class AccountGenerateOTPCommand {
    @TargetAggregateIdentifier
    String idAccount;

    public AccountGenerateOTPCommand() {
    }

    public AccountGenerateOTPCommand(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
