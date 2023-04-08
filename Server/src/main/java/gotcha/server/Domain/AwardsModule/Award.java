package gotcha.server.Domain.AwardsModule;

import gotcha.server.Utils.HibernateConverters.StringArrayConverter;

import javax.persistence.*;

@Entity
@Table(name="awards")
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="message", columnDefinition = "TEXT")
    private String message;

    @Column(name="adminEmail", columnDefinition = "VARCHAR")
    private String admin_email;

    @Column(name="emails", columnDefinition = "TEXT")
    @Convert(converter = StringArrayConverter.class)
    private String[] emails;

    public Award(String message, String admin_email, String[] emails){
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
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

    public String[] getEmails() {
        return emails;
    }
}
