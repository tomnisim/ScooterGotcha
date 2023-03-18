package gotcha.server.Service;

import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static gotcha.server.Service.MainSystem.ADMIN_PASSWORD;
import static gotcha.server.Service.MainSystem.ADMIN_USER_NAME;
import static org.springframework.test.util.AssertionErrors.*;

class UserAPITests {
    Facade user_facade = new Facade();
    Facade admin_facade = new Facade();



    private static String server_config_path = "C:\\Users\\Amit\\Desktop\\ScooterGotcha\\server\\src\\main\\java\\gotcha\\server\\Config\\server_config.txt";

    String EMAIL = "moskoga@gmail.com";
    String PASSWORD = "123456Aa";
    String NAME = "AMIT";
    String LAST_NAME = "MOSKO";
    String BIRTH_DATE = "19-04-95";
    LocalDate BIRTH_DAY = LocalDate.of(1995, 4,19);
    String PHONE = "0546794211";
    String GENDER = "MALE";

    @BeforeEach
    void setUp() {
        try{
            MainSystem mainSystem = new MainSystem(server_config_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_facade.clear();
        user_facade.register(EMAIL, PASSWORD, NAME, LAST_NAME, BIRTH_DATE, PHONE, GENDER);
        user_facade.login(EMAIL, PASSWORD);
        admin_facade.login(ADMIN_USER_NAME, ADMIN_PASSWORD);

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void get_routes(){
        Location origin = new Location(0.0, 0.0);
        Location dest = new Location(0.0, 0.0);
        Response response = this.user_facade.get_safe_routes(origin, dest);
        int x =5 ;
    }

}