package gotcha.server.Domain.UserModule;

import gotcha.server.Utils.Exceptions.UserExceptions.InvalidUserInformationException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserAlreadyExistsException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Logger.ErrorLogger;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController {
    private AtomicInteger id_counter;
    private ErrorLogger logger;
    private Map<String, User> allUsers;

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
    public Boolean register(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate) throws UserException {
        if (allUsers.containsKey(userEmail))
        {
            logger.add_log("user with email: "+ userEmail + " already exists");
            throw new UserAlreadyExistsException();
        }

        String informationErrors = verify_user_inforamtion(userEmail, password, phoneNumber, birthDay, gender, scooterType, licenceIssueDate);
        if (informationErrors.length() > 0) {
             logger.add_log("invalid user information");
            throw new InvalidUserInformationException(informationErrors);
        }

        User newUser = new Rider(userEmail, password, phoneNumber, birthDay, gender, scooterType, licenceIssueDate);
        allUsers.put(userEmail, newUser);
        return true;
    }

    /**
     * Login user with email {userEmail}, throws exception if user not found or invalid password
     * @param userEmail
     * @param password
     * @throws Exception
     */

    private void login(String userEmail, String password) throws Exception {
        if (!allUsers.containsKey(userEmail))
        {
            logger.add_log("invalid login: user with email"+ userEmail + " not found");
            throw new UserNotFoundException("invalid login: user with email"+ userEmail + " not found");
        }
        User user = allUsers.get(userEmail);
        if (!password.equals(user.get_password()))
        {
            logger.add_log("user with email: "+ userEmail+ " tried to login with incorrect password");
            throw new Exception("password is incorrect");
        }
        user.login();
    }

    private void logout(String userEmail) throws UserNotFoundException {
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

    private void appoint_new_admin(String newAdminEmail, String appointingAdminEmail) throws Exception {
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
    private String verify_user_inforamtion(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate) {
        StringBuilder errorsInInfo = new StringBuilder();
        errorsInInfo.append(validate_user_email(userEmail));
        errorsInInfo.append(validate_phone_number(phoneNumber));
        errorsInInfo.append(validate_password(password));
        errorsInInfo.append(validate_birth_date(birthDay));
        errorsInInfo.append(validate_gender(gender));
        errorsInInfo.append(validate_scooter_type(scooterType));
        errorsInInfo.append(validate_license_issue_date(licenceIssueDate));
        return errorsInInfo.toString();
    }

    private String validate_user_email(String userEmail) {
        StringBuilder errorsInInfo = new StringBuilder();
        if (userEmail == null) {
            return "user email can't be null\n";
        }
        if (userEmail.isBlank()) {
            errorsInInfo.append("user email can't be empty\n");
        }
        if (!userEmail.contains("@")) {
            errorsInInfo.append("user email must contain @\n");
        }
        return errorsInInfo.toString();
    }

    private String validate_license_issue_date(LocalDate licenceIssueDate) {
        StringBuilder errorsInInfo = new StringBuilder();

        if (licenceIssueDate.isAfter(LocalDate.now())) {
            errorsInInfo.append("license issue date must be before current date\n");
        }

        return errorsInInfo.toString();
    }

    private String validate_scooter_type(String scooterType) {
        StringBuilder errorsInInfo = new StringBuilder();
        if (scooterType == null) {
            return "scooter type can't be null\n";
        }
        if (scooterType.isBlank()) {
            errorsInInfo.append("scooter type can't be empty\n");
        }
        return  errorsInInfo.toString();
    }

    private String validate_gender(String gender) {
        StringBuilder errorsInInfo = new StringBuilder();
        if (gender == null) {
            return "gender can't be null\n";
        }
        if (gender.isBlank()) {
            errorsInInfo.append("gender can't be empty\n");
        }
        return errorsInInfo.toString();
    }

    private String validate_birth_date(LocalDate birthDay) {
        StringBuilder errorsInInfo = new StringBuilder();
        Period p = Period.between(birthDay, LocalDate.now());
        if (p.getYears() < 16) {
            errorsInInfo.append("Rider must be at least 16 years old\n");
        }
        return errorsInInfo.toString();
    }


    private String validate_password(String password) {
        StringBuilder errorsInInfo = new StringBuilder();
        if (password == null) {
            return "password can't be null\n";
        }
        if (password.isBlank()) {
            errorsInInfo.append("password can't be empty\n");
        }
        if(password.length() < 6 || password.length() > 15) {
            errorsInInfo.append("password length must be between 6 and 15 charachters");
        }
        return errorsInInfo.toString();
    }

    private String validate_phone_number(String phoneNumber) {
        StringBuilder errorsInInfo = new StringBuilder();

        if (phoneNumber==null) {
            return "phone number can't be null\n";
        }
        if (phoneNumber.isBlank()) {
            errorsInInfo.append("phone number can't be blank\n");
        }
        if (phoneNumber.length() != 10) {
            errorsInInfo.append("phone number must be of length 10\n");
        }
        if (phoneNumber.matches(".*[a-zA-Z].*"))
        {
            errorsInInfo.append("phone number can't contain letters\n");
        }
        return errorsInInfo.toString();
    }

}
