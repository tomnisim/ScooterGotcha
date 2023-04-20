package gotcha.server.Domain.AdvertiseModule;

import gotcha.server.Utils.Utils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;


@Entity
@Table(name = "advertises")
public class Advertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = 0;
    @Column(name="startDate")
    private LocalDate start_date;
    @Column(name="finalDate")
    private LocalDate final_date;

    @Column(name = "owner")
    private String owner;

    @Column(name = "message")
    private String message;

    @Column(name = "photo")
    private String photo;

    @Column(name = "url")
    private String url;

    @Column(name = "userClicks")
    private int users_clicks;

    public Advertise(LocalDate final_date, String owner, String message, String photo, String url) {
        this.final_date = final_date;
        this.owner = owner;
        this.message = message;
        this.photo = photo;
        this.start_date = LocalDate.now();
        this.url = url;
        this.users_clicks = 0;
    }

    // load
    public Advertise(LocalDate start_date, LocalDate final_date, String owner, String message, String photo,String url, int users_clicks) {
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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getFinal_date() {
        return final_date;
    }

    public void setFinal_date(LocalDate final_date) {
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

    public void add_click() {
        this.users_clicks++;
    }
}
