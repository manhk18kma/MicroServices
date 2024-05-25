package TTCS.ProfileService.application.Command.CommandEvent.Command.Profile;

import KMA.TTCS.CommonService.enumType.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter
@ToString
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreateAggregate {
    @TargetAggregateIdentifier
     String idProfile;
     String fullName;
     String urlProfilePicture;
     String biography;
     Gender gender;
     Date dateOfBirth;
     String idAccount;
    Date executeAt;

}


