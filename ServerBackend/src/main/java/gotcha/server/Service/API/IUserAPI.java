package gotcha.server.Service.API;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Service.Communication.Requests.*;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Response;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

public interface IUserAPI {


    Response login(LoginRequest loginRequest, HttpSession session);
    Response logout(HttpSession session, UserContext userContext);
    Response register(RegisterRequest registerRequest);

    Response reset_password(String userEmail);

    Response change_password(ChangePasswordRequest changePasswordRequest, @SessionAttribute("userContext") UserContext userContext);

    // MOBILE API
    Response view_notifications(@SessionAttribute("userContext") UserContext userContext);
    Response view_user_rides_history(@SessionAttribute("userContext") UserContext userContext);
    Response add_user_question(String message, @SessionAttribute("userContext") UserContext userContext);
    Response view_all_user_questions(@SessionAttribute("userContext") UserContext userContext);
    Response get_safe_routes(String origin, String destination, @SessionAttribute("userContext") UserContext userContext);
    Response view_all_advertisement(@SessionAttribute("userContext") UserContext userContext);
    Response add_advertisement_click(Integer id, @SessionAttribute("userContext") UserContext userContext);
    // RP API - this methods should not check if the user is logged in.

    Response finish_ride(FinishRideRequest finishRideRequest);

    /* maybe extend to get as input all the history rides from rp who doesn't send to the server. */
    Response get_rp_config_file();

    Response update_information(UpdateInformationRequest updateInformationRequest);
}
