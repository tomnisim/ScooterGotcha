package gotcha.server.Service;

import gotcha.server.Config.Configuration;
import gotcha.server.Domain.AdvertiseModule.Advertise;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.AdvertiseModule.IAdvertiseController;
import gotcha.server.Domain.AwardsModule.Award;
import gotcha.server.Domain.AwardsModule.IAwardsController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.IHazardController;
import gotcha.server.Domain.HazardsModule.StationaryHazardDAO;
import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.QuestionsModule.IQuestionController;
import gotcha.server.Domain.QuestionsModule.Question;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.IRidesController;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.RidesModule.RideDTO;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.StatisticsModule.DailyStatisticDAO;
import gotcha.server.Domain.StatisticsModule.GeneralStatistic;
import gotcha.server.Domain.StatisticsModule.StatisticsManager;
import gotcha.server.Domain.UserModule.*;
import gotcha.server.Domain.SafeRouteCalculatorModule.Route;
import gotcha.server.Domain.SafeRouteCalculatorModule.RoutesRetriever;
import gotcha.server.Service.Communication.Requests.ChangePasswordRequest;
import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Service.Communication.Requests.LoginRequest;
import gotcha.server.Service.Communication.Requests.RegisterRequest;
import gotcha.server.Service.Communication.Requests.SetConfigRequest;
import gotcha.server.Utils.Exceptions.UserExceptions.UserException;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import gotcha.server.Utils.Logger.SystemLogger;
import gotcha.server.Utils.Response;
import gotcha.server.Utils.Threads.SendEmailThread;
import gotcha.server.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Component
public class Facade {
    final int SUCCESS_OPCODE = 400;
    private ErrorLogger error_logger;
    private ServerLogger serverLogger;
    private SystemLogger systemLogger;
    private IQuestionController question_controller;
    private IRidesController rides_controller;
    private IUserController user_controller;
    private IAdvertiseController advertise_controller;
    private IAwardsController awards_controller;
    private IHazardController hazard_controller;
    private StatisticsManager statisticsManager;
    private RoutesRetriever routes_retriever;
    private Configuration configuration;
   
    @Autowired
    public Facade(UserController userController, HazardController hazardController, AdvertiseController advertiseController
            ,IAwardsController awards_controller, RidesController ridesController, QuestionController questionController,
                  ServerLogger serverLogger, ErrorLogger errorLogger, SystemLogger systemLogger, StatisticsManager statisticsManager, Configuration config, RoutesRetriever routesRetriever) {
        this.error_logger = errorLogger;
        this.serverLogger = serverLogger;
        this.systemLogger = systemLogger;
        this.question_controller = questionController;
        this.rides_controller = ridesController;
        this.user_controller = userController;
        this.advertise_controller = advertiseController;
        this.awards_controller = awards_controller;
        this.hazard_controller = hazardController;
        this.statisticsManager = statisticsManager;
        //        this.statisticsManager = new StatisticsManager(userController, hazardController, advertiseController, awards_controller,ridesController, questionController);
        this.routes_retriever = routesRetriever;
        this.configuration = config;
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

    // USER

    public Response<Boolean> register(RegisterRequest registerRequest) {
        Response<Boolean> response = null;
        try {
            var email = registerRequest.getEmail();
            var password = registerRequest.getPassword();
            var gender = registerRequest.getGender();
            var birthDate = registerRequest.getBirthDate();
            var name = registerRequest.getName();
            var lastName = registerRequest.getLastName();
            var phone = registerRequest.getPhoneNumber();
            var rpSerialNumber = registerRequest.getRaspberrySerialNumber();
            var scooterType = registerRequest.getScooterType();
            var licenseIssueDate = registerRequest.getLicenseIssueDate();
            user_controller.register(email,password,name, lastName,phone,birthDate,gender,scooterType,licenseIssueDate,rpSerialNumber);
            var message = "Successfully registered user with email" + email;
            serverLogger.add_log(message);
            response = new Response(true,message);
        }
        catch (Exception e) {
            error_logger.add_log(e.getMessage());
            response = new Response(e.getMessage(), e);
        }
        return response;
    }
    public Response<User> login(LoginRequest loginRequest) {
        Response<User> response = null;
        try {
            var email = loginRequest.getEmail();
            var password = loginRequest.getPassword();
            var user = user_controller.login(email,password);
            var message = String.format("User with email %s Successfully logged in", email);
            serverLogger.add_log(message);
            response = new Response<>(user,message);
            serverLogger.add_log(message);

        }
        catch (Exception e) {
            error_logger.add_log(e.getMessage());
            response = new Response<>(e.getMessage(), e);
        }
        finally {
            this.statisticsManager.inc_login_count();
        }
        return response;
    }

    public Response logout(UserContext userContext) {
        Response response;
        try {
            String email = userContext.get_email();
            user_controller.logout(email);
            String logger_message = "User's (" + email + ")  has been logout successfully.";
            response = new Response<>("", logger_message);
            serverLogger.add_log(logger_message);
            this.statisticsManager.inc_logout_count();

        }

        catch (Exception e){
            response = new Response<>(e.getMessage(),e);
            error_logger.add_log(e.getMessage());
        }
        finally {
            this.statisticsManager.inc_logout_count();
        }
        return response;
    }

     
    public Response change_password(ChangePasswordRequest changePasswordRequest, UserContext userContext) {
        Response response;
        try {
            check_user_is_logged_in(userContext);
            user_controller.change_password(userContext.get_email(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            String logger_message = "User's (" + userContext.get_email() + ")  password has been changed successfully.";
            response = new Response<>("", logger_message);
            serverLogger.add_log(logger_message);
        }

        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
    public Response view_user_rides_history(UserContext userContext) {
        Response response;
        try{
            check_user_is_logged_in(userContext);
            String user_email = userContext.get_email();
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
        Response response;
        try{
            check_user_is_logged_in(userContext);
            String user_email = userContext.get_email();
            user_controller.send_question_to_admin(user_email, message);
            String logger_message = user_email + " add user question";
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
        Response response;
        try{
            check_user_is_logged_in(userContext);
            String user_email = userContext.get_email();
            List<Question> questions = question_controller.get_all_user_questions(user_email);
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
        Response response;
        try{
            check_user_is_logged_in(userContext);
            List<String> advs = advertise_controller.get_all_advertisements_for_user();
            String logger_message = "user( "+userContext.get_email()+ ") view all advertisements";
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



    public Response get_safe_routes(String origin, String destination, UserContext userContext) {
        Response response;
        // TODO: 03/03/2023 : Tom - have to get 3 routes from Google Maps, find all the hazards in each route,
        //  sum the rating of each hazard by hazard rate calculator
        try {
            check_user_is_logged_in(userContext);
            List<Route> routeList = this.routes_retriever.fetch_safe_routes(origin, destination);
            String logger_message = "user( "+userContext.get_email()+ ") got routes from: " + origin.toString() + ", to: "+destination.toString();
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
        Response response;
        try{
            check_user_is_logged_in(userContext);
            Collection<Notification> notifications = userContext.get_notifications();
            String logger_message = "user( "+userContext.get_email()+ ") view his notifications";
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
     * @param finishRideRequest
     * @return
     */
    public Response finish_ride(FinishRideRequest finishRideRequest) {
        Response response;
        try{
            String userEmail = user_controller.get_user_email_by_rp_serial(finishRideRequest.getRpSerialNumber());
            int number_of_rides = this.rides_controller.get_number_of_rides(userEmail);
            Ride ride = this.rides_controller.add_ride(finishRideRequest, userEmail);
            int ride_id = ride.getRide_id();
            user_controller.update_user_rate(userEmail, ride, number_of_rides);
            hazard_controller.update_hazards(finishRideRequest.getHazards(), ride_id);
            String logger_message = "user added new ride";
            response = new Response(logger_message, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response<String> reset_password(String userEmail) {
        Response<String> response;
        try {
            String newPassword = user_controller.resetPassword(userEmail);
            var emailMessage = "You are receiving this since you asked to reset your password.\n your new password is:" + newPassword;
            // TODO: 4/18/2023 : Change adminEmail and password to be taken from configuration file
            var sendEmailThread = new SendEmailThread("adminEmail", "adminEmailPassword", userEmail, emailMessage);
            sendEmailThread.run();
            var loggerMessage = "Successfully changed password of user with email: "+ userEmail;
            response = new Response<>(newPassword, loggerMessage);
            serverLogger.add_log(loggerMessage);
        }
        catch (Exception e) {
            error_logger.add_log(e.getMessage());
            response = Utils.createResponse(e);
        }
        return response;
    }









    // ADMIN

    public Response view_all_open_questions(UserContext userContext) {
        Response response;
        try{

            check_user_is_admin_and_logged_in(userContext);
            List<Question> questions = question_controller.get_all_open_questions();
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
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
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
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
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
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            List<RideDTO> rides = rides_controller.get_all_rides();
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

    public Response view_daily_statistics(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            DailyStatisticDAO daily_statistic = this.statisticsManager.get_current_daily_statistic();
            String logger_message = "admin view current daily statistics";
            response = new Response(daily_statistic, logger_message);
            serverLogger.add_log(logger_message);
        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_general_statistics(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            GeneralStatistic generalStatistic = this.statisticsManager.get_general_statistic();
            String logger_message = "admin view current general statistics";
            response = new Response(generalStatistic, logger_message);
            serverLogger.add_log(logger_message);
        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }


    public Response view_all_daily_statistics(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            List<DailyStatisticDAO> all_daily_statistic = this.statisticsManager.get_all_daily_statistic();
            String logger_message = "admin view all daily statistics";
            response = new Response(all_daily_statistic, logger_message);
            serverLogger.add_log(logger_message);
        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response view_advertisements(UserContext userContext) {
        Response response;
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
        Response response;
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

    public Response view_hazards(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            Collection<StationaryHazardDAO> hazards = this.hazard_controller.view_hazards();
            String logger_message = "admin("+admin_email+") view all hazards";
            response = new Response(hazards, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response get_hazards_in_city(String city, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            Collection<StationaryHazardDAO> hazards = this.hazard_controller.get_hazards(city);
            String logger_message = String.format("admin(%s) view all hazards in city: %s", admin_email, city);
            response = new Response(hazards, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response delete_hazard(int hazardId, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            hazard_controller.remove_hazard(hazardId);
            String logger_message = String.format("admin (%s) delete hazard with id : %s", admin_email, hazardId);
            response = new Response(SUCCESS_OPCODE, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response report_hazard(int hazardId, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            hazard_controller.report_hazard(hazardId);
            String logger_message = String.format("admin (%s) report hazard with id : %s", admin_email, hazardId);
            response = new Response(SUCCESS_OPCODE, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }


    public Response view_awards(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            Collection<Award> awards = this.awards_controller.view_awards();
            String logger_message = "admin("+admin_email+") view all awards";
            response = new Response(awards, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }


    public Response add_award(List<String> emails, String award, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            this.awards_controller.add_award(award, admin_email, emails);
            user_controller.notify_users(admin_email, emails, award);
            String logger_message = String.format("admin(%s) add awards (%s) to users: %s", admin_email, award, emails.toString());
            response = new Response(SUCCESS_OPCODE, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }





    public Response view_admins(UserContext userContext) {
        Response response;
        try{

            check_user_is_admin_and_logged_in(userContext);
            List<AdminDAO> admins_list = user_controller.view_admins();
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

    public Response add_admin(String user_email, String name, String lastName, String user_password, String phoneNumber, String birthDay, String gender, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birth_date = LocalDate.parse(birthDay, formatter);
            user_controller.appoint_new_admin(user_email,name, lastName, user_password, phoneNumber, birth_date, gender, admin_email);
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

    public Response add_hazard(String lng, String lat, String city, String type, Double size, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            Location location = new Location(lng, lat);
            HazardType hazard_type = HazardType.valueOf(type);
            double hazard_size = size.doubleValue();
            hazard_controller.add_hazard(-1,location, city, hazard_type, hazard_size);
            String logger_message = String.format("admin (%s) add new hazard: (%s, %s, %f)", admin_email, location, type, size);
            response = new Response(SUCCESS_OPCODE, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response delete_admin(String user_email, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            user_controller.remove_admin_appointment(user_email, admin_email);
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
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            List<RiderDAO> users_list = user_controller.get_all_riders();
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

    public Response view_waiting_rp(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            List<WaitingRaspberryPiDAO> waiting_rp_list = user_controller.get_waiting_rp();
            String logger_message = String.format("admin (%s), view all waiting Raspberry Pi list",userContext.get_email());
            response = new Response(waiting_rp_list, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }
    public Response add_rp_serial_number(String rpSerial, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            user_controller.add_rp_serial_number(rpSerial);
            String logger_message = String.format("Admin: %s, add new Raspberry Pi: %s to system.", userContext.get_email(),rpSerial);
            response = new Response(SUCCESS_OPCODE, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response delete_user(String user_email, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
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

    public Response add_advertisement(LocalDate final_date, String owner, String message, String photo, String url, UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            Advertise advertise = this.advertise_controller.add_advertise(final_date, owner, message, photo, url);
            String logger_message = "admin("+admin_email+"" +") add new advertise (" + owner+ ")";
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
        this.error_logger.add_log("clear");
        this.serverLogger.add_log("claer");
        this.systemLogger.add_log("clear");

        // TODO: 01/03/2023 : clear all the data in instances.
//        this.user_controller.reset();
//        this.hazard_controller.reset();
//        this.rides_controller.reset();
//        this.advertise_controller.reset();
//        this.question_controller.reset();
    }


    // PROGRAMMER



    public Response reset(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            clear();
            String logger_message = "admin ( "+admin_email+ ") reset the system";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);
        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        finally {
            this.statisticsManager.inc_reset_count();
        }
        return response;
    }


    public Response shut_down(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            System.exit(800);
            String logger_message = "admin ( "+admin_email+ ") sut down the system";
            response = new Response("", logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        finally {
            this.statisticsManager.inc_shut_down_count();
        }
        return response;
    }


    public Response view_error_logger(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            String logger = error_logger.toString();
            String logger_message = "admin( "+admin_email+ ") view error logger";
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
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            String logger = systemLogger.toString();
            String logger_message = "admin( "+admin_email+ ") view system logger";
            response = new Response(logger, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }


    public Response view_server_logger(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            String logger = serverLogger.toString();
            String logger_message = "admin( "+admin_email+ ") view server logger";
            response = new Response(logger, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }


    public Response get_config(UserContext userContext) {
        Response response;
        try{
            check_user_is_admin_and_logged_in(userContext);
            String admin_email = userContext.get_email();
            String logger_message = String.format("admin(%s) view system configuration.",admin_email);
            response = new Response(configuration, logger_message);
            serverLogger.add_log(logger_message);

        }
        catch (Exception e){
            response = Utils.createResponse(e);
            error_logger.add_log(e.getMessage());
        }
        return response;
    }

    public Response set_config(SetConfigRequest request, UserContext userContext) {
        // TODO: 12/04/2023  
        return null;
    }
}
