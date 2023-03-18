package gotcha.server.Utils;

import java.util.Objects;

public class Location {
    private Double lng;
    private Double lat;

    public Location(Double lng, Double lat){
        this.lng = lng;
        this.lat = lat;
    }

    // TODO: 17/03/2023 : extend to some radios who will set in config.
    public boolean equals(Object o, double radios) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return lng.equals(location.getLng()) &&
                lat.equals(location.getLat());
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lng, lat);
    }

    @Override
    // TODO: 17/03/2023 : implement 
    public String toString() {
        return "Location{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
