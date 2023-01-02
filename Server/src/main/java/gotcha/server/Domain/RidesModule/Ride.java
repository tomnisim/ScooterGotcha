package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Ride {
    private int ride_id;
    private int rider_id;
    private LocalDate date; //TODO - check the date type
    private String city;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Location origin;
    private Location destination;
    private List<RidingAction> actions;


    public Ride(int ride_id, int rider_id, String city, LocalDateTime start_time, LocalDateTime end_time, Location origin, Location destination, List<RidingAction> actions) {
        this.ride_id = ride_id;
        this.rider_id = rider_id;
        this.date = LocalDate.now();
        this.city = city;
        this.start_time = start_time;
        this.end_time = end_time;
        this.origin = origin;
        this.destination = destination;

        this.actions = this.create_riding_actions(actions);
    }

    private List<RidingAction> create_riding_actions(List<RidingAction> actions) {
        // TODO - check how the riding action accepted from RP
        for (RidingAction action : actions)
        {
            RidingAction ridingAction;
            // crete RidingAction from RP riding action
            this.actions.add(ridingAction);

        }
    }

    public int getRide_id() {
        return ride_id;
    }

    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public List<RidingAction> getActions() {
        return actions;
    }

    public void setActions(List<RidingAction> actions) {
        this.actions = actions;
    }
}
