package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * this DTO is for ADMIN.
 */
public class RideDTO {
    private int ride_id;
    private String rider_email;
    private LocalDate date;
    private String city;
    private LocalTime start_time;
    private LocalTime end_time;
    private String origin;
    private String destination;
    private List<RidingAction> actions;

    private double destination_lng;
    private double destination_lat;
    private double origin_lat;
    private double origin_lng;
    private String duration;
    private double distance;
    private List<LocationDTO> junctions;

    public RideDTO(){}
    public RideDTO(Ride ride){

        this.ride_id = ride.getRide_id();
        this.rider_email = ride.getRider_email();
        this.city = ride.getCity();
        this.date = ride.getDate();
        this.start_time = ride.getStart_time().toLocalTime();
        this.end_time = ride.getEnd_time().toLocalTime();
        this.origin = ride.getOriginAddress();
        this.destination = ride.getDestinationAddress();
        this.actions = ride.getActions();
        this.destination_lng = ride.getDestination().getLongitude().doubleValue();
        this.destination_lat = ride.getDestination().getLatitude().doubleValue();
        this.origin_lng = ride.getOrigin().getLongitude().doubleValue();
        this.origin_lat = ride.getOrigin().getLatitude().doubleValue();
        this.distance = ride.getDistance();
        this.duration = ride.getDuration();
        this.junctions = new ArrayList<>();
        for (Location junction : ride.getJunctions()){
            this.junctions.add(new LocationDTO(junction));
        }
    }

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

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<RidingAction> getActions() {
        return actions;
    }

    public void setActions(List<RidingAction> actions) {
        this.actions = actions;
    }

    public double getDestination_lng() {
        return destination_lng;
    }

    public void setDestination_lng(double destination_lng) {
        this.destination_lng = destination_lng;
    }

    public double getDestination_lat() {
        return destination_lat;
    }

    public void setDestination_lat(double destination_lat) {
        this.destination_lat = destination_lat;
    }

    public double getOrigin_lat() {
        return origin_lat;
    }

    public void setOrigin_lat(double origin_lat) {
        this.origin_lat = origin_lat;
    }

    public double getOrigin_lng() {
        return origin_lng;
    }

    public void setOrigin_lng(double origin_lng) {
        this.origin_lng = origin_lng;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<LocationDTO> getJunctions() {
        return junctions;
    }

    public void setJunctions(List<LocationDTO> junctions) {
        this.junctions = junctions;
    }
}
