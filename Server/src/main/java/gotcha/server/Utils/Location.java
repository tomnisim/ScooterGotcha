package gotcha.server.Utils;


import java.math.BigDecimal;
import java.util.Objects;

public class Location {
    private final double RADIOS = 0.01; // TODO: 31/03/2023 : move to configuration. 
    
    private BigDecimal longitude;
    private BigDecimal latitude;
    // TODO: 18/03/2023 : try to add city and country. 

    public Location(BigDecimal lng, BigDecimal lat){
        this.longitude = lng;
        this.latitude = lat;
    }

    // Default constructor for deserialization
    public Location() {}

    public boolean equals(Location location) {
        if (this == location) return true;
        double distanceTo = this.distanceTo(location);
        if (distanceTo <= RADIOS)
            return true;
        return longitude.equals(location.getLongitude()) &&
                latitude.equals(location.getLatitude());
    }


    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @param that (Location)
     * @return distance between this location and that location, measured in statute KM
     */
    public double distanceTo(Location that) {
        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double lat1 = Math.toRadians(this.latitude.doubleValue());
        double lon1 = Math.toRadians(this.longitude.doubleValue());
        double lat2 = Math.toRadians(that.latitude.doubleValue());
        double lon2 = Math.toRadians(that.longitude.doubleValue());

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles * 1.60934;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    @Override
    public String toString() {
        return "Location{" +
                "lng=" + longitude +
                ", lat=" + latitude +
                '}';
    }
}
