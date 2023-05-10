package gotcha.server.Domain.Notifications;

import javax.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    @Column(name="message")
    private String message;

    @Column(name="senderEmail")
    private String senderEmail;

    @Column(name="wasSeen")
    private boolean wasSeen;

    public Notification(String senderEmail, String message) {
        this.senderEmail = senderEmail;
        this.wasSeen = false;
        this.message = message;

    }

    public Notification(){}

    public boolean isWasSeen() {
        return wasSeen;
    }

    public void setWasSeen(boolean wasSeen) {
        this.wasSeen = wasSeen;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String print() {
        return senderEmail + " sent you a message";
    }

}
