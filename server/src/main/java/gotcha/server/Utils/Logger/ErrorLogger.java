package gotcha.server.Utils.Logger;

import org.springframework.stereotype.Component;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
public class ErrorLogger {

    /*------------------------------------------------------ FIELDS ------------------------------------------------------*/

    private Logger logger;
    private FileHandler handler;

    /*------------------------------------------------- CLASS CONSTRUCTOR -------------------------------------------------*/

    public ErrorLogger() {
        this.logger = Logger.getLogger("Error Logger");
        try {
//            this.handler = new FileHandler("ErrorLogger.txt");
            this.handler = new FileHandler("/LogFiles/ErrorLog.txt");

            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);

        }
        catch(Exception e){}
    }

    /*-------------------------------------------------- CLASS FUNCTIONS --------------------------------------------------*/

    public void add_log(String message){
        this.logger.severe(message);
    }

}
