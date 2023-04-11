package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;

import java.time.LocalDate;
import java.util.Map;
/**
 * this DAO is for ADMIN application.
 */
public class AdminDAO {
    private String appointedBy;
    private LocalDate appointmentDate;
    private String userEmail;
    private String phoneNumber;
    private String gender;
    private String name;
    private String lastName;
    private String loggedIn;

    public AdminDAO(){}

    public AdminDAO(Admin admin){
        this.userEmail = admin.get_email();
        this.name = admin.getName();
        this.lastName = admin.getLastName();
        this.phoneNumber = admin.get_phone_number();
        this.gender = admin.get_gender();
        if (admin.get_appointed_by() == null){
            this.appointedBy = "System Appointment";
        }
        else {
            this.appointedBy = admin.get_appointed_by().get_email();
        }
        this.appointmentDate = admin.get_appointment_date();
        if (admin.is_logged_in())
            this.loggedIn = "V";
        else
            this.loggedIn = "X";
    }

    public String getappointedBy() {
        return appointedBy;
    }

    public void setappointedBy(String appointedBy) {
        this.appointedBy = appointedBy;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
    }
}
