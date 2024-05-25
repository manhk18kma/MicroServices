package TTCS.ProfileService.application.Query.Query;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ProfileQueryGetAllFriend {
    String idProfile;
    int pageNo;
    int pageSize;

}
