package TTCS.MessagingService.infrastructure.persistence.Repository;
import TTCS.MessagingService.Domain.Model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
}
