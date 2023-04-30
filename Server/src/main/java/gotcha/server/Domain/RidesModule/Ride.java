package gotcha.server.Domain.RidesModule;

import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ride_id = 0;
    @Column(name="riderEmail")
    private String rider_email;

    @Column(name="ride")
    private LocalDate date;

    @Column(name="city")

    private String city;

    @Column(name="startTime")

    private LocalDateTime start_time;

    @Column(name="endTime")

    private LocalDateTime end_time;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude"))
    })
    private Location origin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude"))
    })
    private Location destination;

    @Column(name = "originAddress", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String originAddress;

    @Column(name = "destinationAddress", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String destinationAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ride_id")
    private List<RidingAction> actions;

    @ElementCollection()
    @CollectionTable(name = "junctions", joinColumns = @JoinColumn(name = "ride_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "junction_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "junction_latitude"))
    })

    private List<Location> junctions;

    @Column(name = "duration")
    private String duration; // minutes

    @Column(name = "distance")
    private double distance; // Km



    public Ride()
    {

    }

    public Ride(String rider_email, String city, LocalDateTime start_time, LocalDateTime end_time, Location origin, Location destination, List<RidingAction> actions, List<LocationDTO> junctions,  String originAddress, String destinationAddress) {
        this.rider_email = rider_email;
        this.date = start_time.toLocalDate();
        this.city = city;
        this.start_time = start_time;
        this.end_time = end_time;
        this.origin = origin;
        this.destination = destination;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.actions = actions;
        this.distance = (origin.distanceTo(destination));
        this.duration = setDurationByTimes();
        this.junctions = junctions.stream().map(Location::new).collect(Collectors.toList());
    }

    public Ride(int rideId, String userEmail, FinishRideRequest finishRideRequest, String originAddress, String destinationAddress) {
        this.ride_id = rideId;
        this.rider_email = userEmail;
        this.origin = new Location(finishRideRequest.getOrigin());
        this.destination = new Location(finishRideRequest.getDestination());
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.date = finishRideRequest.getStartTime().toLocalDate();
        this.city = finishRideRequest.getCity();
        this.start_time = finishRideRequest.getStartTime();
        this.end_time = finishRideRequest.getEndTime();
        this.actions = finishRideRequest.getRidingActions();
        this.junctions = new ArrayList<>();
        for (LocationDTO junction : finishRideRequest.getJunctions()){
            junctions.add(new Location(junction));

        }
        this.distance = (origin.distanceTo(destination) * 1000);
        this.duration = setDurationByTimes();
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

    private String setDurationByTimes() {
        Duration duration = Duration.between(start_time, end_time);
        double seconds = duration.getSeconds();
        int hours = (int) (seconds / 3600);
        int minutes = (int) ((seconds % 3600) / 60);
        return String.format("%02d.%02d", hours, minutes);
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

    public List<Location> getJunctions() {
        return junctions;
    }

    public void setJunctions(List<Location> junctions) {
        this.junctions = junctions;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
