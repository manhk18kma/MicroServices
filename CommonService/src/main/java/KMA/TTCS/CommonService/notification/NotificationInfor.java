package KMA.TTCS.CommonService.notification;

import java.util.Date;

public class NotificationInfor {
    private String profileSenderId;
    private String profileReceiverId;
    private String notificationType;
    private Date timestamp;
    private String message;

    // Constructor


    public NotificationInfor() {
    }

    public NotificationInfor(String profileSenderId, String profileReceiverId, String notificationType, Date timestamp, String message) {
        this.profileSenderId = profileSenderId;
        this.profileReceiverId = profileReceiverId;
        this.notificationType = notificationType;
        this.timestamp = timestamp;
        this.message = message;
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

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
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
                ", notificationType='" + notificationType + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
