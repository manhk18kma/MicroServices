package TTCS.ProfileService.application.Query.Response;

import KMA.TTCS.CommonService.enumType.Gender;
import TTCS.ProfileService.domain.enumType.TypeRelationship;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProfileDetailResponse {
    String idProfile;
    String fullName;
    String urlProfilePicture;
    String biography;
    Gender gender;
    Date dateOfBirth;
    Date updateAt;
    String idAccount;
    TypeRelationship typeRelationship;

}
