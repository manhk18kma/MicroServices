package TTCS.NotificationService.application.Query.Query;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class EmailOTPQueryGetAll {
    int pageNo;
    int pageSize;
}