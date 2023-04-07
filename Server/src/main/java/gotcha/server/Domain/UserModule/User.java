package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class
User implements Observer {

    private String userEmail;
    private String userPasswordToken;
    private String phoneNumber;
    private String gender;
    private LocalDate birthDay;
    private String name;
    private String lastName;
    private Map<Integer, Notification> userNotifications;
    private boolean loggedIn;

    public User(String userEmail,String name, String lastName, String userPasswordToken, String phoneNumber, LocalDate birthDay, String gender) {
        this.userEmail = userEmail;
        this.userPasswordToken = userPasswordToken;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.loggedIn = false;
        this.userNotifications = new HashMap<>();
        this.name = name;
        this.lastName = lastName;
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

    public void change_password_token(String newToken){
        this.userPasswordToken = newToken;
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

    public synchronized void login(){
        this.loggedIn = true;
    }

    public synchronized void logout() throws Exception {
        if(!loggedIn)
        {
            var message = String.format("User with email %s is NOT logged in", userEmail);
            throw new Exception(message);
        }
        this.loggedIn = false;
    }

    /**
     * adds the notification to the list of notification and tries to notify the user
     * @param notification
     * @return
     */
    public boolean notify_user(Notification notification) {
        boolean wasAdded = userNotifications.putIfAbsent(notification.get_id(), notification) == null;
        if (!wasAdded) {
            return false;
        }
        return true;

    }

    /**
     * function that will be called when user log in to show him all of his not seen notifications
     * @throws Exception
     */
    public void notify_user() throws Exception {
        for(var notification : userNotifications.values()) {
            if (!notification.is_seen()) {
                notify_user(notification);
            }
        }
    }

    public Collection<Notification> get_notifications(){
        return this.userNotifications.values();
    }
    public Boolean is_admin() {
        return false;
    }

    public void edit_details(String phoneNumber, String gender) {
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
