package gotcha.server.Service;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAPI {

    // SMARTPHONE API

    Response register(String email, String password, String name, String last_name, String birth_date, String phone_number, String gender);
    Response login(String email, String password);
    Response logout();
    Response change_password(String old_password, String password);

    Response view_user_rides_history();

    Response add_user_question(String message, User sender);
    Response view_all_user_questions(String user_email);

    // RP API - ?

    Response start_ride(Location origin, Location destination, User user);
    Response finish_ride(Location origin, Location destination, String city, LocalDateTime start_time,
                         LocalDateTime end_time, List<StationaryHazard> hazards);
}
