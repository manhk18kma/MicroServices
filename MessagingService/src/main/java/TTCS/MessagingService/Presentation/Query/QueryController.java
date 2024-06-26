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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/messages")
@Tag(name = "Messaging Query Controller", description = "Endpoints for querying messages and contacts")
public class QueryController {
    final ChatMessageService chatMessageService;
    final ProfileChatService profileChatService;

    @Operation(
            summary = "Get all chats for a profile",
            description = "Retrieve a paginated list of all chat contacts for a specified profile"
    )
    @GetMapping("/{icChatProfile}/chats")
    public ResponseData<PageResponse<?>> getChats(
            @Parameter(description = "Profile ID of the chat", required = true)
            @PathVariable String icChatProfile,
            @Parameter(description = "Page number (default: 0)")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int pageSize
    ){
        PageResponse<List<ContactsResponse>> listContactsResponsePageResponse = chatMessageService.getChats(
                icChatProfile , pageNo , pageSize);
        return new ResponseData<>(HttpStatus.OK.value(), "Chats", new Date(), listContactsResponsePageResponse);
    }

    @Operation(
            summary = "Get all friends for a profile",
            description = "Retrieve a paginated list of all friends for a specified profile"
    )
    @GetMapping("/{icChatProfile}/contacts")
    public ResponseData<PageResponse<?>> getAllFriends(
            @Parameter(description = "Profile ID of the chat", required = true)
            @PathVariable String icChatProfile,
            @Parameter(description = "Page number (default: 0)")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int pageSize
    ){
        PageResponse<?> response = profileChatService.getAllFriends(
                icChatProfile , pageNo , pageSize);
        return new ResponseData<>(HttpStatus.OK.value(), "Friends", new Date(), response);
    }

    @Operation(
            summary = "Get all messages in a chat",
            description = "Retrieve a paginated list of all messages in a specified chat"
    )
    @GetMapping("/{idChat}/messages")
    public ResponseData<PageResponse<?>> getAllMessages(
            @Parameter(description = "Chat ID of the messages", required = false)
            @PathVariable String idChat,
            @Parameter(description = "Page number (default: 0)")
            @RequestParam(defaultValue = "0") int pageNo,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int pageSize
    ){
        String[] strings = chatMessageService.getAllMessagesPrev(idChat);
        PageResponse<List<ChatMessageResponse>> response = chatMessageService.getAllMessages(
                idChat , pageNo , pageSize ,strings);
        return new ResponseData<>(HttpStatus.OK.value(), "Messages", new Date(), response);
    }
}
