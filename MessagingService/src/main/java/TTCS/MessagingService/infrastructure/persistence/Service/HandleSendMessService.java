package TTCS.MessagingService.infrastructure.persistence.Service;

import KMA.TTCS.CommonService.model.ProfileMessageResponse;
import KMA.TTCS.CommonService.query.ProfileMessageQuery;
import TTCS.MessagingService.Domain.Model.ChatDual;
import TTCS.MessagingService.Domain.Model.ChatMessage;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import TTCS.MessagingService.Domain.Model.ChatRoom;
import TTCS.MessagingService.Presentation.DTO.Request.SendChatRequest;
import TTCS.MessagingService.Presentation.DTO.Response.ChatMessageResponse;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatDualRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatMessageRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatRoomRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ProfileChatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HandleSendMessService {
    final ChatDualRepository chatDualRepository;
    final ProfileChatRepository profileChatRepository;
    final ChatMessageRepository chatMessageRepository;
    final ChatRoomRepository chatRoomRepository;
    final SimpMessagingTemplate simpMessagingTemplate;
    final QueryGateway queryGateway;

    public void sendMessageChat(SendChatRequest sendChatRequest)  {
        ChatProfile chatProfileSender = getChatProfileSender(sendChatRequest.getIdChatProfileSender());
        ChatMessage chatMessage = createChatMessage(sendChatRequest.getContent(), chatProfileSender.getIdChatProfile());
        if (sendChatRequest.getIdChat() != null) {
            handleExistingChat(sendChatRequest, chatMessage, chatProfileSender);
        } else {
            handleNewChat(sendChatRequest, chatMessage, chatProfileSender);
        }
    }
    private ChatProfile getChatProfileSender(String idChatProfileSender) {
        return profileChatRepository.findById(idChatProfileSender)
                .orElseThrow(() -> new RuntimeException("Chat profile sender not found"));
    }
    private ChatMessage createChatMessage(String content, String idChatProfileSender) {
        return ChatMessage.builder()
                .idChatMessage(UUID.randomUUID().toString())
                .content(content)
                .idChatRoomChatDual(null)
                .idChatProfileSender(idChatProfileSender)
                .timeStamp(new Date())
                .build();
    }
    private void handleExistingChat(SendChatRequest sendChatRequest, ChatMessage chatMessage, ChatProfile chatProfileSender) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(sendChatRequest.getIdChat());
        Optional<ChatDual> chatDualOptional = chatDualRepository.findById(sendChatRequest.getIdChat());

        if (chatRoomOptional.isPresent()) {
            processChatRoom(chatRoomOptional.get(), chatMessage , chatProfileSender);
        } else if (chatDualOptional.isPresent()) {
            processChatDual(chatDualOptional.get(), sendChatRequest, chatMessage, chatProfileSender);
        }
    }
    private void processChatRoom(ChatRoom chatRoom, ChatMessage chatMessage , ChatProfile chatProfileSender) {
        chatMessage.setIdChatRoomChatDual(chatRoom.getIdChatRoom());
        updateLastUsed(chatRoom , chatProfileSender, chatMessage);
        chatRoomRepository.save(chatRoom);
        chatMessageRepository.save(chatMessage);
    }
    private void processChatDual(ChatDual chatDual, SendChatRequest sendChatRequest, ChatMessage chatMessage, ChatProfile chatProfileSender) {
        chatMessage.setIdChatRoomChatDual(chatDual.getIdChatDual());
        String idProfileTarget = chatDual.getIdChatProfile1().equals(sendChatRequest.getIdChatProfileSender()) ?
                chatDual.getIdChatProfile2() : chatDual.getIdChatProfile1();
        ChatProfile chatProfileReceiver = profileChatRepository.findById(idProfileTarget)
                .orElseThrow(() -> new RuntimeException("Chat profile receiver not found"));


        updateChatProfilesForChatDual(chatDual, chatProfileSender, chatProfileReceiver , chatMessage);
        chatDualRepository.save(chatDual);
        chatMessageRepository.save(chatMessage);
        profileChatRepository.save(chatProfileSender);
        profileChatRepository.save(chatProfileReceiver);


        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery( chatProfileSender.getIdProfile(), -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();
        ChatMessageResponse chatMessageResponse =  ChatMessageResponse.builder()
                .idChatMessage(chatMessage.getIdChatMessage())
                .content(chatMessage.getContent())
                .idChatProfileSender(chatProfileSender.getIdChatProfile())
                .fullNameSender(profileMessageResponse.getFullName())
                .urlAvtSender(profileMessageResponse.getUrlProfilePicture())
                .timeStamp(chatMessage.getTimeStamp()).build();
        simpMessagingTemplate.convertAndSendToUser(chatDual.getIdChatDual(), "/private", chatMessageResponse);

//        simpMessagingTemplate.convertAndSendToUser(chatProfileSender.getIdChatProfile(), "/private", chatMessageResponse);
//        simpMessagingTemplate.convertAndSendToUser(chatProfileReceiver.getIdChatProfile(), "/private", chatMessageResponse);
    }
    private void updateChatProfilesForChatDual(ChatDual chatDual, ChatProfile chatProfileSender, ChatProfile chatProfileReceiver, ChatMessage chatMessage) {
        Date now = new Date();

        chatProfileSender.getChatRoomLastUsed().put(chatDual.getIdChatDual(), now);
        chatProfileReceiver.getChatRoomLastUsed().put(chatDual.getIdChatDual(), now);

        chatProfileSender.getChatRoomChecked().put(chatDual.getIdChatDual(), true);
        chatProfileReceiver.getChatRoomChecked().put(chatDual.getIdChatDual(), false);



    }
    private void handleNewChat(SendChatRequest sendChatRequest, ChatMessage chatMessage, ChatProfile chatProfileSender) {
        ChatProfile chatProfileReceiver = profileChatRepository.findById(sendChatRequest.getIdChatProfileReceiver())
                .orElseThrow(() -> new RuntimeException("Chat profile receiver not found"));

        ChatDual chatDual = createNewChatDual(
                chatProfileSender.getIdChatProfile(),
                chatProfileReceiver.getIdChatProfile()
        );
        chatMessage.setIdChatRoomChatDual(chatDual.getIdChatDual());
        processChatDual(chatDual , sendChatRequest , chatMessage ,chatProfileSender );
    }
    private ChatDual createNewChatDual(String id1 , String id2) {
        String id = UUID.randomUUID().toString();
        ChatDual chatDual = ChatDual.builder()
                .idChatDual(id)
                .idChatProfile1(id1)
                .idChatProfile2(id2)
                .build();
        return chatDual;
    }
    private void updateLastUsed(ChatRoom chatRoom, ChatProfile chatProfileSender, ChatMessage chatMessage) {
    List<ChatProfile> chatProfiles = new ArrayList<>();
    chatRoom.getIdChatProfiles().forEach(id -> {
        ChatProfile chatProfileInRoom = profileChatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat profile not found for ID " + id));

        chatProfiles.add(chatProfileInRoom);

        chatProfileInRoom.getChatRoomLastUsed().put(chatRoom.getIdChatRoom(), new Date());
        chatProfileInRoom.getChatRoomChecked().put(chatRoom.getIdChatRoom(), id.equals(chatProfileSender.getIdChatProfile()));
    });

    profileChatRepository.saveAll(chatProfiles);

    chatProfiles.forEach(profile -> {
        ChatMessageResponse chatMessageResponse =  ChatMessageResponse.builder()
                .idChatMessage(chatMessage.getIdChatMessage())
                .content(chatMessage.getContent())
                .idChatProfileSender(chatProfileSender.getIdChatProfile())
                .timeStamp(chatMessage.getTimeStamp()).build();
        if (profile.getIdChatProfile().equals(chatProfileSender.getIdChatProfile())) {
//            simpMessagingTemplate.convertAndSendToUser(profile.getIdChatProfile(), "/send", chatMessageResponse);
        } else {
            simpMessagingTemplate.convertAndSendToUser(profile.getIdChatProfile(), "/received", chatMessageResponse);
        }
    });
 }
}
