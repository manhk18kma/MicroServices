package TTCS.MessagingService.infrastructure.persistence.Service;

import KMA.TTCS.CommonService.model.ProfileMessageResponse;
import KMA.TTCS.CommonService.query.ProfileMessageQuery;
import TTCS.MessagingService.Application.Exception.AppException.AppErrorCode;
import TTCS.MessagingService.Application.Exception.AppException.AppException;
import TTCS.MessagingService.Domain.Model.ChatDual;
import TTCS.MessagingService.Domain.Model.ChatMessage;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import TTCS.MessagingService.Domain.Model.ChatRoom;
import TTCS.MessagingService.Presentation.DTO.Request.CheckChatRequest;
import TTCS.MessagingService.Presentation.DTO.Response.ChatMessageResponse;
import TTCS.MessagingService.Presentation.DTO.Response.CheckChatResponse;
import TTCS.MessagingService.Presentation.DTO.Response.ContactsResponse;
import TTCS.MessagingService.Presentation.PageResponse;
import TTCS.MessagingService.Presentation.ResponseData;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatDualRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatMessageRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatRoomRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ProfileChatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageService {
    final ChatMessageRepository chatMessageRepository;
    final ProfileChatRepository profileChatRepository;
    final ChatRoomRepository chatRoomRepository;
    final ChatDualRepository chatDualRepository;
    final QueryGateway queryGateway;
    @PreAuthorize("#idChatProfile == authentication.principal.claims['idChatProfile']  and hasRole('USER')")
    public PageResponse<List<ContactsResponse>> getChats(String idChatProfile, int pageNo, int pageSize){
        Optional<ChatProfile> chatProfileOptional = profileChatRepository.findById(idChatProfile);
        if (!chatProfileOptional.isPresent()){
            throw  new AppException(AppErrorCode.CHAT_PROFILE_NOT_EXISTED);
        }
        ChatProfile chatProfile = chatProfileOptional.get();
        System.out.println(chatProfile.getChatRoomLastUsed().size());

        Map<String , Date> sortedMap = sortByValue(chatProfile.getChatRoomLastUsed());
        List<ContactsResponse> contactsResponses = new ArrayList<>();
        int startIndex = pageNo * pageSize;
        int endIndex = Math.min(startIndex + pageSize, chatProfile.getChatRoomLastUsed().size());
        AtomicInteger currentIndex = new AtomicInteger();

        AtomicInteger count = new AtomicInteger();
        sortedMap.forEach((s, date) -> {
            if (currentIndex.get() >= startIndex && currentIndex.get() < endIndex) {
                Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(s);
                Optional<ChatDual> chatDualOptional = chatDualRepository.findById(s);

                if (chatRoomOptional.isPresent()) {
                    if(!chatProfile.getChatRoomChecked().get(s)){
                        count.getAndIncrement();
                    }
                    ChatRoom chatRoom = chatRoomOptional.get();
                    contactsResponses.add(
                            ContactsResponse.builder()
                                    .idChat(chatRoom.getIdChatRoom())
                                    .chatName(chatRoom.getChatRoomName())
                                    .lastUsed(date)
                                    .urlImageChatRoom(chatRoom.getUrlImageChatRoom())
                                    .isChecked(chatProfile.getChatRoomChecked().get(s))
                                    .build()
                    );
                } else if (chatDualOptional.isPresent()) {
                    if(!chatProfile.getChatRoomChecked().get(s)){
                        count.getAndIncrement();
                    }
                    ChatDual chatDual = chatDualOptional.get();
                    String idProfileTarget = chatDual.getIdChatProfile1().equals(chatProfile.getIdChatProfile()) ?
                            chatDual.getIdChatProfile2() : chatDual.getIdChatProfile1();
                    ChatProfile chatProfile1 = profileChatRepository.findById(idProfileTarget)
                            .orElseThrow(() -> new RuntimeException("Chat profile sender not found 2"));
                    ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(chatProfile1.getIdProfile() , -1 , -1);

                    CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
                    ProfileMessageResponse profileMessageResponse = future.join();

                    contactsResponses.add(
                            ContactsResponse.builder()
                                    .idChat(s)
                                    .chatName(profileMessageResponse.getFullName())
                                    .lastUsed(date)
                                    .isChecked(chatProfile.getChatRoomChecked().get(s))
                                    .urlImageChatRoom(profileMessageResponse.getUrlProfilePicture())
                                    .build()
                    );
                } else {
                    throw new RuntimeException("Chat room or dual chat not found");
                }
            }
            currentIndex.set(currentIndex.get() + 1);
        });



        int totalElements = chatProfile.getChatRoomLastUsed().size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return PageResponse.<List<ContactsResponse>>builder()
                .size(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .number(pageNo)
                .items(contactsResponses)
                .build();

    }
    private   Map<String, Date> sortByValue(Map<String, Date> map) {
        List<Map.Entry<String, Date>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        Map<String, Date> result = new LinkedHashMap<>();
        for (Map.Entry<String, Date> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }



    @PreAuthorize("(#strings[0] == authentication.principal.claims['idChatProfile'] or #strings[1] == authentication.principal.claims['idChatProfile']) and hasRole('USER')")
    public PageResponse<List<ChatMessageResponse>> getAllMessages(String idChatRoomOrChatDual, int pageNo, int pageSize, String[] strings) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ChatMessage> chatMessages = chatMessageRepository.findAllByIdChatRoomChatDualOrderByTimeStampDesc(idChatRoomOrChatDual, pageable);
        List<ChatMessage> result = chatMessages.getContent();
        List<ChatMessageResponse> responseList = new ArrayList<>();


        result.stream().forEach(chatMessage -> {
            ChatProfile chatProfile = profileChatRepository.findById(chatMessage.getIdChatProfileSender()).get();
        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery( chatProfile.getIdProfile(), -1, -1);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();
            responseList.add(ChatMessageResponse.builder()
                    .idChat(chatMessage.getIdChatRoomChatDual())
                    .idChatMessage(chatMessage.getIdChatMessage())
                    .content(chatMessage.getContent())
                    .idChatProfileSender(chatMessage.getIdChatProfileSender())
                    .fullNameSender(profileMessageResponse.getFullName())
                    .urlAvtSender(profileMessageResponse.getUrlProfilePicture())
                    .timeStamp(chatMessage.getTimeStamp())
                    .build());
        });
        return PageResponse.<List<ChatMessageResponse>>builder()
                .size(pageSize)
                .totalElements((int) chatMessages.getTotalElements())
                .totalPages(chatMessages.getTotalPages())
                .number(pageNo)
                .items( responseList)
                .build();
    }

    public String[] getAllMessagesPrev(String idChat) {
        ChatDual chatDual = chatDualRepository.findById(idChat)
                .orElseThrow(() -> new AppException(AppErrorCode.CHAT_NOT_EXISTED));

        return new String[]{chatDual.getIdChatProfile1(), chatDual.getIdChatProfile2()};
    }



    @PreAuthorize("#idChatProfile == authentication.principal.claims['idChatProfile']  and hasRole('USER')")

    public CheckChatResponse checkChat(String idChatProfile , String idChat) {
        ChatProfile chatProfile = profileChatRepository.findById(idChatProfile).orElseThrow(
                ()->new AppException(AppErrorCode.CHAT_PROFILE_NOT_EXISTED)
        );

        chatProfile.getChatRoomChecked().put(idChat , true);
        profileChatRepository.save(chatProfile);
        return new CheckChatResponse(idChat);
    }
}
