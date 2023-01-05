package gotcha.server.Service;

import gotcha.server.Domain.AdvertiseModule.Advertise;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.Service.API.AdminAPI;
import gotcha.server.Service.API.ProgrammerAPI;
import gotcha.server.Service.API.UserAPI;
import gotcha.server.Utils.Exceptions.UserExceptions.UserException;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import gotcha.server.Utils.Response;
import gotcha.server.Utils.Utils;

import java.time.LocalDateTime;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

public class Facade  {
    private MapsAdapter mapsAdapter;
    private ErrorLogger error_logger;
    private ServerLogger serverLogger;
    private QuestionController question_controller;
    private RidesController rides_controller;
    private UserController user_controller;
    private AdvertiseController advertise_controller;
    private HazardController hazard_controller;
    private User loggedUser;
    // TODO: 30/12/2022 : remove // after open class "Route"
//    private RoutesRetriever routes_retriever;

    public Facade() {
        this.error_logger = ErrorLogger.get_instance();
        this.serverLogger = ServerLogger.get_instance();
        this.question_controller = QuestionController.get_instance();
        this.rides_controller = RidesController.get_instance();
        this.user_controller = UserController.get_instance();
        this.advertise_controller = AdvertiseController.get_instance();
        this.hazard_controller = HazardController.get_instance();
        // TODO: 30/12/2022 : remove // after open class "Route"
//        this.routes_retriever = RoutesRetriever.getInstance();
    }



    private void check_user_is_logged_in() throws UserException {
        if (this.loggedUser == null)
            throw new UserException("user is not logged in");
    }


    private void check_user_is_admin_and_logged_in() throws UserException {
        if (this.loggedUser == null)
            throw new UserException("user is not logged in");
        if (!this.loggedUser.is_admin()){
            throw new UserException("user is not an admin");
        }
    }












    // PROGRAMMER
     
    public Response set_server_config() {
        return null;
    }

     
    public Response set_rp_config() {
        return null;
    }

     
    public Response view_error_logger() {
        return null;
    }

     
    public Response view_system_logger() {
        return null;
    }

     
    public Response view_server_logger() {
        return null;
    }

     
    public Response reset() {
        return null;
    }

     
    public Response shout_down() {
        return null;
    }

     
    public Response update_user_rate_tables(Dictionary<String, Dictionary<Integer, Integer>> tables) {
        return null;
    }

     
    public Response update_hazard_formula(HazardType type, Formula formula) {
        return null;
    }












    // USER

    public Response register(String email, String password, String name, String last_name, String birth_date, String phone_number, String gender) {
        return null;
    }
    public Response<Integer> login(String email, String password) {
        return null;
    }
    public Response logout() {
        return null;
    }

     
    public Response change_password(String old_password, String password) {
        Response response = null;
        try {
            check_user_is_logged_in();
            String email = user_controller.change_password(loggedUser, old_password, password);
            String logger_message = "User's (" + email + ")  password has been changed successfully.";
            response = new Response<>(password, logger_message);
            serverLogger.add_log(logger_message);
        }

        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
    public Response view_user_rides_history() {
        Response response = null;
        try{
            check_user_is_logged_in();
            String user_email = loggedUser.get_email();
            int user_id = 0; // TODO: 04/01/2023 : get id from logged user
            List<Ride> rides = this.rides_controller.get_rides(user_id);
            String logger_message = user_email+ " view rides history";
            response = new Response(rides, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
    public Response add_user_question(String message) {
        Response response = null;
        try{
            check_user_is_logged_in();
            String user_email = this.loggedUser.get_email();
            user_controller.send_question_to_admin(user_email, message);
            String logger_message = loggedUser.get_email() + " add user question";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
    public Response view_all_user_questions() {
        Response response = null;
        try{
            check_user_is_logged_in();
            String user_email = loggedUser.get_email();
            List<String> questions = question_controller.get_all_user_questions(user_email);
            String logger_message = user_email+ " view all user questions";
            response = new Response(questions, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
    public Response view_all_advertisements() {
        Response response = null;
        try{
            check_user_is_logged_in();
            List<String> advs = advertise_controller.get_all_advertisements_for_user();
            String logger_message = "user( "+loggedUser.get_email()+ ") view all advertisements";
            response = new Response(advs, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    /**
     *
     * @param origin
     * @param destination
     * @return sorted & suggested routes & ride id
     */
    
    public Response get_safe_routes(Location origin, Location destination) {
//        this.routes_retriever.fetch_safe_routes(origin, destination);
//        int ride_id = this.rides_controller.start_ride(user);
        try {
            check_user_is_logged_in();
        }
        catch (UserException ex){
            // TODO: 04/01/2023 : handle exception - build response.
        }
        return null;

     }



    /**
     * don't check if the user is logged in - can perform without logged in.
     * @param user_id
     * @param origin
     * @param destination
     * @param city
     * @param start_time
     * @param end_time
     * @param hazards
     * @return
     */
     
    public Response finish_ride(int user_id, Location origin, Location destination, String city, LocalDateTime start_time,
                                LocalDateTime end_time, List<StationaryHazard> hazards) {

        Response response = null;
        try{
            String ride_info="";
            String hazard_info ="";
            // TODO: 05/01/2023 : build the info objects
            int number_of_rides = this.rides_controller.get_number_of_rides(user_id);
            Ride ride = this.rides_controller.add_ride(ride_info);
            int ride_id = ride.getRide_id();
            user_controller.update_user_rate(user_id, ride, number_of_rides);
            hazard_controller.update_hazards(hazard_info, ride_id);
            String logger_message = "user added new ride";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;

    }











    // ADMIN

    public Response view_all_open_questions() {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            List<String> questions = question_controller.get_all_open_questions();
            String logger_message = "admin view all user questions";
            response = new Response(questions, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response answer_user_question(int question_id, String answer) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            String admin_email = this.loggedUser.get_email();
            question_controller.answer_user_question(question_id, answer, admin_email);
            String logger_message = "admin answer question : " + question_id;
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response send_message_to_all_users(String message) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            String admin_email = this.loggedUser.get_email();
//            question_controller.send_message_to_all_users(message, admin_email); // TODO: 04/01/2023 : SASHA
            String logger_message = "admin send message to all the users ";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_rides() {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            List<Ride> rides = rides_controller.get_all_rides();
            String logger_message = "admin view all rides";
            response = new Response(rides, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_statistics() {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            List<String> stats = new LinkedList<>(); // TODO: 04/01/2023 : implement statistic module
            String logger_message = "admin view statistics";
            response = new Response(stats, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_advertisements() {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            List<String> advs = advertise_controller.get_all_advertisements_for_admin();
            String logger_message = "admin view all advertisements";
            response = new Response(advs, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response delete_advertisement(int advertise_id) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            advertise_controller.remove_advertise(advertise_id);
            String logger_message = "admin delete an advertisement with id : " + advertise_id;
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_awards() {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            List<String> awards = new LinkedList<>(); // TODO: 04/01/2023 : implement awards module
            String logger_message = "admin view all awards";
            response = new Response(awards, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response delete_award(int award_id) {
        return null;
    }

    public Response view_admins() {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            List<String> admins_list = user_controller.view_admins();
            String logger_message = "admin view all admins list";
            response = new Response(admins_list, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response add_admin(String user_email, String user_password) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            String admin_email = this.loggedUser.get_email();
            user_controller.appoint_new_admin(user_email, admin_email);
            // TODO: 04/01/2023 : have to register a new user, and not change the state of existing one.
            String logger_message = "admin (" + admin_email + ")appoint new admin (" + user_email+ ")";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;

    }

    public Response delete_admin(String user_email) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            String admin_email = this.loggedUser.get_email();
            user_controller.remove_admin_appointment(user_email, admin_email);
            // TODO: 04/01/2023 : have to register a new user, and not change the state of existing one.
            String logger_message = "admin (" + admin_email + ")remove appoint of admin (" + user_email + ")";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_users() {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            List<User> users_list = user_controller.get_all_users();
            String logger_message = "admin view all users list";
            response = new Response(users_list, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response delete_user(String user_email) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            String admin_email = this.loggedUser.get_email();
            user_controller.delete_user(user_email);
            String logger_message = "admin (" + admin_email + ") delete the user (" + user_email + ")";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in();
            this.advertise_controller.add_advertise(final_date, owner, message, photo, url);
            String logger_message = "admin add new advertise (" + owner+ ")";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
}