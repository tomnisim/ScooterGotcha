package gotcha.server.Utils;

import java.math.BigDecimal;

public class LocationDTO {
    private double longitude;
    private double latitude;

    public LocationDTO(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationDTO(Location location){
        this.longitude = location.getLongitude().doubleValue();
        this.latitude = location.getLatitude().doubleValue();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "longitude is:" + longitude + "\n latitude is:" + latitude + "\n";
    }
}
