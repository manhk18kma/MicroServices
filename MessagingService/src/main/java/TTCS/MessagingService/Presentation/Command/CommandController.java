package TTCS.MessagingService.Presentation.Command;

import TTCS.MessagingService.Presentation.DTO.Request.*;
import TTCS.MessagingService.infrastructure.persistence.Service.HandleSendMessService;
import TTCS.MessagingService.infrastructure.persistence.Service.ChatRoomService;
import TTCS.MessagingService.infrastructure.persistence.Service.ProfileChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandController {
    final ChatRoomService chatRoomService;
    final HandleSendMessService handleSendMessService;
    final ProfileChatService profileChatService;

    @MessageMapping("/messages")
    public SendChatRequest sendMessage(@Payload SendChatRequest chatMessageRequest){
        handleSendMessService.sendMessageChat(chatMessageRequest);
        return chatMessageRequest;
    }


    @PostMapping("/read-status")
    public CheckChatRequest sendCHat(@Payload CheckChatRequest checkChatRequest){
        profileChatService.checkChat(checkChatRequest);
        return checkChatRequest;
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
