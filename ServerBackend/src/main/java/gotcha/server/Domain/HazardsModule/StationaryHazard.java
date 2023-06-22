package gotcha.server.Domain.HazardsModule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gotcha.server.Domain.RatingModule.HazardRateCalculator;
import gotcha.server.Utils.Location;

import javax.persistence.*;

@Entity
@Table(name="hazards")
public class StationaryHazard implements IHazard {

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
        //this.calculateRate();
    }

    // Default Constructor for deserialization
    public StationaryHazard() {}


    @Override
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getRide_id() {
        return ride_id;
    }
    @Override
    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String getCity() {
        return city;
    }
    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public HazardType getType() {
        return type;
    }
    @Override
    public void setType(HazardType type) {
        this.type = type;
    }

    @Override
    public double getSize() {
        return size;
    }
    @Override
    public void setSize(double size) {
        this.size = size;
    }


    @Override
    public double getRate() {
        return rate;
    }
    @Override
    public void calculateRate() {
        HazardRateCalculator hazardRateCalculator = HazardRateCalculator.get_instance();
        this.rate = hazardRateCalculator.rate_hazard(this);

    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean isReport() {
        return report;
    }

    @Override
    public void setReport(boolean report) {
        this.report = report;
    }

    public StationaryHazardDTO getDTO(){
        return new StationaryHazardDTO(this);
    }

    @Override
    public String getPhotoS3Key() {
        return photoS3Key;
    }

    @Override
    public void setPhotoS3Key(String photoS3Key) {
        this.photoS3Key = photoS3Key;
    }
}
