package gotcha.server.Domain.HazardsModule;

import gotcha.server.Domain.RatingModule.HazardRateCalculator;
import gotcha.server.Utils.Location;
/**
 * this DAO is for Admin application.
 */

public class StationaryHazardDAO {
    private int id;
    private int ride_id;
    private String location;
    private String city;
    private HazardType type;
    private double size;
    private double rate;
    private String report;
    private double lat;
    private double lng;

    public StationaryHazardDAO(){}

    public StationaryHazardDAO(StationaryHazard stationaryHazard) {
        this.id = stationaryHazard.getId();
        this.ride_id = stationaryHazard.getRide_id();
        this.location = stationaryHazard.getLocation().toString();
        this.lat = stationaryHazard.getLocation().getLatitude().doubleValue();
        this.lng = stationaryHazard.getLocation().getLongitude().doubleValue();
        this.city = stationaryHazard.getCity();
        this.type = stationaryHazard.getType();
        this.size = stationaryHazard.getSize();
        this.rate = stationaryHazard.getRate();
        if (stationaryHazard.isReport()){
            this.report = "Yes";
        }
        else {
            this.report = "No";
        }
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

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
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


    public void setRate(double rate) {
        this.rate = rate;

    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
