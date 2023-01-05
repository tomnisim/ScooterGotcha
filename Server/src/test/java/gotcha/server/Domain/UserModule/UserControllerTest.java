package gotcha.server.Domain.UserModule;

import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private IUserController userController = new UserController();
    private final String adminEmail = "admin@gmail.com";
    private final String userEmail = "user@gmail.com";
    private final String password = "1233456";
    private final String phoneNumber = "0502298166";
    private final String gender = "male";
    private final String scooterType = "testScooter";
    private final LocalDate birthDay = LocalDate.ofYearDay(1996, 28);
    private final LocalDate licenseDate = LocalDate.ofYearDay(2018, 28);

    @Test
    void TestGetUserById_ValidUserId_UserReturned() {
        TestRegister_ValidUserCredentials_SuccessfullyRegistered();
        try {
            var user = userController.get_user_by_id(userEmail);
            if (user.is_admin())
            {
                fail("user should be a rider");
            }
            assertTrue(verifyUser((Rider)user));
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private boolean verifyUser(Rider user) {
        boolean res = true;
        res &= user.get_email().equals(userEmail);
        res &= user.get_gender().equals(gender) ;
        res &= user.get_birth_day().isEqual(birthDay);
        res &= user.get_phone_number().equals(phoneNumber);
        return res;
    }

    @Test
    void TestRegister_ValidUserCredentials_SuccessfullyRegistered() {
        try {
            userController.register(userEmail, password, phoneNumber, birthDay, gender, scooterType, licenseDate);
            assertTrue(userController.get_all_users().size()  == 1);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void TestRegister_InvalidUserEmail_FailedRegister() {
        try {
            userController.register(userEmail.replace('@', '+'), password, phoneNumber, birthDay, gender, scooterType, licenseDate);
            assertTrue(userController.get_all_users().size()  == 1);
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }


    @Test
    void TestLogin_ValidUserCredentials_SuccessfullyLoggedIn() {
        TestRegister_ValidUserCredentials_SuccessfullyRegistered();
        try {
            userController.login(userEmail, password);
            assertTrue(userController.get_all_users().get(0).is_logged_in());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void TestLogin_InvalidUserCredentials_SuccessfullyLoggedIn() {
        TestRegister_ValidUserCredentials_SuccessfullyRegistered();
        try {
            userController.login(userEmail, password);
            fail();

        }
        catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void TestLogout_ValidUserCredentials_SuccessfullyLoggedOut() {
        TestLogin_ValidUserCredentials_SuccessfullyLoggedIn();
        try {
            userController.logout(userEmail);
            assertFalse(userController.get_all_users().get(0).is_logged_in());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @Test
    void TestLogout_InvalidUserCredentials_SuccessfullyLoggedOut() {
        TestLogin_ValidUserCredentials_SuccessfullyLoggedIn();
        try {
            userController.logout(userEmail.replace('@','+'));
            fail();


        } catch (Exception e) {
            assertTrue(userController.get_all_users().get(0).is_logged_in());
        }
    }

    @Test
    void TestAppointNewAdmin_ValidNewAdminCredentials_SuccessfullyAppointed() {
        // TODO: add after pushing changes from user controller branch
    }

    @Test
    void reply_to_user_question() {
    }

    @Test
    void send_question_to_admin() {
    }

    @Test
    void view_admins() {
    }

    @Test
    void remove_admin_appointment() {
    }

    @Test
    void delete_user() {

    }

    @Test
    void TestChangePassword_ValidUserCredentials_SuccessfullyChanged() {
        final String NewPassword = "4125263463";
        TestRegister_ValidUserCredentials_SuccessfullyRegistered();
        TestLogin_ValidUserCredentials_SuccessfullyLoggedIn();
        try {
            var user = userController.get_user_by_id(userEmail);
            userController.change_password(user, password, NewPassword);
            TestLogout_ValidUserCredentials_SuccessfullyLoggedOut();
            userController.login(userEmail, NewPassword);
            assertTrue(user.is_logged_in());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void TestChangePassword_InvalidUserCredentials_SuccessfullyChanged() {
        final String NewPassword = "4125263463";
        TestRegister_ValidUserCredentials_SuccessfullyRegistered();
        TestLogin_ValidUserCredentials_SuccessfullyLoggedIn();
        try {
            var user = userController.get_user_by_id(userEmail);
            userController.change_password(user, NewPassword, NewPassword);
            fail("shouldn't have successfully changed the password");
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void update_user_rate() {
    }
}