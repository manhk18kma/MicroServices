package KMA.TTCS.CommonService.model;

public class ProfileNotificationResponse {
    String fullNameProfileSender;
    String urlAvtPictureSender;

    public ProfileNotificationResponse(String fullNameProfileSender, String urlAvtPictureSender) {
        this.fullNameProfileSender = fullNameProfileSender;
        this.urlAvtPictureSender = urlAvtPictureSender;
    }

    public String getFullNameProfileSender() {
        return fullNameProfileSender;
    }

    public void setFullNameProfileSender(String fullNameProfileSender) {
        this.fullNameProfileSender = fullNameProfileSender;
    }

    public String getUrlAvtPictureSender() {
        return urlAvtPictureSender;
    }

    public void setUrlAvtPictureSender(String urlAvtPictureSender) {
        this.urlAvtPictureSender = urlAvtPictureSender;
    }
}
