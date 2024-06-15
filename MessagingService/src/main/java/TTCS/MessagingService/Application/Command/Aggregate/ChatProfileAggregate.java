package TTCS.MessagingService.Application.Command.Aggregate;

import KMA.TTCS.CommonService.command.AccountProfileCommand.AccountRollBackCommand;
import KMA.TTCS.CommonService.command.AccountProfileCommand.ChatProfileCreateCommand;
import KMA.TTCS.CommonService.command.AccountProfileCommand.ChatProfileRollbackCommand;
import KMA.TTCS.CommonService.command.IdentityMessage.ConnectChatProfileCommand;
import KMA.TTCS.CommonService.command.IdentityMessage.DisConnectChatCommand;
import KMA.TTCS.CommonService.event.AccountProfile.AccountRollBackEvent;
import KMA.TTCS.CommonService.event.AccountProfile.ChatProfileCreateEvent;
import KMA.TTCS.CommonService.event.AccountProfile.ChatProfileRollbackEvent;
import KMA.TTCS.CommonService.event.IdentityMessage.ConnectChatProfileEvent;
import KMA.TTCS.CommonService.event.IdentityMessage.DisConnectChatEvent;
import TTCS.MessagingService.Application.Command.CommandEvent.Command.ConnectCommand;
import TTCS.MessagingService.Domain.Model.ChatProfile;
import TTCS.MessagingService.Domain.Model.Status;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Slf4j
@Aggregate
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatProfileAggregate {
    @AggregateIdentifier
    String idChatProfile;
    String idProfile;
    Status status;

    public ChatProfileAggregate(){

    }

    @CommandHandler
    public ChatProfileAggregate(ChatProfileCreateCommand command){
        ChatProfileCreateEvent event = new ChatProfileCreateEvent();
        event.setIdProfile(command.getIdProfile());
        event.setIdChatProfile(command.getIdChatProfile());
        event.setEmail(command.getEmail());
        event.setIdAccount(command.getIdAccount());
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(ChatProfileCreateEvent event){
        this.idChatProfile = event.getIdChatProfile();
        this.idProfile = event.getIdProfile();
    }

    @CommandHandler
    public void handle(ChatProfileRollbackCommand command) {
        System.out.println("ChatProfileRollbackCommand "+command.getIdChatProfile());
        ChatProfileRollbackEvent event = new ChatProfileRollbackEvent();
        event.setIdChatProfile(command.getIdChatProfile());
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(ChatProfileRollbackEvent event) {
        System.out.println("ChatProfileRollbackEvent "+event.getIdChatProfile());
        this.idChatProfile = event.getIdChatProfile();
    }



    @CommandHandler
    public void handle(ConnectChatProfileCommand command){
        ConnectChatProfileEvent event = new ConnectChatProfileEvent();
        event.setIdChatProfile(command.getIdChatProfile());
        event.setStatus(Status.ONLINE.toString());
        System.out.println("here");
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void on(ConnectChatProfileEvent event){
        this.idChatProfile = event.getIdChatProfile();
        this.status = Status.valueOf(event.getStatus());
    }

    @CommandHandler
    public void handle(DisConnectChatCommand command){
        DisConnectChatEvent event = new DisConnectChatEvent();
        event.setIdChatProfile(command.getIdChatProfile());
        event.setStatus(Status.OFFLINE.toString());
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void on(DisConnectChatEvent event){
        this.idChatProfile = event.getIdChatProfile();
        this.status = Status.valueOf(event.getStatus());
    }
}
