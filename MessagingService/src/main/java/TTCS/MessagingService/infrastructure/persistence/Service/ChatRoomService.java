package TTCS.MessagingService.infrastructure.persistence.Service;

import TTCS.MessagingService.Domain.Model.ChatDual;
import TTCS.MessagingService.Domain.Model.ChatMessage;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import TTCS.MessagingService.Domain.Model.ChatRoom;
import TTCS.MessagingService.Presentation.DTO.Request.*;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatMessageRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatRoomRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ProfileChatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoomService {
    final ChatMessageRepository chatMessageRepository;
    final ProfileChatRepository profileChatRepository;
    final ChatRoomRepository chatRoomRepository;
    final QueryGateway queryGateway;



//    public void sendMessageChatRoom(SendChatRoomRequest sendChatRoomRequest) {
//        ChatProfile chatProfileSender = profileChatRepository.findById(sendChatRoomRequest.getIdChatProfileSender())
//                .orElseThrow(() -> new RuntimeException("Chat profile sender not found"));
//
//        ChatRoom chatRoom = chatRoomRepository.findById(sendChatRoomRequest.getIdChatRoom())
//                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
//
//        ChatMessage chatMessage = ChatMessage.builder()
//                .idChatMessage(UUID.randomUUID().toString())
//                .content(sendChatRoomRequest.getContent())
//                .idChatRoomOrChatDual(chatRoom.getIdChatRoom())
//                .idChatProfileSender(chatProfileSender.getIdChatProfile())
//                .timeStamp(new Date())
//                .build();
//
//
//        chatRoom.getIdMessages().add(chatMessage.getIdChatMessage());
//        updateLastUsed(chatRoom);
//
//        chatRoomRepository.save(chatRoom);
//        chatMessageRepository.save(chatMessage);
//    }

    public void createRoomChatMultipleChatProfiles(CreateRoomChatRequest createRoomChatRequest){
        ChatProfile chatProfileCreate = profileChatRepository.findById(createRoomChatRequest.getIdChatProfileCreate())
                .orElseThrow(() -> new RuntimeException("Chat profile sender not found"));

        ChatRoom chatRoom = ChatRoom.builder()
                .idChatRoom(UUID.randomUUID().toString())
                .chatRoomName(createRoomChatRequest.getChatRoomName())
                .idChatProfiles(new HashSet<>())
//                .idMessages(new HashSet<>())
                .urlImageChatRoom("https://cdn-icons-png.flaticon.com/512/6387/6387947.png")
                .build();
        chatRoom.getIdChatProfiles().add(createRoomChatRequest.getIdChatProfileCreate());
        createRoomChatRequest.getIdChatProfileMembers().stream().forEach(id->{
            chatRoom.getIdChatProfiles().add(id);
        });
        updateLastUsed(chatRoom);
        ChatMessage addedMessage = createMessage(chatProfileCreate.getIdProfile() , chatRoom.getIdChatRoom() , "Message");
//        chatRoom.getIdMessages().add(addedMessage.getIdChatMessage());
        chatRoomRepository.save(chatRoom);
        chatMessageRepository.save(addedMessage);

    }


    public void addMemberToChatRoom(AddMemberRequest addMemberRequest){
        ChatProfile chatProfileAdder = profileChatRepository.findById(addMemberRequest.getIdChatProfileAdder())
                .orElseThrow(() -> new RuntimeException("Chat profile sender not found"));
        ChatProfile chatProfileTarget = profileChatRepository.findById(addMemberRequest.getIdChatProfileTarget())
                .orElseThrow(() -> new RuntimeException("Chat profile sender not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(addMemberRequest.getIdChatRoom())
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        ChatMessage message = createMessage(chatProfileAdder.getIdProfile() , chatRoom.getIdChatRoom() , "Message");
//        chatRoom.getIdMessages().add(message.getIdChatMessage());

        chatRoom.getIdChatProfiles().add(chatProfileTarget.getIdChatProfile());
        updateLastUsed(chatRoom);
        chatRoomRepository.save(chatRoom);
        chatMessageRepository.save(message);


    }

    public void removeMemberFromChatRoom(RemoveMemberRequest removeMemberRequest){

        ChatProfile chatProfileRemover = profileChatRepository.findById(removeMemberRequest.getIdChatProfileRemover())
                .orElseThrow(() -> new RuntimeException("Chat profile sender not found"));
        ChatProfile chatProfileTarget = profileChatRepository.findById(removeMemberRequest.getIdChatProfileTarget())
                .orElseThrow(() -> new RuntimeException("Chat profile sender not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(removeMemberRequest.getIdChatRoom())
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        ChatMessage message = createMessage(chatProfileRemover.getIdProfile() , chatRoom.getIdChatRoom() , "message");
        updateLastUsed(chatRoom);
        chatRoom.getIdChatProfiles().remove(chatProfileTarget.getIdChatProfile());
        chatMessageRepository.save(message);
        chatRoomRepository.save(chatRoom);

    }

    private void updateLastUsed(ChatRoom chatRoom){
        chatRoom.getIdChatProfiles().forEach(id -> {
            if (id != null) {
                ChatProfile chatProfileInRoom = profileChatRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Chat profile not found for ID "+id));
                chatProfileInRoom.getChatRoomLastUsed().put(chatRoom.getIdChatRoom(), new Date());
                chatProfileInRoom.getChatRoomChecked().put(chatRoom.getIdChatRoom() , false);
                profileChatRepository.save(chatProfileInRoom);
            }
        });
    }

    private ChatMessage createMessage(String IdChatProfileAdder, String chatRoomId , String content) {
        return ChatMessage.builder()
                .idChatMessage(UUID.randomUUID().toString())
                .content(content)
                .idChatRoomChatDual(chatRoomId)
                .idChatProfileSender(IdChatProfileAdder)
                .timeStamp(new Date())
                .build();
    }


}
