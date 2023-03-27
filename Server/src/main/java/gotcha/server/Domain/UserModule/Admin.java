package gotcha.server.Domain.UserModule;

import java.time.LocalDate;

public class Admin extends User{
    private Admin appintedBy;
    private LocalDate appointmentDate;

    public Admin(String userEmail, String name, String lastName, String userPasswordToken, String phoneNumber, LocalDate birthDay, String gender, Admin appointedBy) {
        super(userEmail, name, lastName, userPasswordToken, phoneNumber, birthDay, gender);
        this.appintedBy = appointedBy;
        this.appointmentDate = LocalDate.now();
    }

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
}
