package gotcha.server.Utils.Logger;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ServerLogger {

    /*------------------------------------------------------ FIELDS ------------------------------------------------------*/

    private Logger logger;
    private FileHandler handler;

    /*------------------------------------------------ SINGLETON BUSINESS ------------------------------------------------*/
    private static class SingletonHolder{
        private static ServerLogger instance = new ServerLogger();
    }

    public static ServerLogger get_instance(){
        return ServerLogger.SingletonHolder.instance;
    }

    /*------------------------------------------------- CLASS CONSTRUCTOR -------------------------------------------------*/
    public ServerLogger() {
        this.logger = Logger.getLogger("Server Logger");
        try {
//            this.handler = new FileHandler("SystemLogger.txt");
            this.handler = new FileHandler("LogFiles/ServerLogger.txt");
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

