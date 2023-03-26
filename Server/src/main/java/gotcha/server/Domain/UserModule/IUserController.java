package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.AdvertiseModule.Advertise;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Observable;

import java.time.LocalDate;
import java.util.List;

public interface IUserController extends Observable {
    User get_user_by_email(String userEmail) throws UserNotFoundException;
    List<User> get_all_users();
    Boolean register(String userEmail, String password,String name, String lastName, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber) throws Exception;
    User login(String userEmail, String password) throws Exception;
    void logout(String userEmail) throws Exception;
    void appoint_new_admin(String newAdminEmail,String name, String lastName, String password, String phoneNumber, LocalDate birthDay, String gender, String appointingAdminEmail) throws Exception;
    void reply_to_user_question(String adminEmail, String reply, int question_id) throws Exception;
    void send_question_to_admin(String userEmail, String message) throws Exception;
    List<Admin> view_admins();
    void change_password(String userEmail, String oldPassword, String newPassword) throws Exception;
    void remove_admin_appointment(String user_email, String admin_email) throws Exception;
    void delete_user(String user_email) throws Exception ;
    void notify_all_users(String senderEmail, String message) throws Exception;
    void notify_users(String senderEmail, String[] emails, String message);
    // user id -> user email
    void update_user_rate(String user_id, Ride ride, int number_of_rides) throws Exception;

}
