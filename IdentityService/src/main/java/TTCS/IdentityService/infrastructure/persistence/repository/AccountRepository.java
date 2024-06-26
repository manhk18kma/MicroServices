package TTCS.IdentityService.infrastructure.persistence.repository;

import TTCS.IdentityService.domain.model.Account;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Repository
@Hidden
//@RepositoryRestResource(path = "account")
public interface AccountRepository extends JpaRepository<Account , String> {
    Account findByEmail(String email);
    boolean existsAccountByEmail(String email);
    boolean existsAccountByUsername(String username);
    Account findByUsername(String username);
    Account findByIdProfile(String username);



    Page<Account> findAllBy(Pageable pageable);

    Optional<Account> findById(String id);

    int countAllBy();

}
