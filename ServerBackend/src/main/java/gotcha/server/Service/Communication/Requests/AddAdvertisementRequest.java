package gotcha.server.Service.Communication.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddAdvertisementRequest {
    private String url;
    private String owner;
    private String photo;
    private String message;
    private LocalDate final_date;
    public AddAdvertisementRequest(){}

    public AddAdvertisementRequest(String url, String owner, String photo, String message, String final_date) {
        this.url = url;
        this.owner = owner;
        this.photo = photo;
        this.message = message;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.final_date = LocalDate.parse(final_date, formatter);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getFinal_date() {
        return final_date;
    }

    public void setFinal_date(LocalDate final_date) {
        this.final_date = final_date;
    }

    @Override
    public String toString() {
        return "AddAdvertisementRequest{" +
                "url='" + url + '\'' +
                ", owner='" + owner + '\'' +
                ", photo='" + photo + '\'' +
                ", message='" + message + '\'' +
                ", final_date=" + final_date +
                '}';
    }
}