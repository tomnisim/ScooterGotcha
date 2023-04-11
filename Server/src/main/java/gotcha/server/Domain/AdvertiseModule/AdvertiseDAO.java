package gotcha.server.Domain.AdvertiseModule;

import java.time.LocalDate;

/**
 * this DAO is for RIDER application.
 */
public class AdvertiseDAO {

    private String message;
    private String photo;
    private String url;

    public AdvertiseDAO(){}
    public AdvertiseDAO(Advertise advertise){
        this.message = advertise.getMessage();
        this.photo = advertise.getPhoto();
        this.url = advertise.getUrl();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
