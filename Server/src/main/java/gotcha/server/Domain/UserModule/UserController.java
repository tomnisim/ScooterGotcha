package gotcha.server.Domain.UserModule;

import gotcha.server.Utils.Exceptions.UserExceptions.InvalidUserInformationException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserAlreadyExistsException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Observable;
import gotcha.server.Utils.Observer;
import gotcha.server.Utils.Password.PasswordManagerImpl;
import gotcha.server.Utils.Password.iPasswordManager;
import gotcha.server.Utils.Utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController implements IUserController, Observable {
    private AtomicInteger id_counter;
    private ErrorLogger logger;
    private Map<String, User> allUsers;
    private iPasswordManager passwordManager;


    private static class SingletonHolder {
        private static UserController instance = new UserController();
    }

    public static UserController get_instance() {
        return UserController.SingletonHolder.instance;
    }

    public UserController() {
        this.id_counter = new AtomicInteger(1);
        this.logger = ErrorLogger.getInstance();
        this.allUsers = new HashMap<>();
        this.passwordManager = new PasswordManagerImpl();
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
            logger.add_log("User with user name of: " + userEmail + " is not found");
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
    public Boolean register(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate) throws Exception {
        if (allUsers.containsKey(userEmail))
        {
            logger.add_log("user with email: "+ userEmail + " already exists");
            throw new UserAlreadyExistsException();
        }

        verify_user_information(userEmail, password, phoneNumber, birthDay, gender, scooterType, licenceIssueDate);

        String passwordToken = passwordManager.hash(password);
        User newUser = new Rider(userEmail, passwordToken, phoneNumber, birthDay, gender, scooterType, licenceIssueDate);
        allUsers.put(userEmail, newUser);
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
            logger.add_log("invalid login: user with email"+ userEmail + " not found");
            throw new UserNotFoundException("invalid login: user with email"+ userEmail + " not found");
        }
        User user = allUsers.get(userEmail);
        if (!passwordManager.authenticate(password, user.get_password_token()))
        {
            logger.add_log("user with email: "+ userEmail+ " tried to login with incorrect password");
            throw new Exception("password is incorrect");
        }
        user.login();
    }

    /**
     * Logout user, throws exception if user not found
     * @param userEmail
     * @throws UserNotFoundException
     */
    public void logout(String userEmail) throws UserNotFoundException {
        if (!allUsers.containsKey(userEmail))
        {
            logger.add_log("invalid login: user with email"+ userEmail + " not found");
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
            logger.add_log("invalid appointing/appointed user email");
            throw new UserNotFoundException();
        }
        User appointingAdmin = allUsers.get(appointingAdminEmail);
        if (!appointingAdmin.is_admin()) {
            logger.add_log("appointing user is not an admin");
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
}
