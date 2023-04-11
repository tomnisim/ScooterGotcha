package gotcha.server.Domain.AwardsModule;

import java.time.LocalDate;
import java.util.List;

public class Award {
    private int id;
    private String message;
    private String admin_email;
    private List<String> emails;

    private LocalDate date;

    /**
     * BL Constructor for new Award, create time -> now().
     * @param id
     * @param message
     * @param admin_email
     * @param emails
     */
    public Award(int id, String message, String admin_email, List<String> emails){
        this.id = id;
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
        this.date = LocalDate.now();
    }

    /**
     * Constructor for loading DB.
     * @param id
     * @param message
     * @param admin_email
     * @param emails
     * @param date
     */
    public Award(int id, String message, String admin_email, List<String> emails, LocalDate date){
        this.id = id;
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public List<String> getEmails() {
        return emails;
    }

    public LocalDate getDate() {
        return date;
    }
}
