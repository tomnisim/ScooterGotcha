package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ride_id;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ride_id")
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
