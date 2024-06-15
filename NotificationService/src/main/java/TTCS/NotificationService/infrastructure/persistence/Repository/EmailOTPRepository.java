package TTCS.NotificationService.infrastructure.persistence.Repository;

import TTCS.NotificationService.Domain.Model.EmailOTP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailOTPRepository extends MongoRepository<EmailOTP, String> {

    long countAllByIdAccount(String idAccount);
    long countAllBy();
    Page<EmailOTP> findAllByIdAccount(String idAccount , Pageable pageable );


}
