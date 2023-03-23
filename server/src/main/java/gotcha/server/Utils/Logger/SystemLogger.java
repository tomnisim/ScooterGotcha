package gotcha.server.Utils.Logger;

import org.springframework.stereotype.Component;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
@Component
public class SystemLogger {

    /*------------------------------------------------------ FIELDS ------------------------------------------------------*/

    private Logger logger;
    private FileHandler handler;

    /*------------------------------------------------- CLASS CONSTRUCTOR -------------------------------------------------*/
    public SystemLogger() {
        this.logger = Logger.getLogger("System Logger");
        try {
//            this.handler = new FileHandler("SystemLogger.txt");
            this.handler = new FileHandler("LogFiles/System/SystemLog.txt");
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

