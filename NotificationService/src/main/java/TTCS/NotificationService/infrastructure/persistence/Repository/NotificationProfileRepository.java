package TTCS.NotificationService.infrastructure.persistence.Repository;

import TTCS.NotificationService.Domain.Model.EmailOTP;
import TTCS.NotificationService.Domain.Model.NotificationProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationProfileRepository   extends MongoRepository<NotificationProfile, String> {

    Page<NotificationProfile> findAllByProfileReceiverId(String idAccount , Pageable pageable );

    List<NotificationProfile> findByProfileReceiverIdAndIsChecked(String receiverId, boolean isChecked);

    void deleteAllByNotificationIdToRemove(String id);

}
