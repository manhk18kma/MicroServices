package TTCS.PostService.Database.Messaging;

import KMA.TTCS.CommonService.command.PostNotification.RemoveNotificationCommand;
import KMA.TTCS.CommonService.notification.NotificationInfor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.NoHandlerForCommandException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessagingService {
     KafkaTemplate<String , Object> kafkaTemplate;
     CommandGateway commandGateway;




    public void sendToKafka(String message, String idProfileSender,String idProfileReceiver,String idPost , String topic , String idToRemove) {
        NotificationInfor notificationInfor = buildNotificationInfor(idProfileSender , idProfileReceiver ,message ,idPost , idToRemove );
        kafkaTemplate.send(topic , notificationInfor);
    }


    public void sendDataToServiceB(String idToRemove) {
        try {
            commandGateway.sendAndWait(new RemoveNotificationCommand(idToRemove));
        } catch (NoHandlerForCommandException e) {
//            System.err.println("Error sending command to Service B: " + e.getMessage());
//            e.printStackTrace();
            kafkaTemplate.send("remove_notification_topic", new RemoveNotificationCommand(idToRemove));
        }
//        catch (Exception e) {
//            System.err.println("Unexpected error: " + e.getMessage());
//            e.printStackTrace();
//        }
    }
    private NotificationInfor buildNotificationInfor(String idProfileSender,String idProfileReceiver , String message , String idTarget ,  String idToRemove){
        NotificationInfor notificationInfor = new NotificationInfor();
        notificationInfor.setProfileSenderId(idProfileSender);
        notificationInfor.setIdTarget(idTarget);
        notificationInfor.setTimestamp(new Date());
        notificationInfor.setMessage(message);
        notificationInfor.setNotificationIdToRemove(idToRemove);
        if(idProfileReceiver!=null){
            notificationInfor.setProfileReceiverId(idProfileReceiver);
        }

        return notificationInfor;

    }
}
