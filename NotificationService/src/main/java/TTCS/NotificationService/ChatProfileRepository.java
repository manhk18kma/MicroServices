package TTCS.NotificationService;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatProfileRepository extends MongoRepository<ChatProfile, String> {
}
