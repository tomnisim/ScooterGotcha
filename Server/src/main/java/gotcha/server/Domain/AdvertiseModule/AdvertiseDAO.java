package gotcha.server.Domain.AdvertiseModule;

import java.time.LocalDate;

/**
 * this DAO is for RIDER application.
 */
public class AdvertiseDAO {

    private String message;
    private String photo;
    private String url;
    private int id;

    public AdvertiseDAO(){}
    public AdvertiseDAO(Advertise advertise){
        this.message = advertise.getMessage();
        this.photo = advertise.getPhoto();
        this.url = advertise.getUrl();
        this.id = advertise.getId();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
