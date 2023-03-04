package gotcha.server.Domain.Notifications;

import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

    // TODO: 01/03/2023 : add message 
    private static AtomicInteger idCounter = new AtomicInteger();


    private Integer id;

    private String message;

    private String senderEmail;

    private boolean wasSeen;
    public Notification(String senderEmail, String message) {
        this.id = idCounter.getAndIncrement();
        this.senderEmail = senderEmail;
        this.wasSeen = false;
        this.message = message;

    }

    public void set_seen() {
        this.wasSeen = true;
    }

    public Boolean is_seen() {
        return this.wasSeen;
    }

    public Integer get_id() {
        return this.id;
    }

    public String get_message() {
        return message;
    }

    public String print() {
        return senderEmail + " sent you a message";
    }

}
