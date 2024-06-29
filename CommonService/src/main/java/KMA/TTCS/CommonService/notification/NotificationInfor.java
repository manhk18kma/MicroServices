package KMA.TTCS.CommonService.notification;

import java.util.Date;

public class NotificationInfor {
    private String notificationIdToRemove;

    private String profileSenderId;
    private String profileReceiverId;
    private String idTarget;

    private Date timestamp;
    private String message;

    // Constructor


    public NotificationInfor() {
    }

    public NotificationInfor(String notificationIdToRemove, String profileSenderId, String profileReceiverId, String idTarget, Date timestamp, String message) {
        this.notificationIdToRemove = notificationIdToRemove;
        this.profileSenderId = profileSenderId;
        this.profileReceiverId = profileReceiverId;
        this.idTarget = idTarget;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getNotificationIdToRemove() {
        return notificationIdToRemove;
    }

    public void setNotificationIdToRemove(String notificationIdToRemove) {
        this.notificationIdToRemove = notificationIdToRemove;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    public String getProfileSenderId() {
        return profileSenderId;
    }

    public void setProfileSenderId(String profileSenderId) {
        this.profileSenderId = profileSenderId;
    }

    public String getProfileReceiverId() {
        return profileReceiverId;
    }

    public void setProfileReceiverId(String profileReceiverId) {
        this.profileReceiverId = profileReceiverId;
    }



    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FriendCreateNotification{" +
                "profileSenderId='" + profileSenderId + '\'' +
                ", profileReceiverId='" + profileReceiverId + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
