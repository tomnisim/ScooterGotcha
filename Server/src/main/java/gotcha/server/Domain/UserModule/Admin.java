package gotcha.server.Domain.UserModule;

import java.time.LocalDate;

public class Admin extends User{
    private Admin appintedBy;
    private LocalDate appointmentDate;

    public Admin(String userEmail, String userPasswordToken, String phoneNumber, LocalDate birthDay, String gender, Admin appointedBy) {
        super(userEmail, userPasswordToken, phoneNumber, birthDay, gender);
        this.appintedBy = appointedBy;
        this.appointmentDate = LocalDate.now();
    }

    public Admin(User user, Admin appointedBy) {
        this(user.get_email(), user.get_password_token(), user.get_phone_number(), user.get_birth_day(), user.get_gender(), appointedBy);
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
