package gotcha.server.Utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    BigDecimal longitude = new BigDecimal("34.855490");
    BigDecimal latitude = new BigDecimal("32.333090");
    Location cinema_city_location = new Location(longitude, latitude);
    BigDecimal longitude1 = new BigDecimal("34.856540");
    BigDecimal latitude1 = new BigDecimal("32.329370");
    Location stadium_location = new Location(longitude1, latitude1);

    BigDecimal longitude2 = new BigDecimal("34.790090");
    BigDecimal latitude2 = new BigDecimal("32.069470");
    Location beit_lesin_location = new Location(longitude2, latitude2);

    @Test
    void testEquals() {
    }


    @Test
    void equals_radios(){
        BigDecimal longitude = new BigDecimal("34.855479");
        BigDecimal latitude = new BigDecimal("32.333080");
        Location location = new Location(longitude, latitude);
        System.out.println(location.distanceTo(cinema_city_location));
        assertTrue(cinema_city_location.equals(location));
    }
    @Test
    void distanceTo_Netanya_inside() {
        double distance = cinema_city_location.distanceTo(stadium_location);
        assertTrue(distance < 1 && distance > 0.2);
    }
    @Test
    void distanceTo_Netanya_TelAviv() {
        double distance1 = stadium_location.distanceTo(beit_lesin_location);
        assertTrue(distance1 > 25 && distance1 < 35);
    }

    @Test
    void distance_netanya_Haifa(){
        BigDecimal longitude = new BigDecimal("34.9665508");
        BigDecimal latitude = new BigDecimal("32.7816484");
        Location haifa_location = new Location(longitude, latitude);
        double haifa_netanya_distance = haifa_location.distanceTo(stadium_location);
        assertTrue(haifa_netanya_distance< 60 && haifa_netanya_distance > 50);

    }


    @Test
    void testToString() {
        System.out.println(stadium_location.toString());
    }
}