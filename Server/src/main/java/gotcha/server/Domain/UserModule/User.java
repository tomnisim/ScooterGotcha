package gotcha.server.Domain.UserModule;

import java.time.LocalDate;

public abstract class User{

    private String userEmail;
    private String userPassword;
    private String phoneNumber;
    private String gender;
    private LocalDate birthDay;

    private boolean loggedIn;

    public User(String userEmail, String userPassword, String phoneNumber, LocalDate birthDay, String gender) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.loggedIn = false;
    }

    // Empty constructor for DB
    public User() {

    }
    public String get_email() {
        return this.userEmail;
    }

    public String get_phone_number() {
        return this.phoneNumber;
    }

    public String get_gender() {
        return this.gender;
    }

    public LocalDate get_birth_day() {
        return this.birthDay;
    }

    public String get_password() {return  this.userPassword;}

    public boolean is_logged_in() {
        return this.loggedIn;
    }

    public void login(){
        this.loggedIn = true;
    }

    public void logout() {
        this.loggedIn = false;
    }

    public void add_notification(String your_question_got_answered) {
    }

    public User get_appointed_by() throws Exception {
        throw new Exception("User is not appointed by no one");
    }

    public LocalDate get_appointment_date() throws Exception {
        throw new Exception("User is not appointed by no one");
    }

    public Boolean is_admin() {
        return false;
    }
}
