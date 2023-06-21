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
    @Column(name="rideId", nullable = false)
    private int ride_id;

    @Column(name="location", nullable = false)
    @Embedded
    private Location location;

    @Column(name="city", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci", nullable = false)
    private String city;

    @Enumerated
    @Column(name="hazardType", nullable = false)
    private HazardType type;

    @Column(name="size", nullable = false)
    private double size;

    @Column(name="rate", nullable = false)
    private double rate;

    @Column(name="report", nullable = false)
    private boolean report;


    @Transient
    private byte[] photo;
    @Column(name="s3Key", nullable = false)
    private String photoS3Key;



    public StationaryHazard(int ride_id, Location location, String city, HazardType type, double size) {
        this.ride_id = ride_id;
        this.location = location;
        this.city = city;
        this.type = type;
        this.size = size;
        this.report = false;
        this.photoS3Key = "";
        this.calculateRate();
    }

    // Default Constructor for deserialization
    public StationaryHazard() {}


    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

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
    public void calculateRate() {
        HazardRateCalculator hazardRateCalculator = HazardRateCalculator.get_instance();
        this.rate = hazardRateCalculator.rate_hazard(this);

    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public StationaryHazardDTO getDTO(){
        return new StationaryHazardDTO(this);
    }

    public String getPhotoS3Key() {
        return photoS3Key;
    }

    public void setPhotoS3Key(String photoS3Key) {
        this.photoS3Key = photoS3Key;
    }
}
