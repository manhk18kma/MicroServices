package TTCS.MessagingService.infrastructure.persistence.Repository;

import TTCS.MessagingService.Domain.Model.ChatDual;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatDualRepository   extends MongoRepository<ChatDual, String> {
}
