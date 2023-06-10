package gotcha.server.Service.API;

import gotcha.server.Domain.UserModule.RiderDTO;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Service.Communication.Requests.*;
import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Service.Communication.Requests.AddAdvertisementRequest;
import gotcha.server.Service.Communication.Requests.AddAwardRequest;
import gotcha.server.Service.Communication.Requests.LoginRequest;
import gotcha.server.Service.Communication.Requests.RegisterRequest;
import gotcha.server.Service.Facade;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;
import org.apache.commons.codec.binary.Base64;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@RestController
@CrossOrigin
public class ApiController implements IAdminAPI, IUserAPI {
    private final Facade facade;
    private final String USER_CONTEXT_ATTRIBUTE_NAME = "userContext";

    @Autowired
    public ApiController(Facade facade){
        this.facade = facade;

    }

    @RequestMapping(value = "/test")
    public void test() {
        System.out.println("Test");

        this.facade.clear();
    }

    /**
     * this method will create a new facade for the connection.
     * @param loginRequest
     * @param session
     * @return
     */
    @RequestMapping(value = "/login")
    @Override
    public Response login(@RequestBody LoginRequest loginRequest, HttpSession session){
        System.out.println("login");
        Response<User> response = facade.login(loginRequest);
        if(!response.iswas_exception()) {
            var userContext = new UserContext(response.getValue());
            session.setAttribute(USER_CONTEXT_ATTRIBUTE_NAME, userContext);
        }
        return response;
    }

    @RequestMapping(value = "/rider_login")
    public Response rider_login(@RequestBody LoginRequest loginRequest, HttpSession session){
        Response<User> response = facade.login(loginRequest);
        Response<RiderDTO> response1 = facade.rider_login(loginRequest);
        if(!response.iswas_exception()) {
            var userContext = new UserContext(response.getValue());
            session.setAttribute(USER_CONTEXT_ATTRIBUTE_NAME, userContext);
        }
        return response1;
    }

    /**
     * this method will clear the facade.
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout")
    @Override
    public Response logout(HttpSession session, @SessionAttribute("userContext") UserContext userContext) {
        Response response = facade.logout(userContext);
        if (!response.iswas_exception())
            session.removeAttribute(USER_CONTEXT_ATTRIBUTE_NAME);
        return response;
    }

    @RequestMapping(value = "/register")
    @Override
    public Response<Boolean> register(@RequestBody RegisterRequest registerRequest) {
        var response = facade.register(registerRequest);
        return response;
    }

    @RequestMapping(value = "/reset_password")
    @Override
    public Response reset_password(String userEmail) {
        var response = facade.reset_password(userEmail);
        return response;
    }

    @RequestMapping(value = "/change_password")
    @Override
    public Response change_password(ChangePasswordRequest changePasswordRequest, @SessionAttribute("userContext") UserContext userContext) {
        return facade.change_password(changePasswordRequest, userContext);
    }

    @RequestMapping(value = "/view_user_rides_history")
    public Response view_user_rides_history(@SessionAttribute("userContext") UserContext userContext){
        return facade.view_user_rides_history(userContext);
    }

    @RequestMapping(value = "/add_user_question")
    @Override
    public Response add_user_question(String message, @SessionAttribute("userContext") UserContext userContext) {
        return facade.add_user_question(message, userContext);
    }

    @RequestMapping(value = "/view_all_user_questions")
    @Override
    public Response view_all_user_questions(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_user_questions(userContext);
    }

    @RequestMapping(value = "/get_safe_routes")
    @Override
    public Response get_safe_routes(String origin, String destination, @SessionAttribute("userContext") UserContext userContext) {
        return facade.get_safe_routes(origin, destination, userContext);
    }

    @RequestMapping(value = "/view_all_advertisement")
    @Override
    public Response view_all_advertisement(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_advertisements(userContext);
    }

    @RequestMapping(value = "/add_adv_click")
    @Override
    public Response add_advertisement_click(Integer id, UserContext userContext) {
        return facade.add_advertisement_click(id, userContext);
    }

    @RequestMapping(value = "/view_notifications")
    @Override
    public Response view_notifications(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_notifications(userContext);
    }

    @RequestMapping(value = "/updated _information")
    @Override
    public Response<Boolean> update_information(@RequestBody UpdateInformationRequest updateInformationRequest){
        var response = facade.update_information(updateInformationRequest);
        return response;
    }


    /* the methods for RP */

    /**
     * this method is for RP usage, when user is not have to be logged in.
     * @param finishRideRequest
     * @return
     */
    @RequestMapping(value = "/finish_ride")
    @Override
    public Response finish_ride(@RequestBody FinishRideRequest finishRideRequest) {
        Response response = facade.finish_ride(finishRideRequest);
        return response;
    }
    @RequestMapping(value = "/send_ride_test")
    public void send_ride_test(@RequestBody test t) throws IOException {
        int a = 4;
        byte[] byteArrayImage = t.getData(); // Replace with your method to get the Base64-encoded string
        int b=byteArrayImage.length;
// Decode the Base64 string to a byte array
//        // Specify the file path and name to save the image
        String filePath = "image_test2.jpg";
//
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // Save the byte array as an image file
            fos.write(byteArrayImage);
            System.out.println("Image saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this method is for RP usage, when user is not have to be logged in.
     * this method will create a new facade for rp connection and remove it after finish in case of dismiss connection,
     * and will use an existing facade and not remove him else.
     * @return
     */
    @RequestMapping(value = "/get_rp_config_file")
    @Override
    public Response get_rp_config_file() {
        return facade.get_rp_config();
    }

    // ADMIN METHODS

    @RequestMapping(value = "/view_all_open_questions")
    @Override
    public Response view_all_open_questions(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_open_questions(userContext);
    }

    @RequestMapping(value = "/answer_user_question")
    @Override
    public Response answer_user_question(int question_id, String answer, @SessionAttribute("userContext") UserContext userContext) {
        return facade.answer_user_question(question_id, answer, userContext);
    }

    @RequestMapping(value = "/send_message_to_all_users") @Override
    public Response send_message_to_all_users(String message, @SessionAttribute("userContext") UserContext userContext) {
        return facade.send_message_to_all_users(message, userContext);
    }

    @RequestMapping(value = "/view_rides")
    @Override
    public Response view_rides(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_rides(userContext);

    }

    @RequestMapping(value = "/view_daily_statistics")
    @Override
    public Response view_daily_statistics(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_daily_statistics(userContext);
    }

    @RequestMapping(value = "/view_general_statistics")
    @Override
    public Response view_general_statistics(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_general_statistics(userContext);
    }

    @RequestMapping(value = "/view_all_daily_statistics")
    @Override
    public Response view_all_daily_statistics(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_daily_statistics(userContext);
    }

    @RequestMapping(value = "/view_advertisements")
    @Override
    public Response view_advertisements(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_advertisements(userContext);
    }

    @RequestMapping(value = "/add_advertisement")
    @Override
    public Response add_advertisement(@RequestBody AddAdvertisementRequest addAdvertisementRequest, @SessionAttribute("userContext") UserContext userContext) {
        String url = addAdvertisementRequest.getUrl();
        String owner = addAdvertisementRequest.getOwner();
        String photo = addAdvertisementRequest.getPhoto();
        String message = addAdvertisementRequest.getMessage();
        LocalDate final_date = addAdvertisementRequest.getFinal_date();
        return facade.add_advertisement(final_date, owner, message, photo, url, userContext);
    }

    @RequestMapping(value = "/delete_advertisement")
    @Override
    public Response delete_advertisement(int advertise_id, @SessionAttribute("userContext") UserContext userContext) {
        return facade.delete_advertisement(advertise_id, userContext);
    }

    @RequestMapping(value = "/view_awards")
    @Override
    public Response view_awards(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_awards(userContext);
    }

    @RequestMapping(value = "/add_award")
    @Override
    public Response add_award(@RequestBody AddAwardRequest addAwardRequest, @SessionAttribute("userContext") UserContext userContext) {
        List<String> emails = addAwardRequest.getEmails();
        String award = addAwardRequest.getAward();
        return facade.add_award(emails,award, userContext);
    }

    @RequestMapping(value = "/view_admins")
    @Override
    public Response view_admins(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_admins(userContext);
    }

    @RequestMapping(value = "/add_admin")
    @Override
    public Response add_admin(String user_email,String name, String lastName, String user_password, String phoneNumber, String birthDay, String gender, @SessionAttribute("userContext") UserContext userContext) {
        return facade.add_admin(user_email,name, lastName, user_password, phoneNumber, birthDay, gender, userContext);
    }

    @RequestMapping(value = "/delete_admin")
    @Override
    public Response delete_admin(String user_email, @SessionAttribute("userContext") UserContext userContext) {
        return facade.delete_admin(user_email, userContext);
    }

    @RequestMapping(value = "/view_users")
    @Override
    public Response view_users(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_users(userContext);
    }
    @RequestMapping(value = "/view_waiting_rp")
    @Override
    public Response view_waiting_rp(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_waiting_rp(userContext);
    }


    @RequestMapping(value = "/delete_user")
    @Override
    public Response delete_user(String user_email, @SessionAttribute("userContext") UserContext userContext) {
        return facade.delete_user(user_email, userContext);
    }
    @RequestMapping(value = "/add_rp_serial_number")
    @Override
    public Response add_rp_serial_number(String rp_serial, UserContext userContext) {
        return facade.add_rp_serial_number(rp_serial, userContext);
    }

    @RequestMapping(value = "/view_hazards")
    @Override
    public Response view_hazards(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_hazards(userContext);
    }

    @RequestMapping(value = "/add_hazard")
    @Override
    public Response add_hazard(String lng, String lat, String city, String type, Double size, UserContext userContext) {
        return facade.add_hazard(lng, lat, city, type, size, userContext);
    }


    // super admin API


    @RequestMapping(value = "/view_server_logger")
    @Override
    public Response view_server_logger(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_server_logger(userContext);
    }

    @RequestMapping(value = "/reset")
    @Override
    public Response reset(@SessionAttribute("userContext") UserContext userContext) {
        return facade.reset(userContext);
    }


    @RequestMapping(value = "/shut_down")
    @Override
    public Response shut_down(@SessionAttribute("userContext") UserContext userContext) {
        return facade.shut_down(userContext);
    }

    @RequestMapping(value = "/delete_hazard")
    @Override
    public Response delete_hazard(int hazard_id, UserContext userContext) {
        return this.facade.delete_hazard(hazard_id, userContext);
    }
    @RequestMapping(value = "/report_hazard")
    @Override
    public Response report_hazard(int hazard_id, UserContext userContext) {
        return this.facade.report_hazard(hazard_id, userContext);
    }

    @RequestMapping(value = "/get_hazards_in_city")
    @Override
    public Response get_hazards_in_city(String city, UserContext userContext) {
        return this.facade.get_hazards_in_city(city, userContext);
    }


    @RequestMapping(value = "/set_config")
    @Override
    public Response set_config(@RequestBody SetConfigRequest request, @SessionAttribute("userContext") UserContext userContext) {
        return facade.set_config(request, userContext);
    }

    @RequestMapping(value = "/get_config")
    @Override
    public Response get_config(@SessionAttribute("userContext") UserContext userContext) {
        return facade.get_config(userContext);
    }

    @RequestMapping(value = "/view_error_logger")
    @Override
    public Response view_error_logger(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_error_logger(userContext);
    }

    @RequestMapping(value = "/view_system_logger")
    @Override
    public Response view_system_logger(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_system_logger(userContext);
    }




}
