package gotcha.server.Utils.Logger;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
@Component
public class ServerLogger extends GeneralLogger {

    public ServerLogger() {
        super("ServerLog");
    }

}

