package gotcha.server.Domain.UserModule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User{
    @ManyToOne
    @JoinColumn(name = "appointed_by_admin_id")
    private Admin appintedBy;

    @Column(name="appointmentDate", nullable = false)
    private LocalDate appointmentDate;

    public Admin(String userEmail, String name, String lastName, String userPasswordToken, String phoneNumber, LocalDate birthDay, String gender, Admin appointedBy) {
        super(userEmail, name, lastName, userPasswordToken, phoneNumber, birthDay, gender);
        this.appintedBy = appointedBy;
        this.appointmentDate = LocalDate.now();
    }

    public Admin() {super();}

    @Override
    public Boolean is_admin() {
        return true;
    }

    public User get_appointed_by() {
        return this.appintedBy;
    }

    public LocalDate get_appointment_date() {
        return this.appointmentDate;
    }

    public void setAppintedBy(Admin appintedBy) {
        this.appintedBy = appintedBy;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
