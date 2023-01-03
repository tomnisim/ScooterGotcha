package gotcha.server.Utils.Exceptions.UserExceptions;

public class UserAlreadyExistsException extends UserException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException() {

    }
}
