package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ride {
    private int ride_id;
    private String rider_email;
    private LocalDate date;
    private String city;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Location origin;
    private Location destination;
    private List<RidingAction> actions;

    public Ride()
    {

    }
    public Ride(int ride_id, String rider_email, String city, LocalDateTime start_time, LocalDateTime end_time, Location origin, Location destination, List<RidingAction> actions) {
        this.ride_id = ride_id;
        this.rider_email = rider_email;
        this.date = LocalDate.now();
        this.city = city;
        this.start_time = start_time;
        this.end_time = end_time;
        this.origin = origin;
        this.destination = destination;
        this.actions = actions;

    }

    // ------------------------------------------ Getters & Setters ----------------------------------------------------------

    public int getRide_id() {
        return ride_id;
    }

    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public String getRider_email() {
        return rider_email;
    }

    public void setRider_email(String rider_email) {
        this.rider_email = rider_email;
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
