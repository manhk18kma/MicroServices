package TTCS.NotificationService.infrastructure.messaging;

import KMA.TTCS.CommonService.model.ProfileNotificationResponse;
import KMA.TTCS.CommonService.notification.NotificationInfor;
import KMA.TTCS.CommonService.query.ProfileNotificationQuery;
import TTCS.NotificationService.Domain.Model.NotificationProfile;
import TTCS.NotificationService.application.Exception.AppException.AppErrorCode;
import TTCS.NotificationService.application.Exception.AppException.AppException;
import TTCS.NotificationService.infrastructure.persistence.Repository.NotificationProfileRepository;
import TTCS.NotificationService.presentation.DTO.CheckNotificationResponse;
import TTCS.NotificationService.presentation.DTO.NotificationResponse;
import TTCS.NotificationService.presentation.DTO.PageResponse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class NotificationProfileService {
    final NotificationProfileRepository notificationProfileRepository;
    final QueryGateway queryGateway;
     final FirebaseMessaging firebaseMessaging;
     final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifications");;
    final  String tokenDevice = "c_Ay7xhzlbwMUhI2NzD0t-:APA91bFnKFGncR08tKd4agJOrTjZwFr4LgmXrDXQVrZTnlhwF449savVceTyILTYu3EOCQpgl8ySzgLMTHlhFIuPZC5YZfFrj4Hs2uxjEZRk48_Lb4od1HzgxRMzqgKpqQNREyY4cwap";

    @KafkaListener(topics = "create_follow_topic", id = "notification_info_follow")
    public void receiveNotificationInfoFollow(NotificationInfor notificationInfor) {
        System.out.println("FOLLOW");
        NotificationProfile notificationProfile = new NotificationProfile();
        BeanUtils.copyProperties(notificationInfor, notificationProfile);

        ProfileNotificationQuery profileNotificationQuery = new ProfileNotificationQuery(notificationInfor.getProfileSenderId());
        CompletableFuture<ProfileNotificationResponse> future = queryGateway.query(profileNotificationQuery, ResponseTypes.instanceOf(ProfileNotificationResponse.class));
        ProfileNotificationResponse response = future.join();

        notificationProfile.setMessage(notificationProfile.getMessage());
        notificationProfile.setNotificationId(UUID.randomUUID().toString());
        notificationProfile.setIsChecked(false);

        NotificationProfile notificationProfileSaved =  notificationProfileRepository.save(notificationProfile);
        notificationProfileSaved.setMessage(notificationProfile.getMessage().replace("nameTarget", response.getFullNameProfileSender()));
        sendNotification(notificationProfileSaved, response);
    }

    @KafkaListener(topics = "create_friend_topic", id = "notification_info_friend")
    public void receiveNotificationInfoFriend(NotificationInfor notificationInfor) {
        System.out.println("FRIEND");
        NotificationProfile notificationProfile = new NotificationProfile();
        BeanUtils.copyProperties(notificationInfor, notificationProfile);
        ProfileNotificationQuery profileNotificationQuery = new ProfileNotificationQuery(notificationInfor.getProfileSenderId());
        CompletableFuture<ProfileNotificationResponse> future = queryGateway.query(profileNotificationQuery, ResponseTypes.instanceOf(ProfileNotificationResponse.class));
        ProfileNotificationResponse response = future.join();
        notificationProfile.setMessage(notificationProfile.getMessage());
        notificationProfile.setNotificationId(UUID.randomUUID().toString());
        notificationProfile.setIsChecked(false);

        NotificationProfile notificationProfileSaved =  notificationProfileRepository.save(notificationProfile);
        notificationProfileSaved.setMessage(notificationProfile.getMessage().replace("nameTarget", response.getFullNameProfileSender()));
        sendNotification(notificationProfileSaved , response);



    }

    public PageResponse getNotificationsByIdProfile(int pageNo, int pageSize, String idProfile) {
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<NotificationProfile> page = notificationProfileRepository.findAllByProfileReceiverId(idProfile , pageable);


        List<NotificationResponse> notificationProfiles =
                page.getContent().stream().map(notificationProfile -> {
                    NotificationResponse notificationResponse = new NotificationResponse();
                    BeanUtils.copyProperties(notificationProfile , notificationResponse);
                    ProfileNotificationQuery profileNotificationQuery = new ProfileNotificationQuery(notificationProfile.getProfileSenderId());
                    CompletableFuture<ProfileNotificationResponse> future = queryGateway.query(profileNotificationQuery, ResponseTypes.instanceOf(ProfileNotificationResponse.class));
                    ProfileNotificationResponse response = future.join();
                    notificationResponse.setMessage(notificationProfile.getMessage().replace("nameTarget", response.getFullNameProfileSender()));
                    notificationResponse.setUrlAvtSender(response.getUrlAvtPictureSender());
                    notificationResponse.setIsChecked(notificationProfile.getIsChecked());
                    return notificationResponse;
                }).collect(Collectors.toList());



        return PageResponse.builder()
                .size(pageSize)
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(pageNo)
                .items(notificationProfiles)
                .build();

    }

    public void sendNotification(NotificationProfile notificationProfile, ProfileNotificationResponse response) {
        String title = notificationProfile.getNotificationType().equals("CREATE_FRIEND") ?
                "New friend" : "New follow";

        // Build notification object
//        Notification notification = Notification.builder()
//                .setTitle(title)
//                .setBody(n)
//                .setImage(response.getUrlAvtPictureSender())
//                .build();

        // Build FCM message
        Message message = Message.builder()
                .setToken(tokenDevice)
//                .setNotification(notification)
                .putData("notificationId", notificationProfile.getNotificationId())
                .putData("profileSenderId", notificationProfile.getProfileSenderId())
                .putData("profileReceiverId", notificationProfile.getProfileReceiverId())
                .putData("notificationType", notificationProfile.getNotificationType())
                .putData("timestamp" , String.valueOf(notificationProfile.getTimestamp()))
                .putData("message" , notificationProfile.getMessage())
                .putData("urlAvtSender" , response.getUrlAvtPictureSender())
                .build();

        try {
            System.out.println("here");
            System.out.println(            firebaseMessaging.send(message));
//            String notificationId = databaseReference.push().getKey();
//            databaseReference.child(notificationId).setValueAsync(notification);

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send FCM message", e);
        }
    }

    public CheckNotificationResponse checkNotification(String idProfile, String idNotification) {
        NotificationProfile notificationProfile = notificationProfileRepository.findById(idNotification)
                .orElseThrow(()->new AppException(AppErrorCode.NOTIFICATION_NOT_EXISTED));

        notificationProfile.setIsChecked(true);
        notificationProfileRepository.save(notificationProfile);
        return new CheckNotificationResponse(notificationProfile.getNotificationId());
    }
}
