package TTCS.MessagingService.infrastructure.persistence.Repository;
import TTCS.MessagingService.Domain.Model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {


//    Page<ChatMessage> findAllByIdChatRoomChatDual(String idChatRoomOrChatDual ,  Pageable pageable);

//    Page<ChatMessage> findAllByIdChatRoomChatDualOrderByTimeStampDesc(String idChatRoomOrChatDual ,  Pageable pageable);

    Page<ChatMessage> findAllByIdChatRoomChatDualOrderByTimeStampDesc(String idChatRoomOrChatDual, Pageable pageable);


//    List<ChatMessage> findByIdChatRoomChatDual(String idChatRoomOrChatDual );

}
