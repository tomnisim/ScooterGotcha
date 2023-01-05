package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.QuestionsModule.IQuestionController;
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
    public Boolean register(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber) throws Exception {
        if (allUsers.containsKey(userEmail))
        {
            throw new UserAlreadyExistsException("User with email :"+ userEmail + " alerady exists");
        }

        verify_user_information(userEmail, password, phoneNumber, birthDay, gender, scooterType, licenceIssueDate);

        String passwordToken = passwordManager.hash(password);
        User newUser = new Rider(userEmail, passwordToken, phoneNumber, birthDay, gender, scooterType, licenceIssueDate, raspberryPiSerialNumber);
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
     * Appoints a new admin, throws exception if users not found or the appointing admin is not admin
     * @param newAdminEmail
     * @param appointingAdminEmail
     * @throws Exception
     */
    public void appoint_new_admin(String newAdminEmail, String appointingAdminEmail) throws Exception {
        if (!allUsers.containsKey(appointingAdminEmail) || !allUsers.containsKey(newAdminEmail)) {
            throw new UserNotFoundException();
        }
        User appointingAdmin = allUsers.get(appointingAdminEmail);
        if (!appointingAdmin.is_admin()) {
            throw new Exception();
        }
        User newAdmin = new Admin(allUsers.get(newAdminEmail), (Admin) appointingAdmin);
        allUsers.put(newAdminEmail, newAdmin);
    }

    /**
     * Verifies the user information is valid
     * @param userEmail
     * @param password
     * @param phoneNumber
     * @param birthDay
     * @param gender
     * @param scooterType
     * @param licenceIssueDate
     * @return
     */
    private void verify_user_information(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate) throws Exception {
        Utils.emailValidCheck(userEmail);
        Utils.validate_phone_number(phoneNumber);
        Utils.passwordValidCheck(password);
        Utils.validate_birth_date(birthDay);
        Utils.validate_gender(gender);
        Utils.validate_scooter_type(scooterType);
        Utils.validate_license_issue_date(licenceIssueDate);
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

        var admin = allUsers.get(adminEmail);
        if (!admin.is_admin())
            throw new Exception("Email : " + adminEmail + " is Not related to an admin account");

        var sendingUserEmail = questionController.answer_user_question(question_id, reply, adminEmail);
        var sendingUser = allUsers.get(sendingUserEmail);
        sendingUser.notify_user(new Notification(admin.get_email(), question_id));
    }

    private BiConsumer<String, Integer> create_notify_all_admins_callback(){
        BiConsumer<String, Integer> callback = (senderEmail, questionId) -> {
            for (var user : allUsers.values()) {
                var newNotification = new Notification(senderEmail, questionId);
                if (user.is_admin()) {
                    user.notify_user(newNotification);
                }
            }
        };
        return callback;
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
        questionController.add_user_question(message, sender.get_email(),create_notify_all_admins_callback());
    }

    @Override
    public List<String> view_admins() {
        return null;
    }

    @Override
    public void remove_admin_appointment(String user_email, String admin_email) {

    }

    @Override
    public void delete_user(String user_email) {

    }


}
