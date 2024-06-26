package TTCS.MessagingService.infrastructure.persistence.Service;

import KMA.TTCS.CommonService.PageResponseCom;
import KMA.TTCS.CommonService.model.FriendsResponseCom;
import KMA.TTCS.CommonService.model.ProfileMessageResponse;
import KMA.TTCS.CommonService.query.FriendsQuery;
import KMA.TTCS.CommonService.query.ProfileMessageQuery;
import TTCS.MessagingService.Application.Exception.AppException.AppErrorCode;
import TTCS.MessagingService.Application.Exception.AppException.AppException;
import TTCS.MessagingService.Domain.Model.ChatDual;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import TTCS.MessagingService.Domain.Model.Status;
import TTCS.MessagingService.Presentation.DTO.Request.CheckChatRequest;
import TTCS.MessagingService.Presentation.DTO.Request.ConnectRequest;
import TTCS.MessagingService.Presentation.DTO.Request.DisconnectRequest;
import TTCS.MessagingService.Presentation.DTO.Response.ConnectResponse;
import TTCS.MessagingService.Presentation.DTO.Response.ContactsResponse;
import TTCS.MessagingService.Presentation.DTO.Response.DisconnectResponse;
import TTCS.MessagingService.Presentation.DTO.Response.FriendsResponse;
import TTCS.MessagingService.Presentation.PageResponse;
import TTCS.MessagingService.infrastructure.persistence.Repository.ChatDualRepository;
import TTCS.MessagingService.infrastructure.persistence.Repository.ProfileChatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateEntityNotFoundException;
import org.axonframework.queryhandling.NoHandlerForQueryException;
import org.axonframework.queryhandling.QueryExecutionException;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileChatService {
    final ProfileChatRepository profileChatRepository;
    final QueryGateway queryGateway;
    final SimpMessagingTemplate simpMessagingTemplate;
    final ChatDualRepository chatDualRepository;

    @PreAuthorize("#idChatProfile == authentication.principal.claims['idChatProfile']  and hasRole('USER')")
    public PageResponse<?> getAllFriends(String idChatProfile, int pageNo, int pageSize) {
        Optional<ChatProfile> chatProfileOptional = profileChatRepository.findById(idChatProfile);
        if (!chatProfileOptional.isPresent()){
            throw  new AppException(AppErrorCode.CHAT_PROFILE_NOT_EXISTED);
        }
        ChatProfile chatProfileA = chatProfileOptional.get();

    FriendsQuery friendsQuery = new FriendsQuery(chatProfileA.getIdProfile(), pageNo, pageSize);
    CompletableFuture<PageResponseCom> future = queryGateway.query(friendsQuery, ResponseTypes.instanceOf(KMA.TTCS.CommonService.PageResponseCom.class));

    PageResponseCom<List<FriendsResponseCom>> pageResponseCom;
    try {
        pageResponseCom = future.join();
    } catch (Exception ex) {
        // Xử lý ngoại lệ trong trường hợp không thể lấy dữ liệu
        throw new RuntimeException("Failed to retrieve friends data", ex);
    }
    List<FriendsResponse> friendsResponses =  pageResponseCom.getItems().stream().map(item ->{
        ChatProfile chatProfile = profileChatRepository.findByIdProfile(item.getIdProfile());
        return FriendsResponse.builder()
                .idProfile(item.getIdProfile())
                .idChatProfile(chatProfile.getIdChatProfile())
                .fullName(item.getFullName())
                .urlProfilePicture(item.getUrlProfilePicture())
                .status(chatProfile.getStatus())
                .build();
    }).collect(Collectors.toList());


        return  PageResponse.builder()
           .size(pageResponseCom.getSize())
           .totalElements(pageResponseCom.getTotalElements())
           .totalPages(pageResponseCom.getTotalPages())
           .number(pageResponseCom.getNumber())
           .items(friendsResponses)
           .build();
}
    public ConnectResponse connectProfileChat(ConnectRequest connectRequest) {
        ChatProfile profileConnected = profileChatRepository.findByIdProfile(connectRequest.getIdProfile());
        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(connectRequest.getIdProfile(), 0, 0);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();

            profileConnected.setStatus(Status.ONLINE);
            profileChatRepository.save(profileConnected);

            profileMessageResponse.getSetIdFriends().forEach(id -> {
                ChatProfile chatProfile = profileChatRepository.findByIdProfile(id);
                if (chatProfile != null && chatProfile.getStatus().equals(Status.OFFLINE)) {
                    simpMessagingTemplate.convertAndSendToUser(
                            id.toString(),
                            "/private",
                            profileConnected
                    );
                }
            });

            return ConnectResponse.builder()
                    .idProfile(profileConnected.getIdProfile())
                    .status(Status.ONLINE)
                    .idChatProfile(profileConnected.getIdChatProfile())
                    .fulName(profileMessageResponse.getFullName())
                    .urlAvtPicture(profileMessageResponse.getUrlProfilePicture())
                    .build();}
    public DisconnectResponse disconnectProfileChat(DisconnectRequest disconnectRequest) {
        ChatProfile profileDisConnected = profileChatRepository.findByIdProfile(disconnectRequest.getIdProfile());
        ProfileMessageQuery profileMessageQuery = new ProfileMessageQuery(disconnectRequest.getIdProfile(), 0, 0);
        CompletableFuture<ProfileMessageResponse> future = queryGateway.query(profileMessageQuery, ResponseTypes.instanceOf(ProfileMessageResponse.class));
        ProfileMessageResponse profileMessageResponse = future.join();

        profileDisConnected.setStatus(Status.OFFLINE);
        profileChatRepository.save(profileDisConnected);

        profileMessageResponse.getSetIdFriends().forEach(id -> {
            ChatProfile chatProfile = profileChatRepository.findByIdProfile(id);
            if (chatProfile != null && chatProfile.getStatus().equals(Status.OFFLINE)) {
                simpMessagingTemplate.convertAndSendToUser(
                        id.toString(),
                        "/private",
                        profileDisConnected
                );
            }
        });

        return DisconnectResponse.builder()
                .idProfile(profileDisConnected.getIdProfile())
                .status(profileDisConnected.getStatus())
                .build();}

    @PreAuthorize("#idChatProfile == authentication.principal.claims['idChatProfile']  and hasRole('USER')")
    public void removeChat(String idChat, String idChatProfile) {
        ChatProfile chatProfile = profileChatRepository.findById(idChatProfile)
                .orElseThrow(() -> new AppException(AppErrorCode.CHAT_PROFILE_NOT_EXISTED));

        ChatDual chatDual = chatDualRepository.findById(idChat)
                .orElseThrow(() -> new AppException(AppErrorCode.CHAT_NOT_EXISTED));
        System.out.println(chatProfile.getChatRoomLastUsed().containsKey(chatDual.getIdChatDual()));
        if (!chatProfile.getChatRoomLastUsed().containsKey(chatDual.getIdChatDual())) {
            throw new AppException(AppErrorCode.CHAT_NOT_EXISTED);
        }

        chatProfile.getChatRoomChecked().remove(chatDual.getIdChatDual());
        chatProfile.getChatRoomLastUsed().remove(chatDual.getIdChatDual());
        profileChatRepository.save(chatProfile);
    }

}
