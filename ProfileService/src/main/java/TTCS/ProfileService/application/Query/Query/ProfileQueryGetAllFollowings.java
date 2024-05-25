package TTCS.ProfileService.application.Query.Query;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ProfileQueryGetAllFollowings {
    String idProfile;
    int pageNo;
    int pageSize;
}
