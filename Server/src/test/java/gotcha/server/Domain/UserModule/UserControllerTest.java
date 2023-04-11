package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.QuestionsModule.IQuestionController;
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
        when(userRepository.getUserByEmail(email)).thenReturn(user);
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
        var testEmail = "test@gmail.com";
        var testPassword = "testPassword";
        //user.change_password_token(passwordToken);
        when(userRepository.getUserByEmail(testEmail)).thenReturn(null);
        //when(passwordManager.authenticate(testPassword, passwordToken)).thenReturn(true);
        assertThrows(UserNotFoundException.class, () -> {
            userController.login(testEmail,testPassword);
        });
    }

    @Test
    void login_passwordIsIncorrect_failedLogin() {
        var testEmail = "test@gmail.com";
        var testPassword = "testPassword";
        var passwordToken = "testToken";
        var user = new Rider();
        user.change_password_token(passwordToken);
        when(userRepository.getUserByEmail(testEmail)).thenReturn(user);
        when(passwordManager.authenticate(testPassword, passwordToken)).thenReturn(false);
        assertThrows(Exception.class, () -> {
            userController.login(testEmail,testPassword);
        });
    }

    @Test
    void register_validUserCredentials_successfullyRegistered() {
        configureRegisterMockForSuccess();
        assertDoesNotThrow(() -> userController.add_rp_serial_number(rpSerialNumber));
        assertDoesNotThrow(() -> userController.register(email,password,name,lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber));
    }

    @Test
    void register_userAlreadyExists_failedRegisration() {
        configureRegisterMockForSuccess();
        try {
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
        }
        catch (Exception e) {
            fail("Unexpected exception when configuring the mock: " + e.getMessage());
        }
    }
}