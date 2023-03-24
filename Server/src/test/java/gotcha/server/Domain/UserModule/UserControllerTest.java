package gotcha.server.Domain.UserModule;

import gotcha.server.Config.Configuration;
import gotcha.server.Domain.QuestionsModule.IQuestionController;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import gotcha.server.Utils.Password.PasswordManagerImpl;
import gotcha.server.Utils.Password.iPasswordManager;
import gotcha.server.Utils.Utils;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_userExists_successfullyLoggedIn() {
        var testEmail = "test@gmail.com";
        var testPassword = "testPassword";
        var passwordToken = "testToken";
        var user = new Rider();
        user.change_password_token(passwordToken);
        when(userRepository.getUser(testEmail)).thenReturn(user);
        when(passwordManager.authenticate(testPassword, passwordToken)).thenReturn(true);
        try{
            var userResult = userController.login(testEmail,testPassword);
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
        when(userRepository.getUser(testEmail)).thenReturn(null);
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
        when(userRepository.getUser(testEmail)).thenReturn(user);
        when(passwordManager.authenticate(testPassword, passwordToken)).thenReturn(false);
        assertThrows(Exception.class, () -> {
            userController.login(testEmail,testPassword);
        });
    }

    @Test
    void login_userAlreadyLoggedIn_failedLogin() {
        var testEmail = "test@gmail.com";
        var testPassword = "testPassword";
        login_userExists_successfullyLoggedIn();
        assertThrows(Exception.class, () -> {
           userController.login(testEmail,testPassword);
        });
    }
}