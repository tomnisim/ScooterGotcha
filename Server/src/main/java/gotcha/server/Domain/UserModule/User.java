package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.QuestionsModule.Question;
import gotcha.server.Utils.Observer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class User implements Observer {

    private String userEmail;
    private String userPasswordToken;
    private String phoneNumber;
    private String gender;
    private LocalDate birthDay;
    private Map<Integer, Question> userQuestions;

    private boolean loggedIn;

    public User(String userEmail, String userPasswordToken, String phoneNumber, LocalDate birthDay, String gender) {
        this.userEmail = userEmail;
        this.userPasswordToken = userPasswordToken;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.loggedIn = false;
        this.userQuestions = new HashMap<>();
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

    public String get_password_token() {return  this.userPasswordToken;}

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
