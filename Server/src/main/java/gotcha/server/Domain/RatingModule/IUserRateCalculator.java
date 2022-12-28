package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.User;

import java.util.Dictionary;

public interface IUserRateCalculator {
    
    void update_user_rating(User user, Ride ride);
    void update_tables(Dictionary<String, Dictionary<Integer, Integer>> tables);
}
