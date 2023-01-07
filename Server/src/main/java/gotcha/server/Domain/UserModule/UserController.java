package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.QuestionsModule.IQuestionController;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Exceptions.UserExceptions.UserAlreadyExistsException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Password.PasswordManagerImpl;
import gotcha.server.Utils.Password.iPasswordManager;
import gotcha.server.Utils.Utils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class UserController implements IUserController {
    private Map<String, User> allUsers;

    private Map<String, String> usersEmailByRaspberryPi;
    private iPasswordManager passwordManager;

    private IQuestionController questionController;

    private static class SingletonHolder {
        private static UserController instance = new UserController();
    }

    public static UserController get_instance() {
        return UserController.SingletonHolder.instance;
    }

    public UserController() {
        this.allUsers = new ConcurrentHashMap<>();
        this.passwordManager = new PasswordManagerImpl();
        this.questionController = new QuestionController();
        this.usersEmailByRaspberryPi = new ConcurrentHashMap<>();
    }

    public void add_first_admin(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String firstName, String lastName) throws Exception {
        verify_user_information(userEmail, password, phoneNumber, birthDay, gender,firstName, lastName);
        var passwordToken = passwordManager.hash(password);
        var admin = new Admin(userEmail, passwordToken, phoneNumber, birthDay, gender,firstName, lastName, null );
    }

    public void load() {

    }

    /**
     * Returns the user with the give {userEmail}, throws exception if not found
     * @param userEmail
     * @return
     * @throws UserNotFoundException
     */
    public User get_user_by_id(String userEmail) throws UserNotFoundException {
        User toReturn = allUsers.get(userEmail);
        if (toReturn == null) {
            throw new UserNotFoundException("User with user name of: " + userEmail + " is not found");
        }
        return toReturn;
    }

    /**
     * Returns a list of all users
     * @return
     */
    public List<User> get_all_users() {
        return new ArrayList<User>(this.allUsers.values());
    }

    /**
     * Registers a new user, throws exception if user already exists or one of his credentials is invalid
     * @param userEmail
     * @param password
     * @param phoneNumber
     * @param birthDay
     * @param gender
     * @param scooterType
     * @param licenceIssueDate
     * @return
     * @throws UserException
     */
    public Boolean register(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber, String firstName, String lastName) throws Exception {
        if (allUsers.containsKey(userEmail))
        {
            throw new UserAlreadyExistsException("User with email :"+ userEmail + " alerady exists");
        }

        verify_user_information(userEmail, password, phoneNumber, birthDay, gender, firstName, lastName, scooterType, licenceIssueDate);

        String passwordToken = passwordManager.hash(password);
        User newUser = new Rider(userEmail, passwordToken, phoneNumber, birthDay, gender, scooterType, licenceIssueDate,firstName, lastName, raspberryPiSerialNumber);
        allUsers.put(userEmail, newUser);
        usersEmailByRaspberryPi.put(raspberryPiSerialNumber, userEmail);
        return true;
    }

    /**
     * Login user with email {userEmail}, throws exception if user not found or invalid password
     * @param userEmail
     * @param password
     * @throws Exception
     */
    public void login(String userEmail, String password) throws Exception {
        if (!allUsers.containsKey(userEmail))
        {
            throw new UserNotFoundException("invalid login: user with email"+ userEmail + " not found");
        }
        User user = allUsers.get(userEmail);
        if (!passwordManager.authenticate(password, user.get_password_token()))
        {
            throw new Exception("password is incorrect");
        }
        user.login();
    }

    public void change_password(String userEmail, String oldPassword, String newPassword) throws Exception {
        if (!allUsers.containsKey(userEmail))
            throw new UserNotFoundException("user email: " + userEmail + " not found");

        var user = allUsers.get(userEmail);
        if (passwordManager.authenticate(oldPassword, user.get_password_token()));
        {
            Utils.passwordValidCheck(oldPassword);
            String passwordToken = passwordManager.hash(newPassword);
            user.change_password_token(passwordToken);

        }
        throw new Exception("Invalid password");
    }

    /**
     * Logout user, throws exception if user not found
     * @param userEmail
     * @throws UserNotFoundException
     */
    public void logout(String userEmail) throws UserNotFoundException {
        if (!allUsers.containsKey(userEmail))
        {
            throw new UserNotFoundException("invalid login: user with email"+ userEmail + " not found");
        }
        allUsers.get(userEmail).logout();
    }

    /**
     * Appoints a new admin, throws exception if user eamil already exists or the appointing admin is not admin
     * new admin needs to have an email that is not already registered in the system
     * @param newAdminEmail
     * @param appointingAdminEmail
     * @throws Exception
     */
    public void appoint_new_admin(String newAdminEmail, String password, String phoneNumber, LocalDate birthDay, String gender,String firstName, String lastName, String appointingAdminEmail) throws Exception {
        if (allUsers.containsKey(newAdminEmail)) {
            throw new UserNotFoundException("appointed admin email: " + appointingAdminEmail + " already exists");
        }

        verify_user_information(newAdminEmail, password, phoneNumber, birthDay, gender, firstName, lastName);
        verify_user_is_admin(appointingAdminEmail);
        var appointingAdmin = allUsers.get(appointingAdminEmail);

        var passwordToken = passwordManager.hash(password);
        var newAdmin = new Admin(newAdminEmail, passwordToken, phoneNumber, birthDay, gender, firstName, lastName,(Admin)appointingAdmin);
        allUsers.put(newAdminEmail, newAdmin);
    }


    /**
     * Verifies the user information is valid
     * @param userEmail
     * @param password
     * @param phoneNumber
     * @param birthDay
     * @param gender
     * @param extraParams
     * @return
     */
    private void verify_user_information(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender,String firstName, String lastName, Object...extraParams) throws Exception {
        Utils.emailValidCheck(userEmail);
        Utils.validate_phone_number(phoneNumber);
        Utils.passwordValidCheck(password);
        Utils.validate_birth_date(birthDay);
        Utils.validate_gender(gender);
        Utils.validate_name(firstName);
        Utils.validate_name(lastName);
        if (extraParams[0] != null)
            Utils.validate_scooter_type((String)extraParams[0]);
        if (extraParams[1] != null)
            Utils.validate_license_issue_date((LocalDate)extraParams[1]);
    }

    /**
     * adds an answer to the question and notifies the user about the reply
     * @param adminEmail
     * @param reply
     * @param question_id
     * @throws Exception
     */
    public void reply_to_user_question(String adminEmail, String reply, int question_id) throws Exception {
        if (!allUsers.containsKey(adminEmail))
            throw new UserNotFoundException("Invalid admin email :" + adminEmail);

        verify_user_is_admin(adminEmail);

        var sendingUserEmail = questionController.answer_user_question(question_id, reply, adminEmail);
        var sendingUser = allUsers.get(sendingUserEmail);
        sendingUser.notify_user(new Notification(adminEmail, question_id));
    }

    private void notify_all(String senderEmail, int questionId, boolean notifyAdmins){
        var newNotification = new Notification(senderEmail, questionId);
        for (var user : allUsers.values()) {
            if (notifyAdmins) {
                if (user.is_admin()) {
                    user.notify_user(newNotification);
                }
            }
            else {
                if (!user.is_admin())
                {
                    user.notify_user(newNotification);
                }
            }
        }
    }

    /**
     * this method will send to all admins a question for the user
     * @param userEmail
     * @param message
     * @throws Exception
     */
    public void send_question_to_admin(String userEmail, String message) throws Exception {
        if (!allUsers.containsKey(userEmail))
            throw new UserNotFoundException("Invalid user email :" + userEmail);

        var sender = allUsers.get(userEmail);
        var questionId = questionController.add_user_question(message, userEmail);
        notify_all(userEmail, questionId, true);
    }

    @Override
    public List<String> view_admins(String requestingAdminEmail) throws Exception {
        verify_user_is_admin(requestingAdminEmail);

        var adminsList = new ArrayList<String>();
        for (var user : allUsers.values()) {
            if (user.is_admin()) {
                adminsList.add(user.get_email());
            }
        }
        return adminsList;
    }

    @Override
    public void remove_admin_appointment(String user_email, String admin_email) throws Exception {
        var user = allUsers.get(user_email);
        if (user == null || !user.is_admin()) {
            throw new Exception("user email: " + user_email + " not found or is not admin");
        }

        // only master admin can remove an admin appointment
        var admin = allUsers.get(admin_email);
        if (((Admin)admin).get_appointed_by() != null) {
            throw new Exception("only master admin can remove appointment");
        }

        allUsers.remove(user_email);
    }

    @Override
    public void delete_user(String userEmail, String adminEmail) throws Exception {
        if (!allUsers.containsKey(userEmail))
            throw new Exception("user with email:" + userEmail + " not found");

        verify_user_is_admin(adminEmail);
        allUsers.remove(userEmail);
    }

    @Override
    public void update_user_rate(String user_id, Ride ride, int number_of_rides) throws Exception {
        var user = allUsers.get(user_id);
        if (user == null || !user.is_admin())
            throw new Exception("user not found or is admin");

        ((Rider)user).update_rating(ride, number_of_rides);
    }

    @Override
    public void send_message_to_all_users(String message, String adminEmail) throws Exception {
        verify_user_is_admin(adminEmail);
        questionController.add_announcement(message, adminEmail);
        notify_all(adminEmail, -1, false);
    }

    private void verify_user_is_admin(String email) throws Exception {
        var user = allUsers.get(email);
        if (user == null || !user.is_admin()) {
            throw new Exception("admin not found or not an admin");
        }
    }
}
