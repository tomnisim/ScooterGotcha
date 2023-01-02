package gotcha.server.Domain.UserModule;

import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

    private static AtomicInteger idCounter = new AtomicInteger();
    private Integer id;

    private Integer question_id;

    private String senderEmail;

    private boolean wasSeen;
    public Notification(String senderEmail, Integer question_id) {
        this.id = idCounter.getAndIncrement();
        this.senderEmail = senderEmail;
        this.wasSeen = false;
        this.question_id = question_id;
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

    public String print() {
        return senderEmail + " sent you a message";
    }

}
