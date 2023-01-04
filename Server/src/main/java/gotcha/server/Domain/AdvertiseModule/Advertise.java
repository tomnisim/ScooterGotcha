package gotcha.server.Domain.AdvertiseModule;

import java.time.LocalDateTime;

public class Advertise {
    private int id;
    private LocalDateTime start_date;
    private LocalDateTime final_date;
    private String owner;
    private String message;
    private String photo;
    private int users_clicks;

    public Advertise(int id, LocalDateTime final_date, String owner, String message, String photo) {
        this.id = id;
        this.final_date = final_date;
        this.owner = owner;
        this.message = message;
        this.photo = photo;

        this.start_date = LocalDateTime.now();
        this.users_clicks = 0;
    }

    // load
    public Advertise(int id, LocalDateTime start_date, LocalDateTime final_date, String owner, String message, String photo, int users_clicks) {
        this.id = id;
        this.start_date = start_date;
        this.final_date = final_date;
        this.owner = owner;
        this.message = message;
        this.photo = photo;
        this.users_clicks = users_clicks;
    }


    // TODO: 28/12/2022 : check inputs and set ranges, test after it.

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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUsers_clicks() {
        return users_clicks;
    }

    public void setUsers_clicks(int users_clicks) {
        this.users_clicks = users_clicks;
    }
}
