package gotcha.server.Service;

import gotcha.server.Utils.Exceptions.ExitException;
import org.junit.jupiter.api.Test;

import static gotcha.server.Service.MainSystem.EXTERNAL_SERVICE_MODE;
import static gotcha.server.Service.MainSystem.FIRST_TIME_RUNNING;
import static org.junit.jupiter.api.Assertions.*;

class MainSystemTest {

    @Test
    void set_static_vars() throws ExitException {
        System.out.println(FIRST_TIME_RUNNING);
        System.out.println(EXTERNAL_SERVICE_MODE);
        MainSystem mainSystem = new MainSystem("C:\\Users\\Amit\\Desktop\\ScooterGotcha\\server\\src\\main\\java\\gotcha\\server\\Config\\server_config.txt");
        System.out.println(FIRST_TIME_RUNNING);
        System.out.println(EXTERNAL_SERVICE_MODE);
    }
}