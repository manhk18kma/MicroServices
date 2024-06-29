package KMA.TTCS.CommonService.command.PostNotification;

public class RemoveNotificationCommand {
    private String notificationIdToRemove;

    public RemoveNotificationCommand() {
    }

    public RemoveNotificationCommand(String notificationIdToRemove) {
        this.notificationIdToRemove = notificationIdToRemove;
    }

    public String getNotificationIdToRemove() {
        return notificationIdToRemove;
    }

    public void setNotificationIdToRemove(String notificationIdToRemove) {
        this.notificationIdToRemove = notificationIdToRemove;
    }
}
