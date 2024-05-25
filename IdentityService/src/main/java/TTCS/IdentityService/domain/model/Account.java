package TTCS.IdentityService.domain.model;

import TTCS.IdentityService.domain.enumType.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Account implements Serializable {
    @Id
    String idAccount;
    String username;
    String password;
    String email;
    Date createAt;
    Date updateAt;
    UserStatus status;
    String idProfile;
    String secretKey;


}

