package gotcha.server.Service;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;

public interface UserAPI {

    // SMARTPHONE API

    Response register();
    Response login();
    Response logout();
    Response change_password();

    Response view_user_rides_history();

    Response add_user_question(String message, User sender);
    Response view_all_user_questions(String user_email);

    // RP API - ?

    Response start_ride(Location begin, Location dest, User user);
    Response finish_ride(Ride ride);
}
