package TTCS.NotificationService.application.Query.Query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class EmailOTPQueryGetByID {
    String idAccount;
    int pageNo;
    int pageSize;
}
