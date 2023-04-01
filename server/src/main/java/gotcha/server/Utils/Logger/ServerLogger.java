package gotcha.server.Utils.Logger;

import org.springframework.stereotype.Component;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
@Component
public class ServerLogger {

    /*------------------------------------------------------ FIELDS ------------------------------------------------------*/

    private final String logger_address = "C:\\Users\\amitm\\Desktop\\SemH\\ScooterGotcha\\Server\\logFiles\\ServerLog.txt";
    private Logger logger;
    private FileHandler handler;

    /*------------------------------------------------- CLASS CONSTRUCTOR -------------------------------------------------*/
    public ServerLogger() {
        this.logger = Logger.getLogger("Server Logger");
        try {
//            this.handler = new FileHandler("SystemLogger.txt");
            this.handler = new FileHandler(logger_address);

            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);

        }
        catch(Exception e){}
    }

    /*-------------------------------------------------- CLASS FUNCTIONS --------------------------------------------------*/
    public void add_log(String info){
        this.logger.info(info);
    }


}

