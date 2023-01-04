package gotcha.server.Service;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.Service.API.AdminAPI;
import gotcha.server.Service.API.ProgrammerAPI;
import gotcha.server.Service.API.UserAPI;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import gotcha.server.Utils.Response;
import gotcha.server.Utils.Utils;

import java.time.LocalDateTime;
import java.util.Dictionary;
import java.util.List;

public class Facade implements AdminAPI, ProgrammerAPI, UserAPI {
    private ErrorLogger error_logger;
    private ServerLogger serverLogger;
    private QuestionController question_controller;
    private RidesController rides_controller;
    private UserController user_controller;
    private User loggedUser;
    // TODO: 30/12/2022 : remove // after open class "Route"
//    private RoutesRetriever routes_retriever;

    public Facade() {
        this.error_logger = ErrorLogger.get_instance();
        this.serverLogger = ServerLogger.get_instance();
        this.question_controller = QuestionController.get_instance();
        this.rides_controller = RidesController.get_instance();
        this.user_controller = UserController.get_instance();
        // TODO: 30/12/2022 : remove // after open class "Route"
//        this.routes_retriever = RoutesRetriever.getInstance();
    }


     @Override
    public Response view_all_open_questions() {
        Response response = null;
        try{
            List<String> questions = question_controller.get_all_open_questions();
            String logger_message = "admin view all user questions";
            response = new Response(questions, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


     @Override
    public Response answer_user_question(int question_id, String answer, Admin admin) {
        Response response = null;
        try{
            question_controller.answer_user_question(question_id, answer, admin);
            String logger_message = "admin answer question : " + question_id;
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

     @Override
    public Response send_message_to_all_users(String message) {
//        user_controller.send_message_to_all_users(message);
        return null;
    }

     @Override
    public Response view_rides() {
        return null;
    }

     @Override
    public Response view_statistics() {
        return null;
    }

     @Override
    public Response view_awards() {
        return null;
    }

     @Override
    public Response add_award() {
        return null;
    }

     @Override
    public Response delete_award() {
        return null;
    }

     @Override
    public Response view_admins() {
        return null;
    }

     @Override
    public Response add_admin() {
        return null;
    }

     @Override
    public Response delete_admin() {
        return null;
    }

     @Override
    public Response view_users() {
        return null;
    }

     @Override
    public Response edit_user() {
        return null;
    }

     @Override
    public Response delete_user() {
        return null;
    }

     @Override
    public Response set_server_config() {
        return null;
    }

     @Override
    public Response set_rp_config() {
        return null;
    }

     @Override
    public Response view_error_logger() {
        return null;
    }

     @Override
    public Response view_system_logger() {
        return null;
    }

     @Override
    public Response view_server_logger() {
        return null;
    }

     @Override
    public Response reset() {
        return null;
    }

     @Override
    public Response shout_down() {
        return null;
    }

     @Override
    public Response update_user_rate_tables(Dictionary<String, Dictionary<Integer, Integer>> tables) {
        return null;
    }

     @Override
    public Response update_hazard_formula(HazardType type, Formula formula) {
        return null;
    }

     @Override
    public Response register(String email, String password, String name, String last_name, String birth_date, String phone_number, String gender) {
        return null;
    }

     @Override
    public Response login(String email, String password) {
        return null;
    }

     @Override
    public Response logout() {
        return null;
    }

     @Override
    public Response change_password(String old_password, String password) {
        Response response = null;
        try {
//            String email = user_controller.edit_password(loggedUser, old_password, password);
//            response = new Response<>(password, email + " password has been changed successfully");
//            serverLogger.add_log("User's (" + email + ")  password has been changed successfully.");
        }

        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

     @Override
    public Response view_user_rides_history() {
        return null;
    }

     @Override
    public Response add_user_question(String message, User sender) {
        Response response = null;
        try{
            question_controller.add_user_question(message, sender);
            String logger_message = sender.get_email() + " add user question";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

     @Override
    public Response view_all_user_questions(String user_email) {
        Response response = null;
        try{
            List<String> questions = question_controller.get_all_user_questions(user_email);
            String logger_message = user_email+ " view all user questions";
            response = new Response(questions, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     *
     * @param origin
     * @param destination
     * @param user
     * @return sorted & suggested routes & ride id
     */
    @Override
    public Response start_ride(Location origin, Location destination, User user) {
//        this.routes_retriever.fetch_safe_routes(origin, destination);
//        int ride_id = this.rides_controller.start_ride(user);
         return null;

     }
     @Override
    public Response finish_ride(Location origin, Location destination, String city, LocalDateTime start_time,
                                LocalDateTime end_time, List<StationaryHazard> hazards) {

//        int ride_id = 0;
//        this.ridesController.finish_ride(ride_id, origin, destination, city, start_time, end_time, hazards);
         return null;

     }
}