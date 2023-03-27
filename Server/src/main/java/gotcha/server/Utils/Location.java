package gotcha.server.Utils;

import java.math.BigDecimal;
import java.util.Objects;

public class Location {
    private BigDecimal lng;
    private BigDecimal lat;
    // TODO: 18/03/2023 : try to add city and country. 

    public Location(BigDecimal lng, BigDecimal lat){
        this.lng = lng;
        this.lat = lat;
    }

    // TODO: 17/03/2023 : extend to some radios who will set in config.
    public boolean equals(Object o) {
        double radios;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return lng.equals(location.getLng()) &&
                lat.equals(location.getLat());
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lng, lat);
    }

    @Override
    public String toString() {
        return "Location{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
