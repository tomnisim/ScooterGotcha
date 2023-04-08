package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.Rider;
import gotcha.server.Domain.UserModule.User;

import java.util.Dictionary;

public interface IUserRateCalculator {
    void update_user_rating(Rider rider, Ride ride, int number_of_rides);
}
