package gotcha.server.Service.API;

import gotcha.server.Service.Communication.Requests.AddAdvertisementRequest;
import gotcha.server.Service.Communication.Requests.AddAwardRequest;
import gotcha.server.Service.Communication.Requests.SetConfigRequest;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Response;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAdminAPI {

    //  Questions
    Response view_all_open_questions(@SessionAttribute("userContext") UserContext userContext);
    Response answer_user_question(int question_id, String answer, @SessionAttribute("userContext") UserContext userContext);
    Response send_message_to_all_users(String message, @SessionAttribute("userContext") UserContext userContext);

    // Rides
    Response view_rides(@SessionAttribute("userContext") UserContext userContext);

    // Statistics
    Response view_daily_statistics(@SessionAttribute("userContext") UserContext userContext);
    Response view_general_statistics(@SessionAttribute("userContext") UserContext userContext);
    Response view_all_daily_statistics(@SessionAttribute("userContext") UserContext userContext);

    // Advertisements
    Response view_advertisements(@SessionAttribute("userContext") UserContext userContext);
    Response add_advertisement(AddAdvertisementRequest addAdvertisementRequest, @SessionAttribute("userContext") UserContext userContext);
    Response delete_advertisement(int advertise_id,@SessionAttribute("userContext") UserContext userContext);

    // Awards
    Response view_awards(@SessionAttribute("userContext") UserContext userContext);
    Response add_award(AddAwardRequest addAwardRequest, @SessionAttribute("userContext") UserContext userContext);

    // Admins
    Response view_admins( @SessionAttribute("userContext") UserContext userContext);
    Response add_admin(String user_email,String name, String lastName, String user_password, String phoneNumber, String birthDay, String gender, @SessionAttribute("userContext") UserContext userContext);
    Response delete_admin(String user_email, @SessionAttribute("userContext") UserContext userContext);

    // Users
    Response view_users(@SessionAttribute("userContext") UserContext userContext);
    Response view_waiting_rp(@SessionAttribute("userContext") UserContext userContext);
    Response delete_user(String user_email, @SessionAttribute("userContext") UserContext userContext);
    Response add_rp_serial_number(String rp_serial, @SessionAttribute("userContext") UserContext userContext);


    // Hazards
    Response view_hazards( @SessionAttribute("userContext") UserContext userContext);
    Response add_hazard(String lng, String lat, String city, String type, Double size, @SessionAttribute("userContext") UserContext userContext);
    Response delete_hazard(int hazard_id,@SessionAttribute("userContext") UserContext userContext);
    Response report_hazard(int hazard_id,@SessionAttribute("userContext") UserContext userContext);

    Response get_hazards_in_city(String city,@SessionAttribute("userContext") UserContext userContext);
    // SUPER admin

    Response get_config(UserContext userContext);
    Response set_config(SetConfigRequest request, UserContext userContext);

    Response view_error_logger(UserContext userContext);
    Response view_system_logger(UserContext userContext);
    Response view_server_logger(UserContext userContext);

    Response reset(UserContext userContext);
    Response shut_down(UserContext userContext);

//    Response update_user_rate_tables(Dictionary<String, Dictionary<Integer, Integer>> tables, UserContext userContext);
//    Response update_hazard_formula(HazardType type, Formula formula, UserContext userContext);



}
