package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.QuestionsModule.IQuestionController;
import gotcha.server.Domain.RatingModule.UserRateCalculator;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Exceptions.UserExceptions.UnavailableRPserialException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserAlreadyExistsException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Password.iPasswordManager;
import gotcha.server.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class UserController implements IUserController {
    private final Utils utils;
    private final iPasswordManager passwordManager;
    private final UserRepository userRepository;
    private final IQuestionController questionController;
    private final AvailableRaspberryPiSerialsRepository serialsRepository;
    private final UserRateCalculator userRateCalculator;

    @Autowired
    public UserController(Utils utils, iPasswordManager passwordManager, IQuestionController questionController, UserRepository userRepository, UserRateCalculator userRateCalculator, AvailableRaspberryPiSerialsRepository serialsRepository) {
        this.utils = utils;
        this.passwordManager = passwordManager;
        this.questionController = questionController;
        this.userRepository = userRepository;
        this.userRateCalculator = userRateCalculator;
        this.serialsRepository = serialsRepository;
    }


    public void add_first_admin(String userEmail, String name, String lastName, String password, String phoneNumber, LocalDate birthDay, String gender) throws Exception {
        var passwordToken = passwordManager.hash(password);
        var admin = new Admin(userEmail, name, lastName, passwordToken, phoneNumber, birthDay, gender, null);
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
        var user = userRepository.getUserByEmail(userEmail);
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
     * @param name
     * @param lastName
     * @param phoneNumber
     * @param birthDay
     * @param gender
     * @param scooterType
     * @param licenceIssueDate
     * @return
     * @throws Exception
     */
    public Boolean register(String userEmail, String password, String name, String lastName, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber) throws Exception {
        verify_user_information(userEmail, password, phoneNumber, birthDay, gender, scooterType, licenceIssueDate);
        if (!serialsRepository.isExists(raspberryPiSerialNumber)){
            throw new UnavailableRPserialException(String.format("Raspberry Pi: %s is unavailable", raspberryPiSerialNumber));
        }
        String passwordToken = passwordManager.hash(password);
        var user = new Rider(userEmail,passwordToken, name, lastName, phoneNumber, birthDay, gender, scooterType, licenceIssueDate, raspberryPiSerialNumber);
        var addResult = userRepository.addUser(user);
        if (addResult != null)
            throw new UserAlreadyExistsException(String.format("user with email: %s is already registered in the system", userEmail));
        var rpAddResult = userRepository.assignRpToUser(raspberryPiSerialNumber, userEmail);
        if (rpAddResult != null) {
            throw new Exception(String.format("rp with serial number: %s is already associated to a user", raspberryPiSerialNumber));
        }
        serialsRepository.removeSerial(raspberryPiSerialNumber);
        return true;
    }



    /**
     * Update information to logged user, throws exception if one of user credentials is invalid
     *
     * @param userEmail
     * @param name
     * @param lastName
     * @param phone
     * @param birthDate
     * @param gender
     * @param scooterType
     * @return
     */
    public Boolean update_information(String userEmail, String name, String lastName, String phone, LocalDate birthDate, String gender, String scooterType) throws Exception {
        verify_user_information_for_update(phone, birthDate, gender, scooterType);
        userRepository.updateUser(userEmail, name, lastName, phone, birthDate, gender, scooterType);
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
        var user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("invalid login: user with email" + userEmail + " not found");
        }
        if (!passwordManager.authenticate(password, user.get_password_token())) {
            throw new Exception("password is incorrect for user with email " + userEmail);
        }
        user.login();
        userRepository.riderLogin(userEmail);
        return user;
    }

    public String resetPassword(String userEmail) throws UserNotFoundException {
        var newPassword = utils.generateRandomPassword();
        var newPasswordToken = passwordManager.hash(newPassword);
        userRepository.changeUserPassword(userEmail, newPasswordToken);
        return newPassword;
    }

    public void change_password(String userEmail, String oldPassword, String newPassword) throws Exception {

        var user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("user email: " + userEmail + " not found");
        }
        if (!passwordManager.authenticate(oldPassword, user.get_password_token())) {
            throw new Exception("Invalid old password");
        }
        utils.passwordValidCheck(newPassword);
        String passwordToken = passwordManager.hash(newPassword);
        userRepository.changeUserPassword(userEmail, passwordToken);
    }

    /**
     * Logout user, throws exception if user not found
     *
     * @param userEmail
     * @throws UserNotFoundException
     */
    public void logout(String userEmail) throws Exception {
        var user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("invalid logout: user with email" + userEmail + " not found");
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
    public void appoint_new_admin(String newAdminEmail, String name, String lastName, String password, String phoneNumber, LocalDate birthDay, String gender, String appointingAdminEmail) throws Exception {
        var newAdmin = userRepository.getUserByEmail(newAdminEmail);
        if (newAdmin != null) {
            throw new Exception("appointed admin email: " + appointingAdminEmail + " already exists");
        }
        verify_user_information(newAdminEmail, password, phoneNumber, birthDay, gender);
        var appointingAdmin = userRepository.getUserByEmail(appointingAdminEmail);
        if (appointingAdmin == null) {
            throw new UserNotFoundException("appointing admin email: " + appointingAdminEmail + " does NOT exists");
        }
        if (!appointingAdmin.is_admin()) {
            throw new Exception("appointing admin email is not of an admin");
        }

        var passwordToken = passwordManager.hash(password);
        newAdmin = new Admin(newAdminEmail, name, lastName, passwordToken, phoneNumber, birthDay, gender, (Admin) appointingAdmin);
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
     * Verifies the user information is valid
     *
     * @param phoneNumber
     * @param birthDay
     * @param gender
     * @param extraParams
     * @return
     */
    private void verify_user_information_for_update(String phoneNumber, LocalDate birthDay, String gender, String scooterType) throws Exception {
        utils.validate_phone_number(phoneNumber);
        utils.validate_birth_date(birthDay);
        utils.validate_gender(gender);
        utils.validate_scooter_type(scooterType);

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
        var admin = userRepository.getUserByEmail(adminEmail);
        if (admin == null)
            throw new UserNotFoundException("Invalid admin email :" + adminEmail);

        if (!admin.is_admin())
            throw new Exception("Email : " + adminEmail + " is Not related to an admin account");

        var sendingUserEmail = questionController.answer_user_question(question_id, reply, adminEmail);
        var sendingUser = userRepository.getUserByEmail(sendingUserEmail);
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
                    this.userRepository.notifyUser(user, newNotification);
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
        var sender = userRepository.getUserByEmail(userEmail);
        if (sender == null) {
            throw new UserNotFoundException("Invalid user email :" + userEmail);
        }
        questionController.add_user_question(message, sender.get_email(), create_notify_all_admins_callback());
        this.notify_admins(userEmail, message);

    }

    @Override
    public List<AdminDTO> view_admins() {

        var adminsList = new ArrayList<AdminDTO>();
        var allUsers = userRepository.getAllUsers();
        for (var user : allUsers) {
            if (user.is_admin()) {
                AdminDTO to_add = new AdminDTO((Admin) user);
                adminsList.add(to_add);
            }
        }
        return adminsList;
    }

    @Override
    public List<RiderDTO> get_all_riders() {
        var ridersList = new ArrayList<RiderDTO>();
        for (var user : userRepository.getAllUsers()){
            if (!user.is_admin()){
                RiderDTO to_add = new RiderDTO(((Rider) user));
                ridersList.add(to_add);
            }
        }
        return ridersList;
    }


    @Override
    public List<WaitingRaspberryPiDTO> get_waiting_rp() {
        List<WaitingRaspberryPiDTO> to_return = new ArrayList<>();
        int i = 0;
        for(var serial : serialsRepository.getAllSerials()) {
            WaitingRaspberryPiDTO to_add = new WaitingRaspberryPiDTO(i, serial);
            to_return.add(to_add);
            i = i+1;
        }
        return to_return;
    }

    @Override
    public void add_rp_serial_number(String rpSerial) throws Exception {
        if (serialsRepository.isExists(rpSerial) || (userRepository.getUserByRpSerialNumber(rpSerial) != null)){
            throw new Exception(String.format("Raspberry Pi Serial Number: %s is already exists in the system!", rpSerial));
        }
        serialsRepository.addSerial(rpSerial);
    }

    @Override
    public void remove_admin_appointment(String user_email, String admin_email) throws Exception {
        var user = userRepository.getUserByEmail(user_email);
        if (user == null || !user.is_admin()) {
            throw new Exception("user email: " + user_email + " not found or is not admin");
        }
        // only master admin can remove an admin appointment
        var admin = userRepository.getUserByEmail(admin_email);
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
        this.userRepository.updateUserRating(userEmail, ride, number_of_rides, userRateCalculator);

    }

    @Override
    public String get_user_email_by_rp_serial(String rpSerialNumber) throws Exception {
        var user = userRepository.getUserByRpSerialNumber(rpSerialNumber);
        if (user == null) {
            throw new UserNotFoundException(String.format("User not found while fetching with rp serial number: %s", rpSerialNumber));
        }
        return user.get_email();
    }

    @Override
    // change all users
    public void notify_all_users(String senderEmail, String message) throws Exception {
        Notification notification = new Notification(senderEmail, message);
        for (User user : userRepository.getAllUsers()) {
            this.userRepository.notifyUser(user, notification);
        }
    }
    // change all users

    public void notify_users(String senderEmail, List<String> emails, String message) throws UserNotFoundException {
        Notification notification = new Notification(senderEmail, message);
        for (String email : emails) {
            User user = userRepository.getUserByEmail(email);
            if (user != null) {
                this.userRepository.notifyUser(user, notification);

            }
        }
    }

    public void notify_admins(String senderEmail , String message) {
        Notification notification = new Notification(senderEmail, "New User Question");
        for (User user : this.userRepository.getAllUsers()){
            if (user.is_admin()){
                this.userRepository.notifyUser(user, notification);

            }
        }
    }

    public boolean isSerialsTableEmpty(){
        return serialsRepository.isDbEmpty();
    }

    public boolean isUsersTableEmpty() {
        return userRepository.isDbEmpty();
    }

}
