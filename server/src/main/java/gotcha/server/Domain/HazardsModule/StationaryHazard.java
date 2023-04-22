package gotcha.server.Domain.HazardsModule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gotcha.server.Domain.RatingModule.HazardRateCalculator;
import gotcha.server.Utils.Location;

import javax.persistence.*;

@Entity
@Table(name="hazards")
public class StationaryHazard {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = 0;
    @Column(name="rideId")
    private int ride_id;

    @Column(name="location")
    @Embedded
    private Location location;

    @Column(name="city")
    private String city;

    @Enumerated
    @Column(name="hazardType")
    private HazardType type;

    @Column(name="size")
    private double size;

    @Column(name="rate")
    private double rate;
    private boolean report;




    public StationaryHazard(int ride_id, Location location, String city, HazardType type, double size) {
        this.ride_id = ride_id;
        this.location = location;
        this.city = city;
        this.type = type;
        this.size = size;
        this.report = false;
        this.setRate();
    }


    public StationaryHazard(int id,int ride_id, Location location, String city, HazardType type, double size) {
        this.id = id;
        this.ride_id = ride_id;
        this.location = location;
        this.city = city;
        this.type = type;
        this.size = size;
        //this.setRate();
    }

    // Default Constructor for deserialization
    public StationaryHazard() {}


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getRide_id() {
        return ride_id;
    }
    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public HazardType getType() {
        return type;
    }
    public void setType(HazardType type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }


    public double getRate() {
        return rate;
    }


    public void setRate() {
        HazardRateCalculator hazardRateCalculator = HazardRateCalculator.get_instance();
        this.rate = hazardRateCalculator.rate_hazard(this);

    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public StationaryHazardDAO getDAO(){
        return new StationaryHazardDAO(this);
    }
}
