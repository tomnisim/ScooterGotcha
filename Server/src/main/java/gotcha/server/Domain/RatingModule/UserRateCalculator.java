package gotcha.server.Domain.RatingModule;

import gotcha.server.Config.RiderRatingConfiguration;
import gotcha.server.Domain.RidesModule.*;
import gotcha.server.Domain.UserModule.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

@Component
public class UserRateCalculator implements IUserRateCalculator {
    private final RiderRatingConfiguration configuration;

    @Autowired
    public UserRateCalculator(RiderRatingConfiguration configuration){
        this.configuration = configuration;
    }


    /**
     * the method calculate the new rate of a user,
     * the method will consider the previous rate of user with weight of user's number of rides.
     * @param rider - rider who just finish ride.
     * @param ride - the last ride.
     */
    @Override
    public void update_user_rating(Rider rider, Ride ride, int number_of_rides){
        double new_rate;
        double user_previous_rate = rider.getRating();
        double current_ride_rate = this.calculate_rate_for_ride(ride);
        new_rate = user_previous_rate * number_of_rides;
        new_rate = new_rate + current_ride_rate;
        new_rate = new_rate / number_of_rides;
        rider.setRating(new_rate);
    }

    private double calculate_rate_for_ride(Ride ride) {
        double rate = 0;

        int speed_changes_per_minuteCounter = 0;
        int brakes_per_minuteCounter = 0;
        int sharp_turns_per_minuteCounter = 0;
        int alerts_per_minuteCounter = 0;
        int on_sidewalkCounter = 0;

        for (RidingAction action : ride.getActions()){
            if (action instanceof SpeedChange)
            {
                brakes_per_minuteCounter++;
            }
            if (action instanceof SharpTurn){
                sharp_turns_per_minuteCounter++;
            }
            if (action instanceof Sidewalk)
            {
                on_sidewalkCounter++;
            }
            if (action instanceof Alert){
                alerts_per_minuteCounter++;
            }
            if (action instanceof SpeedChange)
            {
                speed_changes_per_minuteCounter++;
            }
        }

        speed_changes_per_minuteCounter = Math.min(speed_changes_per_minuteCounter, 6);
        brakes_per_minuteCounter = Math.min(brakes_per_minuteCounter, 5);
        sharp_turns_per_minuteCounter = Math.min(sharp_turns_per_minuteCounter, 4);
        alerts_per_minuteCounter = Math.min(alerts_per_minuteCounter, 6);
        on_sidewalkCounter = Math.min(on_sidewalkCounter, 40);

        rate = configuration.getAlerts_per_minute().get(alerts_per_minuteCounter) + configuration.getBrakes_per_minute().get(brakes_per_minuteCounter) +
               configuration.getSpeed_changes_per_minute().get(speed_changes_per_minuteCounter) + configuration.getSharp_turns_per_minute().get(sharp_turns_per_minuteCounter)
               + configuration.getOn_sidewalk().get(on_sidewalkCounter);

        rate = rate / 10;

        return rate;
    }
}
