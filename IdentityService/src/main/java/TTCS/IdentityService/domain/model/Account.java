package TTCS.IdentityService.domain.model;

import TTCS.IdentityService.domain.enumType.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    String idChatProfile;
    String secretKey;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
     Set<Role> roles = new HashSet<>();


}

