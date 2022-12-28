package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.User;

import java.util.Dictionary;
import java.util.List;

public class UserRateCalculator implements IUserRateCalculator {
    private Dictionary<Integer, Integer> speed_changes_per_minute;
    private Dictionary<Integer, Integer> brakes_per_minute;
    private Dictionary<Integer, Integer> sharp_turns_per_minute;
    private Dictionary<Integer, Integer> alerts_per_minute;
    private Dictionary<Integer, Integer> on_sidewalk;

    /**
     * the method calculate the new rate of a user,
     * the method will consider the previous rate of user with weight of user's number of rides.
     * @param user - rider who just finish ride.
     * @param ride - the last ride.
     */
    @Override
    public void update_user_rating(User user, Ride ride){

    }

    /**
     * this method should get an EXCEL file and covert him to map of 5 tables.
     * @Precondition - legal tables.
     * @param tables - map of 5 tables.
     */
    @Override
    public void update_tables(Dictionary<String, Dictionary<Integer, Integer>> tables){

        this.speed_changes_per_minute = tables.get("speed_changes_per_minute");
        this.brakes_per_minute = tables.get("brakes_per_minute");
        this.sharp_turns_per_minute = tables.get("sharp_turns_per_minute");
        this.alerts_per_minute = tables.get("alerts_per_minute");
        this.on_sidewalk = tables.get("on_sidewalk");
    }
}
