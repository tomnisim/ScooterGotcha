package gotcha.server.Utils.Exceptions.UserExceptions;

public class InvalidUserInformationException extends UserException{
    public InvalidUserInformationException(String message) {
        super(message);
    }

    public InvalidUserInformationException() {}
}
