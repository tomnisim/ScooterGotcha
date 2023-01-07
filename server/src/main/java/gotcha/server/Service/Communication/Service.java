package gotcha.server.Service.Communication;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Service.API.AdminAPI;
import gotcha.server.Service.API.ProgrammerAPI;
import gotcha.server.Service.API.UserAPI;
import gotcha.server.Service.Facade;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;
import gotcha.server.Utils.Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
public class Service implements AdminAPI, UserAPI  {
    private static HashMap<String,Facade> FacadeMap;

    public Service(){
        this.FacadeMap = new HashMap<>();

    }


    private Facade get_facade(String user_id){
        if(!FacadeMap.containsKey(user_id)){
            FacadeMap.put(user_id, new Facade());
        }
        return FacadeMap.get(user_id);
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
        Response response = facade.login(email, password);
        if (!response.iswas_exception()) {
            FacadeMap.put(email, facade);
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
    public Response logout(String user_id) {
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

    @Override
    public Response reset_password(String user_id) {
        var facade = get_facade(user_id);
        var response = facade.reset_password();
        return response;
    }

    @RequestMapping(value = "/change_password")
    @CrossOrigin
    @Override
    public Response change_password(String old_password, String password, String user_id) {
        Facade facade = get_facade(user_id);
        return facade.change_password(old_password, password);
    }

    @RequestMapping(value = "/view_user_rides_history")
    @CrossOrigin
    public Response view_user_rides_history(String user_id){
        Facade facade = get_facade(user_id);
        return facade.view_user_rides_history();
    }

    @RequestMapping(value = "/add_user_question")
    @CrossOrigin
    @Override
    public Response add_user_question(String message, String user_id) {
        Facade facade = get_facade(user_id);
        return facade.add_user_question(message);
    }

    @RequestMapping(value = "/view_all_user_questions")
    @CrossOrigin
    @Override
    public Response view_all_user_questions(String user_id) {
        Facade facade = get_facade(user_id);
        return facade.view_all_user_questions();
    }

    @RequestMapping(value = "/get_safe_routes")
    @CrossOrigin
    @Override
    public Response get_safe_routes(Location origin, Location destination, String user_id) {
        Facade facade = get_facade(user_id);
        return facade.get_safe_routes(origin, destination);
    }

    @RequestMapping(value = "/view_all_advertisement")
    @CrossOrigin
    @Override
    public Response view_all_advertisement(String user_id) {
        Facade facade = get_facade(user_id);
        return facade.view_all_advertisements();
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
    public Response finish_ride(String user_id, Location origin, Location destination, String city, LocalDateTime start_time,
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
    public Response view_all_open_questions(String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.view_all_open_questions();
    }

    @RequestMapping(value = "/answer_user_question")
    @CrossOrigin
    @Override
    public Response answer_user_question(int question_id, String answer, String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.answer_user_question(question_id, answer);
    }

    @RequestMapping(value = "/send_message_to_all_users")
    @CrossOrigin
    @Override
    public Response send_message_to_all_users(String message, String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.send_message_to_all_users(message);
    }

    @RequestMapping(value = "/view_rides")
    @CrossOrigin
    @Override
    public Response view_rides(String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.view_rides();

    }

    @RequestMapping(value = "/view_statistics")
    @CrossOrigin
    @Override
    public Response view_statistics(String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.view_statistics();
    }

    @RequestMapping(value = "/view_advertisements")
    @CrossOrigin
    @Override
    public Response view_advertisements(String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.view_advertisements();
    }

    @RequestMapping(value = "/add_advertisement")
    @CrossOrigin
    @Override
    public Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url, String admin_id) {
        return get_facade(admin_id).add_advertisement(final_date, owner, message, photo, url);
    }

    @RequestMapping(value = "/delete_advertisement")
    @CrossOrigin
    @Override
    public Response delete_advertisement(int advertise_id, String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.delete_advertisement(advertise_id);
    }

    @RequestMapping(value = "/view_awards")
    @CrossOrigin
    @Override
    public Response view_awards(String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.view_awards();
    }

    @RequestMapping(value = "/add_award")
    @CrossOrigin
    @Override
    public Response add_award(String admin_id) {
        return null;
    }

    @RequestMapping(value = "/delete_award")
    @CrossOrigin
    @Override
    public Response delete_award(int award_id, String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.delete_award(award_id);
    }

    @RequestMapping(value = "/view_admins")
    @CrossOrigin
    @Override
    public Response view_admins(String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.view_admins();
    }

    @RequestMapping(value = "/add_admin")
    @CrossOrigin
    @Override
    public Response add_admin(String user_email, String user_password, String admin_id, String phoneNumber, LocalDate birthDay, String gender, String firstName, String lastName) {
        Facade facade = get_facade(admin_id);
        return facade.add_admin(user_email, user_password, phoneNumber, birthDay, gender, firstName, lastName);
    }

    @RequestMapping(value = "/delete_admin")
    @CrossOrigin
    @Override
    public Response delete_admin(String user_email, String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.delete_admin(user_email);
    }

    @RequestMapping(value = "/view_users")
    @CrossOrigin
    @Override
    public Response view_users(String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.view_users();
    }

    @RequestMapping(value = "/edit_user")
    @CrossOrigin
    @Override
    public Response edit_user(String user_email, String admin_id) {
        return null;
    }

    @RequestMapping(value = "/delete_user")
    @CrossOrigin
    @Override
    public Response delete_user(String user_email, String admin_id) {
        Facade facade = get_facade(admin_id);
        return facade.delete_user(user_email);
    }




}
