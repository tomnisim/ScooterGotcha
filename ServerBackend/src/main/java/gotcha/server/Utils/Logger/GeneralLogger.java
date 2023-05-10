package gotcha.server.Utils.Logger;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class GeneralLogger {

    protected String logger_address;
    protected Logger logger;

    /*------------------------------------------------- CLASS CONSTRUCTOR -------------------------------------------------*/

    public GeneralLogger(String loggerName) {
        createFolder("logFiles");
        this.logger_address = "logFiles\\" + loggerName + ".txt";
        this.logger = Logger.getLogger(loggerName);
        try {
            FileHandler handler = new FileHandler(logger_address);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);

        }
        catch(Exception ignored){}
    }

    private void createFolder(String folderName) {
        File logDir = new File(folderName);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
    }

    /*-------------------------------------------------- CLASS FUNCTIONS --------------------------------------------------*/

    public void add_log(String message){
        this.logger.info(message);
    }

    @Override
    public String toString() {
        String answer = "";
        File file = new File(logger_address);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        while (scanner.hasNextLine()) {
            String instruction = scanner.nextLine();
            answer = answer + "\n" + instruction;
        }
        return answer;
    }
}



















