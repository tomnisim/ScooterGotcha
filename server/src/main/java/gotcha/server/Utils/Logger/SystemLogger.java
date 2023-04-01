package gotcha.server.Utils.Logger;

import org.springframework.stereotype.Component;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
@Component
public class SystemLogger extends GeneralLogger {

    public SystemLogger() {
        super("SystemLog");
    }

}

