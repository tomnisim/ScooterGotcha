package gotcha.server.Service;

import gotcha.server.Config.Configuration;
import gotcha.server.Domain.AdvertiseModule.Advertise;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.QuestionsModule.Question;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.StatisticsModule.Statistic;
import gotcha.server.Domain.StatisticsModule.StatisticsManager;
import gotcha.server.Domain.StatisticsModule.iStatisticsManager;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.ExternalService.MapsAdapterImpl;
import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.SafeRouteCalculatorModule.RoutesRetriever;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.Utils.Exceptions.UserExceptions.UserException;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import gotcha.server.Utils.Response;
import gotcha.server.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class Facade {
    private MapsAdapter mapsAdapter;
    private ErrorLogger error_logger;
    private ServerLogger serverLogger;
    private QuestionController question_controller;
    private RidesController rides_controller;
    private UserController user_controller;
    private AdvertiseController advertise_controller;
    private HazardController hazard_controller;
    private StatisticsManager statisticsManager;
    private User loggedUser;
    private RoutesRetriever routes_retriever;
   
    @Autowired
    public Facade(UserController userController, HazardController hazardController, AdvertiseController advertiseController, RidesController ridesController, QuestionController questionController, ServerLogger serverLogger, ErrorLogger errorLogger, StatisticsManager statisticsManager, Configuration config) {
        this.error_logger = errorLogger;
        this.serverLogger = serverLogger;
        this.question_controller = questionController;
        this.rides_controller = ridesController;
        this.user_controller = userController;
        this.advertise_controller = advertiseController;
        this.hazard_controller = hazardController;
        this.statisticsManager = statisticsManager;
		// todo : maybe move to the input
		MapsAdapter mapsAdapter = new MapsAdapterImpl();
        this.routes_retriever = new RoutesRetriever(mapsAdapter, hazardController, config);
    }



    private void check_user_is_logged_in(UserContext userContext) throws UserException {
        if (!userContext.isLoggedIn())
            throw new UserException("user is not logged in");
    }


    private void check_user_is_admin_and_logged_in(UserContext userContext) throws UserException {
        check_user_is_logged_in(userContext);
        if (!userContext.isAdmin()){
            throw new UserException("user is not an admin");
        }
    }

    // PROGRAMMER
     
    public Response set_server_config(UserContext userContext) {
        return null;
    }

     
    public Response set_rp_config(UserContext userContext) {
        return null;
    }

     
    public Response view_error_logger(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String logger = error_logger.toString();
            String logger_message = "user( "+loggedUser.get_email()+ ") view error logger";
            response = new Response(logger, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

     
    public Response view_system_logger(UserContext userContext) {
        return null;
    }

     
    public Response view_server_logger(UserContext userContext) {
        return null;
    }

     
    public Response reset(UserContext userContext) {
        try{
            check_user_is_admin_and_logged_in(userContext);
            clear();

        }
        catch (Exception e){

        }
        return null;
    }

     
    public Response shut_down(UserContext userContext) {
        try{
            check_user_is_admin_and_logged_in(userContext);
            System.exit(800);

        }
        catch (Exception e){

        }
        return null;
    }

     
//    public Response update_user_rate_tables(Dictionary<String, Dictionary<Integer, Integer>> tables) {
//        return null;
//    }
//
//
//    public Response update_hazard_formula(HazardType type, Formula formula) {
//        return null;
//    }

    // USER


    public Response register(String email, String password, String name, String last_name, String birth_date, String phone_number, String gender) {
        Response response = null;
        LocalDate birth_day = LocalDate.of(1995, 4,19) ,licence_date = LocalDate.now(); // TODO: 01/03/2023 : build params
        String scooter_type="amit", rp_serial_number = "amit"; // TODO: 01/03/2023
        try {
            user_controller.register(email, password, phone_number, birth_day, gender, scooter_type, licence_date, rp_serial_number); // TODO: 01/03/2023 : add name & last name
            String logger_message = "User's (" + email + ")  has been registered successfully.";
            response = new Response<>(password, logger_message);
            serverLogger.add_log(logger_message);

            this.statisticsManager.inc_register_count();
            // TODO: 01/03/2023 : update logged user?
        }

        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response<User> login(String email, String password) {
        Response<User> response = null;
        try {
            var user = user_controller.login(email,password);
            var message = String.format("User with email %s Successfully logged in", email);
            response = new Response<>(user,message);
			serverLogger.add_log(message);

        }
        catch (Exception e) {
            response = new Response<>(e.getMessage(), e);
        }
        return response;
    }
	
    public Response logout() {
        Response response = null;
        try {
            String email = this.loggedUser.get_email();
            user_controller.logout(email);
            String logger_message = "User's (" + email + ")  has been logout successfully.";
            response = new Response<>("", logger_message);
            serverLogger.add_log(logger_message);
            this.loggedUser = null;
            this.statisticsManager.inc_logout_count();

        }

        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

     
    public Response change_password(String old_password, String password, UserContext userContext) {
        Response response = null;
        try {
            check_user_is_logged_in(userContext);
            user_controller.change_password(loggedUser.get_email(), old_password, password);
            String logger_message = "User's (" + loggedUser.get_email() + ")  password has been changed successfully.";
            response = new Response<>(password, logger_message);
            serverLogger.add_log(logger_message);
        }

        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
    public Response view_user_rides_history(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_logged_in(userContext);
            String user_email = loggedUser.get_email();
            int user_id = 0; // TODO: 04/01/2023 : get id from logged user -> email not id!!
            List<Ride> rides = this.rides_controller.get_rides_by_email(user_email);
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
    public Response add_user_question(String message, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_logged_in(userContext);
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
    public Response view_all_user_questions(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_logged_in(userContext);
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
    public Response view_all_advertisements(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_logged_in(userContext);
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



    public Response get_safe_routes(Location origin, Location destination, UserContext userContext) {
        Response response = null;
        // TODO: 03/03/2023 : Tom - have to get 3 routes from Google Maps, find all the hazards in each route,
        //  sum the rating of each hazard by hazard rate calculator
        try {
            check_user_is_logged_in(userContext);
            List<Route> routeList = this.routes_retriever.fetch_safe_routes(origin, destination);
            // int ride_id = this.rides_controller.start_ride(user);
            String logger_message = "user( "+loggedUser.get_email()+ ") got routes from: " + origin.toString() + ", to: "+destination.toString();
            response = new Response(routeList, logger_message);
            serverLogger.add_log(logger_message);
        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;

     }

    public Response view_notifications(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_logged_in(userContext);
            Collection<Notification> notifications = loggedUser.get_notifications();
            String logger_message = "user( "+loggedUser.get_email()+ ") view his notifications";
            response = new Response(notifications, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
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
     // todo : user id -> RP serial number 
    public Response finish_ride(String user_id, Location origin, Location destination, String city, LocalDateTime start_time,
                                LocalDateTime end_time, List<StationaryHazard> hazards) {

        Response response = null;
        try{
            String ride_info="";
            String hazard_info ="";
            // TODO: 05/01/2023 : build the info objects
            // TODO: 04/03/2023 : change user id -> email or serial number ?
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

    public Response view_all_open_questions(UserContext userContext) {
        Response response = null;
        try{

            check_user_is_admin_and_logged_in(userContext);
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

    public Response answer_user_question(int question_id, String answer, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
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

    public Response send_message_to_all_users(String message, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = this.loggedUser.get_email();
            user_controller.notify_all_users(admin_email, message);
            String logger_message = "admin send message to all users ";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_rides(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
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

    public Response view_statistics(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
            List<String> stats = new LinkedList<>(); // TODO: 04/01/2023 : implement statistic module
            Statistic statistics = this.statisticsManager.get_system_statistics(user_controller.get_all_users());
            String logger_message = "admin view statistics";
            response = new Response(statistics, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_advertisements(UserContext userContext) {
        Response response = null;
        try{

            check_user_is_admin_and_logged_in(userContext);
            List<Advertise> advs = advertise_controller.get_all_advertisements_for_admin();

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

    public Response delete_advertisement(int advertise_id, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
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

    public Response view_awards(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
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


// todo : add user context
    public Response add_award(String[] emails, String award, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = this.loggedUser.get_email();
            user_controller.notify_users(admin_email, emails, award);
            String logger_message = "admin add awards (" + award + ") to users: "+emails;
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }





    public Response view_admins(UserContext userContext) {
        Response response = null;
        try{

            check_user_is_admin_and_logged_in(userContext);
            List<Admin> admins_list = user_controller.view_admins();
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

    public Response add_admin(String user_email, String user_password, String phoneNumber, LocalDate birthDay, String gender, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = this.loggedUser.get_email();
            user_controller.appoint_new_admin(user_email, user_password, phoneNumber, birthDay, gender, admin_email);
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

    public Response delete_admin(String user_email, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
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

    public Response view_users(UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
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

    public Response delete_user(String user_email, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
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

    public Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url, UserContext userContext) {
        Response response = null;
        try{
            check_user_is_admin_and_logged_in(userContext);
            Advertise advertise = this.advertise_controller.add_advertise(final_date, owner, message, photo, url);
            String logger_message = "admin add new advertise (" + owner+ ")";
            response = new Response(advertise.getId(), logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public void clear() {
        // TODO: 01/03/2023 : clear all the data in instances. 
//        this.user_controller.reset();
//        this.hazard_controller.reset();
//        this.rides_controller.reset();
//        this.advertise_controller.reset();
//        this.question_controller.reset();
    }


}