package TTCS.MessagingService.Presentation.Command;

import TTCS.MessagingService.Presentation.DTO.Request.*;
import TTCS.MessagingService.Presentation.DTO.Response.CheckChatResponse;
import TTCS.MessagingService.Presentation.ResponseData;
import TTCS.MessagingService.infrastructure.persistence.Service.ChatMessageService;
import TTCS.MessagingService.infrastructure.persistence.Service.HandleSendMessService;
import TTCS.MessagingService.infrastructure.persistence.Service.ChatRoomService;
import TTCS.MessagingService.infrastructure.persistence.Service.ProfileChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandController {
    final ChatRoomService chatRoomService;
    final HandleSendMessService handleSendMessService;
    final ProfileChatService profileChatService;
    final ChatMessageService chatMessageService;

    @MessageMapping("/messages")
    @Operation(summary = "Send chat message", description = "Sends a chat message to the specified chat room")
    public SendChatRequest sendMessage(@Payload SendChatRequest chatMessageRequest){
        handleSendMessService.sendMessageChat(chatMessageRequest);
        return chatMessageRequest;
    }

    @DeleteMapping("/{idChatProfile}/chats/{idChat}")
    @Operation(summary = "Remove chat", description = "Removes a chat based on the provided chat profile ID and chat ID")
    public ResponseData<?> removeChat(
            @Parameter(description = "ID of the chat profile", example = "123", required = true)
            @PathVariable String idChatProfile,
            @Parameter(description = "ID of the chat", example = "456", required = true)
            @PathVariable String idChat) throws ExecutionException, InterruptedException {
        profileChatService.removeChat(idChat, idChatProfile);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Removed chat successfully", new Date());
    }

    @PostMapping("/{idChatProfile}/chats/{idChat}")
    @Operation(summary = "Check chat request", description = "Checks a chat request based on the provided chat profile ID and chat ID.")
    public ResponseData<?> checkChatRequest(
            @Parameter(description = "ID of the chat profile", example = "123", required = true)
            @PathVariable String idChatProfile,
            @Parameter(description = "ID of the chat", example = "456", required = true)
            @PathVariable String idChat) {
        CheckChatResponse response = chatMessageService.checkChat(idChatProfile, idChat);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Checked chat successfully", new Date(), response);
    }
}



//    @PostMapping("/private-message2")
//    public CreateRoomChatRequest createRoomChatMultipleChatProfiles(@RequestBody CreateRoomChatRequest createRoomChatRequest){
//        chatRoomService.createRoomChatMultipleChatProfiles(createRoomChatRequest);
//        return createRoomChatRequest;
//    }
//
//    @PostMapping("/private-message3")
//    public AddMemberRequest addMemberToChatRoom(@RequestBody AddMemberRequest addMemberRequest){
//        chatRoomService.addMemberToChatRoom(addMemberRequest);
//        return addMemberRequest;
//    }
//
//    @PostMapping("/private-message4")
//    public RemoveMemberRequest addMemberToChatRoom(@RequestBody RemoveMemberRequest removeMemberRequest){
//        chatRoomService.removeMemberFromChatRoom(removeMemberRequest);
//        return removeMemberRequest;
//    }
