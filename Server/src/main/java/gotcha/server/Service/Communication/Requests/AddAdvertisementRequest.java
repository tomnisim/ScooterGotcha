package gotcha.server.Service.Communication.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AddAdvertisementRequest {
    private String url;
    private String owner;
    private String photo;
    private String message;
    // TODO: 07/04/2023 : BUG when we try to transfer LocalDate / LocalDateTime 
    @JsonFormat(pattern = "yyyy-MM-dd")

    private LocalDate final_date;
    public AddAdvertisementRequest(){}

    public AddAdvertisementRequest(String url, String owner, String photo, String message, LocalDate final_date) {
        this.url = url;
        this.owner = owner;
        this.photo = photo;
        this.message = message;
        this.final_date = final_date;
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