package TTCS.ProfileService.application.Query.Query;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ProfileQueryGetAll {
    int pageNo;
    int pageSize;
}
