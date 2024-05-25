package TTCS.IdentityService.application.Query.Query;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class AccountQueryGetAll {
    int pageNo;
    int pageSize;
}
