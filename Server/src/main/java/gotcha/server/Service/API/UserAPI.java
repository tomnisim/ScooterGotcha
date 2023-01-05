package gotcha.server.Service.API;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAPI {


    Response login(String email, String password);
    Response logout(int user_id);
    Response register(String email, String password, String name, String last_name, String birth_date, String phone_number, String gender);

    // SMARTPHONE API

    Response change_password(String old_password, String password, int user_id);
    Response view_user_rides_history(int user_id);
    Response add_user_question(String message, int user_id);
    Response view_all_user_questions(int user_id);
    Response get_safe_routes(Location origin, Location destination, int user_id);
    Response view_all_advertisement(int user_id);

    // RP API - this methods should not check if the user is logged in.

    Response finish_ride(int user_id, Location origin, Location destination, String city, LocalDateTime start_time,
                         LocalDateTime end_time, List<StationaryHazard> hazards);

    /* maybe extend to get as input all the history rides from rp who doesn't send to the server. */
    Response get_rp_config_file();
}
