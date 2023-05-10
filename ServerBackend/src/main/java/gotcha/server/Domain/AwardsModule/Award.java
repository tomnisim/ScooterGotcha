package gotcha.server.Domain.AwardsModule;

import gotcha.server.Utils.HibernateConverters.StringArrayConverter;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="awards")
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = 0;

    @Column(name="message")
    private String message;

    @Column(name="adminEmail")
    private String admin_email;

    @Column(name="emails")
    @Convert(converter = StringArrayConverter.class)
    private List<String> emails;

    private LocalDate date;

    /**
     * BL Constructor for new Award, create time -> now().
     * @param message
     * @param admin_email
     * @param emails
     */
    public Award(String message, String admin_email, List<String> emails){
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
        this.date = LocalDate.now();
    }

    /**
     * Constructor for loading DB.
     * @param message
     * @param admin_email
     * @param emails
     * @param date
     */
    public Award(String message, String admin_email, List<String> emails, LocalDate date){
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
        this.date = date;
    }

    public Award(){}

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

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
