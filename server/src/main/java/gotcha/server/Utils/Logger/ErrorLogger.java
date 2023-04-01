package gotcha.server.Utils.Logger;
import org.springframework.stereotype.Component;


@Component
public class ErrorLogger extends GeneralLogger {

    public ErrorLogger() {
        super("ErrorLog");
    }


}
