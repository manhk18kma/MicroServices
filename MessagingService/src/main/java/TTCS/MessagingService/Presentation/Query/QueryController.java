package TTCS.MessagingService.Presentation.Query;

import TTCS.MessagingService.Domain.Model.ChatMessage;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import TTCS.MessagingService.Presentation.DTO.Response.ChatMessageResponse;
import TTCS.MessagingService.Presentation.DTO.Response.ContactsResponse;
import TTCS.MessagingService.Presentation.DTO.Response.FriendsResponse;
import TTCS.MessagingService.Presentation.PageResponse;
import TTCS.MessagingService.Presentation.ResponseData;
import TTCS.MessagingService.infrastructure.persistence.Service.ChatMessageService;
import TTCS.MessagingService.infrastructure.persistence.Service.ProfileChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/messages")
public class QueryController {
    final ChatMessageService chatMessageService;
    final ProfileChatService profileChatService;


    @GetMapping("/{icChatProfile}/chats")
    public ResponseData<PageResponse<?>> getAllContacts(
            @PathVariable String icChatProfile,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        PageResponse<List<ContactsResponse>> listContactsResponsePageResponse = chatMessageService.getAllContacts(
                icChatProfile , pageNo , pageSize);
        return new ResponseData<>(HttpStatus.OK.value(), "Chats", new Date(), listContactsResponsePageResponse);
    }


    @GetMapping("/{icChatProfile}/contacts")
    public ResponseData<PageResponse<?>> getAllFriends(
            @PathVariable String icChatProfile,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        PageResponse<?> response = profileChatService.getAllFriends(
                icChatProfile , pageNo , pageSize);
        return new ResponseData<>(HttpStatus.OK.value(), "Friends", new Date(), response);
    }


    @GetMapping("/{idChat}/messages")
    public ResponseData<PageResponse<?>> getAllMessages(
            @PathVariable(required = false) String idChat,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        PageResponse<List<ChatMessageResponse>> response = chatMessageService.getAllMessages(
                idChat , pageNo , pageSize);
        return new ResponseData<>(HttpStatus.OK.value(), "Messages", new Date(), response);
    }



}
