package gotcha.server.Service.API;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

public interface UserAPI {


    Response login(LoginRequest loginRequest, HttpSession session);
    Response logout(HttpSession session);
    Response register(RegisterRequest registerRequest);

    // MOBILE API
    Response view_notifications(@SessionAttribute("userContext") UserContext userContext);
    Response change_password(String old_password, String password, @SessionAttribute("userContext") UserContext userContext);
    Response view_user_rides_history(@SessionAttribute("userContext") UserContext userContext);
    Response add_user_question(String message, @SessionAttribute("userContext") UserContext userContext);
    Response view_all_user_questions(@SessionAttribute("userContext") UserContext userContext);
    Response get_safe_routes(Location origin, Location destination, @SessionAttribute("userContext") UserContext userContext);
    Response view_all_advertisement(@SessionAttribute("userContext") UserContext userContext);

    // RP API - this methods should not check if the user is logged in.

    Response finish_ride(String userEmail, Location origin, Location destination, String city, LocalDateTime start_time,
                         LocalDateTime end_time, List<StationaryHazard> hazards);

    /* maybe extend to get as input all the history rides from rp who doesn't send to the server. */
    Response get_rp_config_file();
}
