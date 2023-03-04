package gotcha.server.Service.Communication;

import gotcha.server.Domain.AdvertiseModule.Advertise;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.QuestionsModule.Question;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.Rider;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Service.API.AdminAPI;
import gotcha.server.Service.API.ProgrammerAPI;
import gotcha.server.Service.API.UserAPI;
import gotcha.server.Service.Facade;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;
import gotcha.server.Utils.Utils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class Service implements AdminAPI, UserAPI, ProgrammerAPI  {
    private static HashMap<Integer,Facade> FacadeMap;
    private LinkedList<Admin> admins_list;
    private LinkedList<Question> questions_list;

    public Service(){
        this.FacadeMap = new HashMap<>();
        admins_list = new LinkedList<>();
        questions_list = new LinkedList<>();
        questions_list.add(new Question("message1", "tom@gmail.com"));
        questions_list.add(new Question("message2", "tom1@gmail.com"));
        questions_list.add(new Question("message3", "tom@2gmail.com"));

    }


    private Facade get_facade(Integer session_id){
        if(!FacadeMap.containsKey(session_id)){
            FacadeMap.put(session_id, new Facade());
        }
        return FacadeMap.get(session_id);
    }


    @RequestMapping(value = "/test")
    @CrossOrigin
    public Response test(){
        System.out.println("test");
        return new Response();
    }

    @RequestMapping(value = "/test_params")
    @CrossOrigin
    public Response test_params(int user_id){
        System.out.println("test params : " +user_id);
        return new Response();
    }



    /**
     * this method will create a new facade for the connection.
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    @CrossOrigin
    @Override
    public Response login(String email, String password){
        System.out.println("login");
        Facade facade = new Facade();
        Response<Integer> response = facade.login(email, password);
        // TODO: 04/01/2023 : have to check somehow if the login success, if it is, put in the facade map.
        if (!response.iswas_exception()){
            int session_id = 5; // TODO: 04/03/2023 : generate random session id.
            FacadeMap.put(session_id, facade);
            response.setValue(session_id);
        }

        return response;
    }


    /**
     * this method will clear the facade.
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/logout")
    @CrossOrigin
    @Override
    public Response logout(int user_id) {
        Facade facade = get_facade(user_id);
        Response response = facade.logout();
        FacadeMap.remove(user_id);
        return response;
    }

    @RequestMapping(value = "/register")
    @CrossOrigin
    @Override
    public Response register(String email, String password, String name, String last_name, String birth_date, String phone_number, String gender) {
        // TODO: 04/01/2023 : implement according new sequence diagram
        return null;
    }

    @RequestMapping(value = "/change_password")
    @CrossOrigin
    @Override
    public Response change_password(String old_password, String password, int user_id) {
        Facade facade = get_facade(user_id);
        return facade.change_password(old_password, password);
    }

    @RequestMapping(value = "/view_user_rides_history")
    @CrossOrigin
    public Response view_user_rides_history(int user_id){
        Facade facade = get_facade(user_id);
        return facade.view_user_rides_history();
    }

    @RequestMapping(value = "/add_user_question")
    @CrossOrigin
    @Override
    public Response add_user_question(String message, int user_id) {
        String str = "" + user_id;
        this.questions_list.add(new Question(message, str));
        return null;
    }

    @RequestMapping(value = "/view_all_user_questions")
    @CrossOrigin
    @Override
    public Response view_all_user_questions(int user_id) {
        Facade facade = get_facade(user_id);
        return facade.view_all_user_questions();
    }

    @RequestMapping(value = "/get_safe_routes")
    @CrossOrigin
    @Override
    public Response get_safe_routes(Location origin, Location destination, int user_id) {
        Facade facade = get_facade(user_id);
        return facade.get_safe_routes(origin, destination);
    }

    @RequestMapping(value = "/view_all_advertisement")
    @CrossOrigin
    @Override
    public Response view_all_advertisement(int user_id) {
        Facade facade = get_facade(user_id);
        return facade.view_all_advertisements();
    }

    @RequestMapping(value = "/view_notifications")
    @CrossOrigin
    @Override
    public Response view_notifications(int user_id) {
        Facade facade = get_facade(user_id);
        return facade.view_notifications();
    }



    /* the methods for RP */


    /**
     * this method is for RP usage, when user is not have to be logged in.
     * this method will create a new facade for rp connection and remove it after finish in case of dismiss connection,
     * and will use an existing facade and not remove him else.
     * @param user_id
     * @param origin
     * @param destination
     * @param city
     * @param start_time
     * @param end_time
     * @param hazards
     * @return
     */
    @RequestMapping(value = "/finish_ride")
    @CrossOrigin
    @Override
    public Response finish_ride(int user_id, Location origin, Location destination, String city, LocalDateTime start_time,
                                LocalDateTime end_time, List<StationaryHazard> hazards) {
        boolean remove_facade_flag = true;
        if(FacadeMap.containsKey(user_id)){
            remove_facade_flag = false;
        }

        Facade facade = get_facade(user_id);
        Response response = facade.finish_ride(user_id, origin, destination, city, start_time, end_time, hazards);
        if (remove_facade_flag)
            FacadeMap.remove(user_id);
        return response;
    }

    /**
     * this method is for RP usage, when user is not have to be logged in.
     * this method will create a new facade for rp connection and remove it after finish in case of dismiss connection,
     * and will use an existing facade and not remove him else.
     * @return
     */
    @RequestMapping(value = "/get_rp_config_file")
    @CrossOrigin
    @Override
    public Response get_rp_config_file() {
        String config_file_data = Utils.get_rp_config_file_data();
        Response response = new Response(config_file_data, "rp config file got successfully");
        return response;
    }








    // ADMIN METHODS




    @RequestMapping(value = "/view_all_open_questions")
    @CrossOrigin
    @Override
    public Response view_all_open_questions(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_all_open_questions();

    }

    @RequestMapping(value = "/answer_user_question")
    @CrossOrigin
    @Override
    public Response answer_user_question(int question_id, String answer, int session_id) {
        Facade facade = get_facade(session_id);
        return facade.answer_user_question(question_id, answer);
    }

    @RequestMapping(value = "/send_message_to_all_users")
    @CrossOrigin
    @Override
    public Response send_message_to_all_users(String message, int session_id) {
        Facade facade = get_facade(session_id);
        return facade.send_message_to_all_users(message);
    }

    @RequestMapping(value = "/view_rides")
    @CrossOrigin
    @Override
    public Response view_rides(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_rides();

    }

    @RequestMapping(value = "/view_statistics")
    @CrossOrigin
    @Override
    public Response view_statistics(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_statistics();
    }

    @RequestMapping(value = "/view_advertisements")
    @CrossOrigin
    @Override
    public Response view_advertisements(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_advertisements();
    }

    @RequestMapping(value = "/add_advertisement")
    @CrossOrigin
    @Override
    public Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url, int session_id) {
        Facade facade = get_facade(session_id);
        return facade.add_advertisement(final_date, owner, message, photo, url);
    }

    @RequestMapping(value = "/delete_advertisement")
    @CrossOrigin
    @Override
    public Response delete_advertisement(int advertise_id, int session_id) {
        Facade facade = get_facade(session_id);
        return facade.delete_advertisement(advertise_id);
    }

    @RequestMapping(value = "/view_awards")
    @CrossOrigin
    @Override
    public Response view_awards(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_awards();
    }

    @RequestMapping(value = "/add_award")
    @CrossOrigin
    @Override
    public Response add_award(int session_id, String[] emails, String award) {
        Facade facade = get_facade(session_id);
        return facade.add_award(emails, award);
    }



    @RequestMapping(value = "/view_admins")
    @CrossOrigin
    @Override
    public Response view_admins(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_admins();
    }

    @RequestMapping(value = "/add_admin")
    @CrossOrigin
    @Override
    public Response add_admin(String user_email, String user_password, int session_id, String phoneNumber, String birthDay, String gender) {
        Facade facade = get_facade(session_id);
        return facade.add_admin(user_email, user_password, phoneNumber, Utils.StringToLocalDate(birthDay), gender);
    }

    @RequestMapping(value = "/delete_admin")
    @CrossOrigin
    @Override
    public Response delete_admin(String user_email, int session_id) {
        Facade facade = get_facade(session_id);
        return facade.delete_admin(user_email);
    }

    @RequestMapping(value = "/view_users")
    @CrossOrigin
    @Override
    public Response view_users(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_users();
    }


    @RequestMapping(value = "/delete_user")
    @CrossOrigin
    @Override
    public Response delete_user(String user_email, int session_id) {
        Facade facade = get_facade(session_id);
        return facade.delete_user(user_email);
    }


    // Programmer API

    @RequestMapping(value = "/set_server_config")
    @CrossOrigin
    @Override
    public Response set_server_config(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.set_server_config();
    }

    @RequestMapping(value = "/set_rp_config")
    @CrossOrigin
    @Override
    public Response set_rp_config(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.set_rp_config();
    }

    @RequestMapping(value = "/view_error_logger")
    @CrossOrigin
    @Override
    public Response view_error_logger(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_error_logger();
    }

    @RequestMapping(value = "/view_system_logger")
    @CrossOrigin
    @Override
    public Response view_system_logger(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_system_logger();
    }

    @RequestMapping(value = "/view_server_logger")
    @CrossOrigin
    @Override
    public Response view_server_logger(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.view_server_logger();
    }

    @RequestMapping(value = "/reset")
    @CrossOrigin
    @Override
    public Response reset(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.reset();
    }

    @RequestMapping(value = "/shut_down")
    @CrossOrigin
    @Override
    public Response shut_down(int session_id) {
        Facade facade = get_facade(session_id);
        return facade.shut_down();
    }
}
