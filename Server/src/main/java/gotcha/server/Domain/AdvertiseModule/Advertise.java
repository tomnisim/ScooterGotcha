package gotcha.server.Domain.AdvertiseModule;

import gotcha.server.Utils.Utils;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "advertises")
public class Advertise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="startDate")
    private LocalDateTime start_date;

    @Column(name="finalDate")
    private LocalDateTime final_date;

    @Column(name = "owner", columnDefinition = "VARCHAR")
    private String owner;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "photo", columnDefinition = "TEXT")
    private String photo;

    @Column(name = "url", columnDefinition = "VARCHAR")
    private String url;

    @Column(name = "userClicks")
    private int users_clicks;

    public Advertise(LocalDateTime final_date, String owner, String message, String photo, String url) {
        this.final_date = final_date;
        this.owner = owner;
        this.message = message;
        this.photo = photo;
        this.start_date = LocalDateTime.now();
        this.url = url;
        this.users_clicks = 0;
    }

    // load
    public Advertise(int id, LocalDateTime start_date, LocalDateTime final_date, String owner, String message, String photo,String url, int users_clicks) {
        this.id = id;
        this.start_date = start_date;
        this.final_date = final_date;
        this.owner = owner;
        this.message = message;
        this.photo = photo;
        this.url = url;
        this.users_clicks = users_clicks;
    }

    public Advertise(){}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getFinal_date() {
        return final_date;
    }

    public void setFinal_date(LocalDateTime final_date) {
        this.final_date = final_date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) throws Exception {
        if (!Utils.isValidURL(url))
            throw new IllegalArgumentException("illegal url");
        this.url = url;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUsers_clicks() {
        return users_clicks;
    }

    public void setUsers_clicks(int users_clicks) throws Exception {
        if (users_clicks < 0)
            throw new IllegalArgumentException("users clicks must be non-negative");
        this.users_clicks = users_clicks;
    }

    public String toString_admin() {
        return "Advertise{" +
                "id=" + id +
                ", start_date=" + start_date +
                ", final_date=" + final_date +
                ", owner='" + owner + '\'' +
                ", message='" + message + '\'' +
                ", photo='" + photo + '\'' +
                ", url='" + url + '\'' +
                ", users_clicks=" + users_clicks +
                '}';
    }

    public String toString_user() {
        return "Advertise{" +
                ", message='" + message + '\'' +
                ", photo='" + photo + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
