package TTCS.ProfileService.presentation.query.dto.response;

import TTCS.ProfileService.domain.enumType.TypeRelationship;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SearchProfileResponse {
    String urlAvt;
    String fullName;
    TypeRelationship typeRelationship;


}
