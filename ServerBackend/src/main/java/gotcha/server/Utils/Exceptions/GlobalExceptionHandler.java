package gotcha.server.Utils.Exceptions;

import gotcha.server.Utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpSessionRequiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody

    public Response handleHttpSessionRequiredException(HttpSessionRequiredException e) {
        // Customize the error response based on your requirements
        return new Response("User session is not available, please log in.", e);
    }
}
