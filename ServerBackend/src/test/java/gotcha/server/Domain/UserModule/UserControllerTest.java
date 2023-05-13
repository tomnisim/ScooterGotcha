package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.QuestionsModule.IQuestionController;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Exceptions.UserExceptions.UserAlreadyExistsException;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import gotcha.server.Utils.Password.iPasswordManager;
import gotcha.server.Utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private Utils utils;

    @Mock
    private iPasswordManager passwordManager;

    @Mock
    private IQuestionController questionController;

    @Mock
    private AvailableRaspberryPiSerialsRepository serialsRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ErrorLogger errorLoggerMock;
    @Mock
    private ServerLogger serverLoggerMock;

    private final String email = "test@gmail.com";
    private final String  password = "testPassword";
    private final String  gender = "male";
    private final LocalDate birthDate = LocalDate.now();
    private final String  name = "testName";
    private final String  lastName = "testLastName";
    private final String  phone = "testPhone";
    private final String  rpSerialNumber = "testRp";
    private final String  scooterType = "testScooter";
    private final LocalDate licenseIssueDate = LocalDate.now();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_userExists_successfullyLoggedIn() {
        var passwordToken = "testToken";
        var user = new Rider();
        user.change_password_token(passwordToken);
        try{
            when(userRepository.getUserByEmail(email)).thenReturn(user);
        }
        catch (UserNotFoundException ex) {
            fail("shouldn't happen");
        }
        when(passwordManager.authenticate(password, passwordToken)).thenReturn(true);
        try{
            var userResult = userController.login(email,password);
            assertTrue(userResult != null);
            assertTrue(userResult.is_logged_in());
        }
        catch (Exception e) {
            fail("should succeed logging in");
        }
    }

    @Test
    void login_userDoesNotExist_failedLogin() {
        try{
            when(userRepository.getUserByEmail(email)).thenReturn(null);
        }
        catch (UserNotFoundException ex) {
            fail("shouldn't happen");
        }        //when(passwordManager.authenticate(testPassword, passwordToken)).thenReturn(true);
        assertThrows(UserNotFoundException.class, () -> {
            userController.login(email,password);
        });
    }

    @Test
    void login_passwordIsIncorrect_failedLogin() {
        var testEmail = "test@gmail.com";
        var testPassword = "testPassword";
        var passwordToken = "testToken";
        var user = new Rider();
        user.change_password_token(passwordToken);
        try{

            when(userRepository.getUserByEmail(testEmail)).thenReturn(user);
        }
        catch (UserNotFoundException ex) {
            fail("shouldn't happen");
        }
        when(passwordManager.authenticate(testPassword, passwordToken)).thenReturn(false);
        assertThrows(Exception.class, () -> {
            userController.login(testEmail,testPassword);
        });
    }

    @Test
    void register_validUserCredentials_successfullyRegistered() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));
        when(serialsRepository.isExists(rpSerialNumber)).thenReturn(true);
        assertDoesNotThrow(() -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_userAlreadyExists_failedRegisration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));
        try {
            when(serialsRepository.isExists(rpSerialNumber)).thenReturn(true);
            when(userRepository.addUser(any())).thenReturn(new Rider());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(UserAlreadyExistsException.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_invalidEmail_failedRegistration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));
        try {
            doThrow(Exception.class).when(utils).emailValidCheck(anyString());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(Exception.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_invalidPassword_failedRegistration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));
        try {
            doThrow(Exception.class).when(utils).passwordValidCheck(anyString());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(Exception.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_invalidPhoneNumber_failedRegistration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));
        try {
            doThrow(Exception.class).when(utils).validate_phone_number(anyString());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(Exception.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_invalidBirthDate_failedRegistration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));

        try {
            doThrow(Exception.class).when(utils).validate_birth_date(any());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(Exception.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_invalidGender_failedRegistration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));

        try {
            doThrow(Exception.class).when(utils).validate_gender(anyString());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(Exception.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_invalidScooterType_failedRegistration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));

        try {
            doThrow(Exception.class).when(utils).validate_scooter_type(anyString());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(Exception.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_invalidLicenseIssueDate_failedRegistration() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));

        try {
            doThrow(Exception.class).when(utils).validate_license_issue_date(any());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
        assertThrows(Exception.class, () -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void logout_userNotExisting_FailedLogout() {
        configureRegisterMockForSuccess();
        try {
            var email = "test@gmail.com";
            when(userRepository.getUserByEmail(email)).thenReturn(null);
            assertThrows(UserNotFoundException.class, () -> userController.logout(email));
        }
        catch (Exception e) {
            fail("unexpected exception");
        }
    }

    @Test
    void logout_userAlreadyLoggedOut_FailedLogout() {
        configureRegisterMockForSuccess();
        try {
            var email = "test@gmail.com";
            var rider = new Rider();
            when(userRepository.getUserByEmail(email)).thenReturn(rider);
            assertThrows(Exception.class, () -> userController.logout(email));
        }
        catch (Exception e) {
            fail("unexpected exception");
        }
    }

    @Test
    void logout_validParameters_SuccessfulLogout() {
        configureRegisterMockForSuccess();
        try {
            var rider = new Rider();
            rider.login();
            assertTrue(rider.is_logged_in());
            when(userRepository.getUserByEmail(email)).thenReturn(rider);
            userController.logout(email);
            assertFalse(rider.is_logged_in());
        }
        catch (Exception e) {
            fail("unexpected exception");
        }
    }

    @Test
    void changePassword_validNewPassword_SuccessfulChange() {
        configureRegisterMockForSuccess();
        var rider = new Rider();
        final String BeforeToken = "beforeToken";
        final String NewPassword = "newPassword";
        rider.change_password_token(BeforeToken);
        configureUserRepositoryChangePassword(rider);
        try{
            when(userRepository.getUserByEmail(email)).thenReturn(rider);
            when(passwordManager.authenticate(any(), any())).thenReturn(true);
            when(passwordManager.hash(NewPassword)).thenReturn("newHash");
            assertDoesNotThrow(() -> userController.change_password(email,"somePassword", NewPassword));
            verify(userRepository, times(1)).changeUserPassword(any(), any());
            assertTrue(rider.get_password_token() == "newHash");
        }
        catch (Exception e) {
            fail("shouldn't happen: "+ e.getMessage());
        }
    }

    @Test
    void changePassword_invalidNewPassword_FailedChange() {
        configureRegisterMockForSuccess();
        var rider = new Rider();
        rider.change_password_token("token");
        try {
            doThrow(Exception.class).when(utils).passwordValidCheck(any());
            when(passwordManager.authenticate(any(), any())).thenReturn(true);
            when(userRepository.getUserByEmail(email)).thenReturn(rider);
            assertThrows(Exception.class, () -> userController.change_password(email, "oldPassword", "newPassword"));
            verify(userRepository, times(0)).changeUserPassword(any(), any());
            verify(utils, times(1)).passwordValidCheck("newPassword");
            assertTrue(rider.get_password_token() == "token");
        }
        catch (Exception e) {
            fail("shouldn't happen");
        }
    }

    @Test
    void changePassword_invalidOldPassword_FailedChange() {
        configureRegisterMockForSuccess();
        var rider = new Rider();
        rider.change_password_token("token");
        try {
            when(passwordManager.authenticate(any(), any())).thenReturn(false);
            when(userRepository.getUserByEmail(email)).thenReturn(rider);
            assertThrows(Exception.class, () -> userController.change_password(email, "oldPassword", "newPassword"));
            verify(userRepository, times(0)).changeUserPassword(any(), any());
            verify(utils, times(0)).passwordValidCheck("newPassword");
            assertTrue(rider.get_password_token() == "token");
        }
        catch (Exception e) {
            fail("shouldn't happen");
        }
    }

    @Test
    void changePassword_invalidEmail_FailedChange() {
        configureRegisterMockForSuccess();
        try {
            when(userRepository.getUserByEmail(email)).thenReturn(null);
            assertThrows(UserNotFoundException.class, () -> userController.change_password(email, "oldPassword", "newPassword"));
            verify(userRepository, times(0)).changeUserPassword(any(), any());
            verify(utils, times(0)).passwordValidCheck("newPassword");
            verify(passwordManager, times(0)).authenticate(any(), any());
        }
        catch (Exception e) {
            fail("shouldn't happen");
        }
    }

    @Test
    void resetPassword_validEmail_SuccessfulReset() {
        configureRegisterMockForSuccess();
        var rider = new Rider();
        rider.change_password_token("token");
        final String NewPassword = "newRandomPassword";
        configureUserRepositoryChangePassword(rider);
        try {
            when(userRepository.getUserByEmail(email)).thenReturn(rider);
            when(utils.generateRandomPassword()).thenReturn(NewPassword);
            when(passwordManager.hash(NewPassword)).thenReturn("newHash");
            assertDoesNotThrow(() -> userController.resetPassword(email));
            verify(userRepository, times(1)).changeUserPassword(any(), any());
            assertTrue(rider.get_password_token() == "newHash");
        }
        catch (Exception e) {
            fail("shouldn't happen");
        }
    }

    @Test
    void resetPassword_invalidEmail_FailedReset() {
        configureRegisterMockForSuccess();
        final String NewPassword = "newRandomPassword";
        try {
            doCallRealMethod().when(userRepository).changeUserPassword(any(), any());
            when(userRepository.getUserByEmail(email)).thenReturn(null);
            when(utils.generateRandomPassword()).thenReturn(NewPassword);
            when(passwordManager.hash(NewPassword)).thenReturn("newHash");
            assertThrows(UserNotFoundException.class, () -> userController.resetPassword(email));
            verify(userRepository, times(1)).changeUserPassword(any(), any());
        }
        catch (Exception e) {
            fail("shouldn't happen");
        }
    }

    private void configureRegisterMockForSuccess() {
        try {
            doNothing().when(utils).emailValidCheck(anyString());
            doNothing().when(utils).passwordValidCheck(anyString());
            doNothing().when(utils).validate_phone_number(anyString());
            doNothing().when(utils).validate_birth_date(any());
            doNothing().when(utils).validate_gender(anyString());
            doNothing().when(utils).validate_scooter_type(anyString());
            doNothing().when(utils).validate_license_issue_date(any());
            when(userRepository.getUserByEmail(anyString())).thenReturn(null);
            when(serialsRepository.isExists(anyString())).thenReturn(false);
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
    }

    @Test
    public void getUserByEmail_invalidEmail_NoUserReturned() {
        try {
            when(userRepository.getUserByEmail(any())).thenThrow(new UserNotFoundException());
            assertThrows(UserNotFoundException.class, () -> userController.get_user_by_email("someEmail@gmail.com"));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getUserByEmail_validEmail_userReturned() {
        var user = new Rider();
        try {
            when(userRepository.getUserByEmail(any())).thenReturn(user);
            var user2 = userController.get_user_by_email("someEmail@gmail.com");
            assertEquals(user, user2);
        } catch (UserNotFoundException e) {
            fail("should return user and not throw exception");
        }
    }

    @Test
    public void updateUser_validInfo_userUpdated() {
        var rider = new Rider();
        configureUserRepositoryUpdateUser(rider);
        configureRegisterMockForSuccess();
        var newPhone = "0502224404";
        var newName = "name";
        var newLastName = "last";
        var newGender = "male";
        var newScooter = "scooter";
        var newBirthDate = LocalDate.now();
        try {
            when(userRepository.getUserByEmail(anyString())).thenReturn(rider);
            assertDoesNotThrow(() -> userController.update_information("", newName, newLastName, newPhone, newBirthDate, newGender, newScooter));
            assertTrue(rider.getName() == newName);
            assertTrue(rider.getLastName() == newLastName);
            assertTrue(rider.get_gender() == newGender);
            assertTrue(rider.get_birth_day() == newBirthDate);
            assertTrue(rider.getScooterType() == newScooter);
            assertTrue(rider.get_phone_number() == newPhone);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateUser_invalidPhoneNumber_userNotUpdated() {
        var rider = new Rider();
        configureUserRepositoryUpdateUser(rider);
        configureRegisterMockForSuccess();
        var newPhone = "0502224404";
        var newName = "name";
        var newLastName = "last";
        var newGender = "male";
        var newScooter = "scooter";
        var newBirthDate = LocalDate.now();
        try {
            when(userRepository.getUserByEmail(anyString())).thenReturn(rider);
            doThrow(Exception.class).when(utils).validate_phone_number(newPhone);
            assertThrows(Exception.class, () -> userController.update_information("", newName, newLastName, newPhone, newBirthDate, newGender, newScooter));
            verifyRiderInfoIsNotChanged(newName, newLastName, newPhone, newBirthDate, newGender, newScooter,rider);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void updateUser_invalidBirthDate_userNotUpdated() {
        var rider = new Rider();
        configureUserRepositoryUpdateUser(rider);
        configureRegisterMockForSuccess();
        var newPhone = "0502224404";
        var newName = "name";
        var newLastName = "last";
        var newGender = "male";
        var newScooter = "scooter";
        var newBirthDate = LocalDate.now();
        try {
            when(userRepository.getUserByEmail(anyString())).thenReturn(rider);
            doThrow(Exception.class).when(utils).validate_birth_date(newBirthDate);
            assertThrows(Exception.class, () -> userController.update_information("", newName, newLastName, newPhone, newBirthDate, newGender, newScooter));
            verifyRiderInfoIsNotChanged(newName, newLastName, newPhone, newBirthDate, newGender, newScooter,rider);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateUser_invalidGender_userNotUpdated() {
        var rider = new Rider();
        configureUserRepositoryUpdateUser(rider);
        configureRegisterMockForSuccess();
        var newPhone = "0502224404";
        var newName = "name";
        var newLastName = "last";
        var newGender = "male";
        var newScooter = "scooter";
        var newBirthDate = LocalDate.now();
        try {
            when(userRepository.getUserByEmail(anyString())).thenReturn(rider);
            doThrow(Exception.class).when(utils).validate_gender(newGender);
            assertThrows(Exception.class, () -> userController.update_information("", newName, newLastName, newPhone, newBirthDate, newGender, newScooter));
            verifyRiderInfoIsNotChanged(newName, newLastName, newPhone, newBirthDate, newGender, newScooter,rider);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateUser_invalidScooterType_userNotUpdated() {
        var rider = new Rider();
        configureUserRepositoryUpdateUser(rider);
        configureRegisterMockForSuccess();
        var newPhone = "0502224404";
        var newName = "name";
        var newLastName = "last";
        var newGender = "male";
        var newScooter = "scooter";
        var newBirthDate = LocalDate.now();
        try {
            when(userRepository.getUserByEmail(anyString())).thenReturn(rider);
            doThrow(Exception.class).when(utils).validate_scooter_type(newScooter);
            assertThrows(Exception.class, () -> userController.update_information("", newName, newLastName, newPhone, newBirthDate, newGender, newScooter));
            verifyRiderInfoIsNotChanged(newName, newLastName, newPhone, newBirthDate, newGender, newScooter,rider);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void verifyRiderInfoIsNotChanged(String newName, String newLastName, String newPhone, LocalDate newBirthDate, String newGender, String newScooter, Rider rider) {
        assertTrue(rider.getName() != newName);
        assertTrue(rider.getLastName() != newLastName);
        assertTrue(rider.get_gender() != newGender);
        assertTrue(rider.get_birth_day() != newBirthDate);
        assertTrue(rider.getScooterType() != newScooter);
        assertTrue(rider.get_phone_number() != newPhone);
    }

    private void configureUserRepositoryChangePassword(Rider rider) {
        try{
            doAnswer(invocation -> {
                Object[] args = invocation.getArguments();
                String password = (String) args[1];
                rider.change_password_token(password);
                return null;
            }).when(userRepository).changeUserPassword(any(), anyString());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
    }

    private void configureUserRepositoryUpdateUser(Rider rider) {
        try{
            doAnswer(invocation -> {
                Object[] args = invocation.getArguments();
                var name = (String) args[1];
                var lastName = (String) args[2];
                var phone = (String) args[3];
                var birthDate = (LocalDate) args[4];
                var gender = (String) args[5];
                var scooterType = (String) args[6];
                rider.update_information(name, lastName, phone, birthDate, gender);
                rider.setScooterType(scooterType);
                return null;
            }).when(userRepository).updateUser(any(), any(), any(), any(), any(),any(),any());
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
    }
}