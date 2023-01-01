package gotcha.server.Utils.Logger;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ErrorLogger {

    /*------------------------------------------------------ FIELDS ------------------------------------------------------*/

    private Logger logger;
    private FileHandler handler;

    /*------------------------------------------------ SINGLETON BUSINESS ------------------------------------------------*/

    private static class SingletonHolder{
        private static ErrorLogger instance = new ErrorLogger();
    }

    public static ErrorLogger getInstance(){
        return ErrorLogger.SingletonHolder.instance;
    }

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
