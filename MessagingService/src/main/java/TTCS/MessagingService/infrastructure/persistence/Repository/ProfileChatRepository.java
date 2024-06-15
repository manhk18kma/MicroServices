package TTCS.MessagingService.infrastructure.persistence.Repository;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileChatRepository extends MongoRepository<ChatProfile, String> {
    ChatProfile findByIdProfile(String id);
}
