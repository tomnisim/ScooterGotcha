package gotcha.server.Utils.Logger;

import org.springframework.stereotype.Component;

@Component
public class EmailsLogger extends GeneralLogger {

    public EmailsLogger() {
        super("EmailsLog");
    }

}

