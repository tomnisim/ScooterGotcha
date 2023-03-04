package gotcha.server.Service;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Utils.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static gotcha.server.Service.MainSystem.ADMIN_PASSWORD;
import static gotcha.server.Service.MainSystem.ADMIN_USER_NAME;
import static org.springframework.test.util.AssertionErrors.*;

class AdminAPITests {
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
    void view_all_open_questions() {
        user_facade.add_user_question("why?");
        user_facade.add_user_question("where?");
        Response response = admin_facade.view_all_open_questions();

        int x = 5;

    }

    @Test
    void answer_user_question() {
        user_facade.add_user_question("why?");
        Response response1 = admin_facade.view_all_open_questions();
        admin_facade.answer_user_question(0, "because");
        Response response2 = admin_facade.view_all_open_questions();
        assertNotEquals("different lists", response1.getValue(), response2.getValue());
        admin_facade.view_error_logger();


    }

    @Test
    void send_message_to_all_users() {
        Response response1 = user_facade.view_notifications();
        Response response = admin_facade.send_message_to_all_users("hello users");
        Response response2 = user_facade.view_notifications();

    }



    @Test
    void add_and_view_advertisements() {
        LocalDateTime final_date = LocalDateTime.now();
        admin_facade.add_advertisement(final_date, "owner", "message", "photo", "url");
        Response response1 = admin_facade.view_advertisements();
        Response response2 = user_facade.view_advertisements();
        Response response3 = user_facade.view_all_advertisements();
        assertNotEquals("user cant view admins", response2.getValue(), response1.getValue());


    }

    @Test
    void delete_advertisement() {
        LocalDateTime final_date = LocalDateTime.now();
        Response response = admin_facade.add_advertisement(final_date, "owner", "message", "photo", "url");
        Response response1 = user_facade.view_all_advertisements();
        admin_facade.delete_advertisement((int)response.getValue());
        Response response2 = user_facade.view_all_advertisements();
        assertNotEquals("delete advertise", response2.getValue(), response1.getValue());
    }

    @Test
    void add_and_view_awards() {
        Response response = user_facade.view_awards();
        Response response1 = admin_facade.view_awards();
        assertTrue("user is not an admin", response.iswas_exception());
        assertFalse("user is an admin", response1.iswas_exception());
        //
        String[] emails = new String[3];
        emails[0] = EMAIL;
        Response response2 = admin_facade.add_award(emails, "code coppun:059");
        Response response3 = user_facade.view_notifications();
        assertFalse("", response2.iswas_exception());
        assertFalse("", response3.iswas_exception());
    }

    @Test
    void view_admins() {
        Response response = user_facade.view_admins();
        Response response1 = admin_facade.view_admins();
        assertNotEquals("user cant view admins", response.getValue(), response1.getValue());

    }

    @Test
    void add_and_delete_admin() {
        Response response = admin_facade.add_admin(EMAIL+"aaa", PASSWORD, PHONE, BIRTH_DAY, GENDER);
        Response response1 = admin_facade.view_admins();
        Response response2 = admin_facade.delete_admin(EMAIL+"aaa");
        Response response3 = admin_facade.view_admins();
        assertNotEquals("admin has been removed", response1.getValue(), response3.getValue());
    }

    @Test
    void delete_view_users() {
        Response response = admin_facade.view_users();
        Response response1 = admin_facade.delete_user(EMAIL);
        Response response2 = admin_facade.view_users();
        assertNotEquals("user has been removed", response.getValue(), response2.getValue());


    }

//    Response edit_user(String user_email,String phoneNumber, String gender, int admin_id);





    @Test
    void view_rides() {


    }

    @Test
    void view_statistics() {
        Response response = admin_facade.view_statistics();
        int x =6 ;
    }

}