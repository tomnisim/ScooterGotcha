package gotcha.server.Domain.UserModule;

import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IUserController {
    User get_user_by_id(String userEmail) throws UserNotFoundException;
    List<User> get_all_users();
    Boolean register(String userEmail, String password, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate) throws Exception;
    void login(String userEmail, String password) throws Exception;
    void logout(String userEmail) throws UserNotFoundException;
    void appoint_new_admin(String newAdminEmail, String appointingAdminEmail) throws Exception;

}
