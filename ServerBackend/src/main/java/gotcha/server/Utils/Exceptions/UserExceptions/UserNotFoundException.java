package gotcha.server.Utils.Exceptions.UserExceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {

    }
}
