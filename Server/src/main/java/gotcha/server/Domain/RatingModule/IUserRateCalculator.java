package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.Rider;
import gotcha.server.Domain.UserModule.User;

import java.util.Dictionary;

public interface IUserRateCalculator {


    void update_tables(Dictionary<String, Dictionary<Integer, Integer>> tables);
    void set_tables();
    void update_user_rating(Rider rider, Ride ride, int number_of_rides);
    double calculate_rate_for_ride(Ride ride);
}
