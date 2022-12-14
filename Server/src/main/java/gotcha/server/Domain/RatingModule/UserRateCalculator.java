package gotcha.server.Domain.RatingModule;

import java.util.Dictionary;
import java.util.List;

public class UserRateCalculator {
    private Dictionary<Integer, Integer> speed_changes_per_minute;
    private Dictionary<Integer, Integer> brakes_per_minute;
    private Dictionary<Integer, Integer> sharp_turns_per_minute;
    private Dictionary<Integer, Integer> alerts_per_minute;
    private Dictionary<Integer, Integer> on_sidewalk;

    /**
     * this method will calculate the new rate of a user, this method will consider the previous rate of user with weight
     * of user's number of rides.
     * @param user - rider who just finish ride.
     * @param ride - the last ride.
     */
//    public void update_user_rating(User user, Ride ride){
//
//    }

    /**
     * this method should get an EXCEL file and covert him to map of 5 tables.
     * @Precondition - legal tables.
     * @param tables - map of 5 tables.
     */
    public void update_tables(Dictionary<String,Dictionary<Integer, Integer>> tables){

        this.speed_changes_per_minute = tables.get("speed_changes_per_minute");
        this.brakes_per_minute = tables.get("brakes_per_minute");
        this.sharp_turns_per_minute = tables.get("sharp_turns_per_minute");
        this.alerts_per_minute = tables.get("alerts_per_minute");
        this.on_sidewalk = tables.get("on_sidewalk");
    }
}
