package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.QuestionsModule.IQuestionController;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Exceptions.UserExceptions.UserAlreadyExistsException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import gotcha.server.Utils.Password.iPasswordManager;
import gotcha.server.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@Component
public class UserController implements IUserController {
    private final Utils utils;
    private Map<String, String> usersEmailByRaspberryPi;
    private final iPasswordManager passwordManager;
    private final UserRepository userRepository;
    private final ServerLogger serverLogger;
    private final ErrorLogger errorLogger;

    private final IQuestionController questionController;

    @Autowired
    public UserController(Utils utils, iPasswordManager passwordManager, IQuestionController questionController, UserRepository userRepository, ServerLogger serverLogger, ErrorLogger errorLogger) {
        this.utils = utils;
        this.passwordManager = passwordManager;
        this.questionController = questionController;
        this.usersEmailByRaspberryPi = new ConcurrentHashMap<>();
        this.userRepository = userRepository;
        this.serverLogger = serverLogger;
        this.errorLogger = errorLogger;
    }


    public void load() {

    }
    public void add_first_admin(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender) throws Exception {
        verify_user_information(userEmail, password, phoneNumber, birthDay, gender);
        var passwordToken = passwordManager.hash(password);
        var admin = new Admin(userEmail, passwordToken, phoneNumber, birthDay, gender, null);
        userRepository.addUser(admin);
    }

    /**
     * Returns the user with the give {userEmail}, throws exception if not found
     *
     * @param userEmail
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public User get_user_by_email(String userEmail) throws UserNotFoundException {
        var user = userRepository.getUser(userEmail);
        if (user == null) {
            throw new UserNotFoundException("user with email" + userEmail + " not found");
        }
        return user;
    }

    /**
     * Returns a list of all users
     *
     * @return
     */
    public List<User> get_all_users() {
        return userRepository.getAllUsers();
    }

    /**
     * Registers a new user, throws exception if user already exists or one of his credentials is invalid
     *
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
        var user = userRepository.getUser(userEmail);
        if (user != null) {
            throw new UserAlreadyExistsException("User with email :" + userEmail + " alerady exists");
        }

        verify_user_information(userEmail, password, phoneNumber, birthDay, gender, scooterType, licenceIssueDate);

        String passwordToken = passwordManager.hash(password);
        User newUser = new Rider(userEmail, passwordToken, phoneNumber, birthDay, gender, scooterType, licenceIssueDate, raspberryPiSerialNumber);
        userRepository.addUser(newUser);
        usersEmailByRaspberryPi.put(raspberryPiSerialNumber, userEmail);
        return true;
    }

    /**
     * Login user with email {userEmail}, throws exception if user not found or invalid password
     *
     * @param userEmail
     * @param password
     * @throws Exception
     */
    public User login(String userEmail, String password) throws Exception {
        var user = userRepository.getUser(userEmail);
        if (user == null) {
            var message = "invalid login: user with email" + userEmail + " not found";
            errorLogger.add_log(message);
            throw new UserNotFoundException(message);
        }
        if (!passwordManager.authenticate(password, user.get_password_token())) {
            var message = "password is incorrect for user with email " + userEmail;
            errorLogger.add_log(message);
            throw new Exception(message);
        }
        try {
            user.login();
            serverLogger.add_log("Successfully logged in user with email" + userEmail);
        } catch (Exception e) {
            errorLogger.add_log(e.getMessage());
            throw e;
        }
        return user;
    }

    public void change_password(String userEmail, String oldPassword, String newPassword) throws Exception {

        var user = userRepository.getUser(userEmail);
        if (user == null) {
            throw new UserNotFoundException("user email: " + userEmail + " not found");
        }
        if (!passwordManager.authenticate(oldPassword, user.get_password_token())) {
            throw new Exception("Invalid password");
        }
        utils.passwordValidCheck(oldPassword);
        String passwordToken = passwordManager.hash(newPassword);
        user.change_password_token(passwordToken);
    }

    /**
     * Logout user, throws exception if user not found
     *
     * @param userEmail
     * @throws UserNotFoundException
     */
    public void logout(String userEmail) throws Exception {
        var user = userRepository.getUser(userEmail);
        if (user == null) {
            throw new UserNotFoundException("invalid login: user with email" + userEmail + " not found");
        }
        user.logout();
    }

    /**
     * Appoints a new admin, throws exception if user eamil already exists or the appointing admin is not admin
     * new admin needs to have an email that is not already registered in the system
     *
     * @param newAdminEmail
     * @param appointingAdminEmail
     * @throws Exception
     */
    public void appoint_new_admin(String newAdminEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String appointingAdminEmail) throws Exception {
        var newAdmin = userRepository.getUser(newAdminEmail);
        if (newAdmin != null) {
            throw new Exception("appointed admin email: " + appointingAdminEmail + " already exists");
        }

        verify_user_information(newAdminEmail, password, phoneNumber, birthDay, gender);
        var appointingAdmin = userRepository.getUser(appointingAdminEmail);
        if (appointingAdmin == null) {
            throw new UserNotFoundException("appointing admin email: " + appointingAdminEmail + " does NOT exists");
        }
        if (!appointingAdmin.is_admin()) {
            throw new Exception("appointing admin email is not of an admin");
        }

        var passwordToken = passwordManager.hash(password);
        newAdmin = new Admin(newAdminEmail, passwordToken, phoneNumber, birthDay, gender, (Admin) appointingAdmin);
        userRepository.addUser(newAdmin);
    }


    /**
     * Verifies the user information is valid
     *
     * @param userEmail
     * @param password
     * @param phoneNumber
     * @param birthDay
     * @param gender
     * @param extraParams
     * @return
     */
    private void verify_user_information(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, Object... extraParams) throws Exception {
        utils.emailValidCheck(userEmail);
        utils.validate_phone_number(phoneNumber);
        utils.passwordValidCheck(password);
        utils.validate_birth_date(birthDay);
        utils.validate_gender(gender);
        if (extraParams[0] != null)
            utils.validate_scooter_type((String) extraParams[0]);
        if (extraParams[1] != null)
            utils.validate_license_issue_date((LocalDate) extraParams[1]);
    }


    /**
     * adds an answer to the question and notifies the user about the reply
     *
     * @param adminEmail
     * @param reply
     * @param question_id
     * @throws Exception
     */
    public void reply_to_user_question(String adminEmail, String reply, int question_id) throws Exception {
        var admin = userRepository.getUser(adminEmail);
        if (admin == null)
            throw new UserNotFoundException("Invalid admin email :" + adminEmail);

        if (!admin.is_admin())
            throw new Exception("Email : " + adminEmail + " is Not related to an admin account");

        var sendingUserEmail = questionController.answer_user_question(question_id, reply, adminEmail);
        var sendingUser = userRepository.getUser(sendingUserEmail);
        if (sendingUser == null) {
            throw new UserNotFoundException("Invalid sending user email :" + sendingUserEmail);
        }
        sendingUser.notify_user(new Notification(admin.get_email(), "you got an answer for your question:" + question_id));
    }

    private BiConsumer<String, Integer> create_notify_all_admins_callback() {
        BiConsumer<String, Integer> callback = (senderEmail, questionId) -> {
            var allUsers = userRepository.getAllUsers();
            for (var user : allUsers) {
                if (user.is_admin()) {
                    var newNotification = new Notification(senderEmail, "you got a new question:" + questionId);
                    user.notify_user(newNotification);
                }
            }
        };
        return callback;
    }

    /**
     * this method will send to all admins a question for the user
     *
     * @param userEmail
     * @param message
     * @throws Exception
     */
    public void send_question_to_admin(String userEmail, String message) throws Exception {
        var sender = userRepository.getUser(userEmail);
        if (sender == null) {
            throw new UserNotFoundException("Invalid user email :" + userEmail);
        }
        questionController.add_user_question(message, sender.get_email(), create_notify_all_admins_callback());
    }

    @Override
    public List<Admin> view_admins() {

        var adminsList = new ArrayList<Admin>();
        var allUsers = userRepository.getAllUsers();
        for (var user : allUsers) {
            if (user.is_admin()) {
                adminsList.add((Admin) user);
            }
        }
        return adminsList;
    }

    @Override
    public void remove_admin_appointment(String user_email, String admin_email) throws Exception {
        var user = userRepository.getUser(user_email);
        if (user == null || !user.is_admin()) {
            throw new Exception("user email: " + user_email + " not found or is not admin");
        }

        // only master admin can remove an admin appointment
        var admin = userRepository.getUser(admin_email);
        if (admin == null) {
            throw new Exception("admin email: " + admin_email);
        }
        if (((Admin) admin).get_appointed_by() != null) {
            throw new Exception("only master admin can remove appointment");
        }

        userRepository.removeUser(user_email);
    }

    @Override
    public void delete_user(String user_email) throws Exception {
        userRepository.removeUser(user_email);
    }

    @Override
    public void update_user_rate(String userEmail, Ride ride, int number_of_rides) throws Exception {
        var user = userRepository.getUser(userEmail);
        if (user == null || !user.is_admin())
            throw new Exception("user not found or is admin");

        ((Rider) user).update_rating(ride, number_of_rides);
    }

    @Override
    // change all users
    public void notify_all_users(String senderEmail, String message) throws Exception {
        Notification notification = new Notification(senderEmail, message);
        for (User user : userRepository.getAllUsers()) {
            user.notify_user(notification);
        }
    }
    // change all users

    public void notify_users(String senderEmail, String[] emails, String message) {
        Notification notification = new Notification(senderEmail, message);
        List<User> allUsers = userRepository.getAllUsers();
        for (String email : emails) {
            User user = userRepository.getUser(email);
            if (user != null) {
                user.notify_user(notification);
            }
        }


    }
}
