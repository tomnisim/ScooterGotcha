package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserRateCalculatorTest {
    //UserRateCalculator userRateCalculator = UserRateCalculator.get_instance();


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void update_user_rating() {
    }

/*    @Test
    void calculate_rate_for_ride_0Brakes() {
        int ride_id = 1;
        String rider_email = "amit@gmail.com";
        String city = "Tel Aviv";
        LocalDateTime start_time = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.now();
        Location origin = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        Location destination = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        String actions = "";
        Ride ride = new Ride(ride_id, rider_email, city, start_time, end_time, origin, destination,  actions);
        double rate = userRateCalculator.calculate_rate_for_ride(ride);
        System.out.println(rate);
        assertEquals(rate, 50);
    }*/

/*    @Test
    void calculate_rate_for_ride_1Brakes() {
        int ride_id = 1;
        String rider_email = "amit@gmail.com";
        String city = "Tel Aviv";
        LocalDateTime start_time = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.now();
        Location origin = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        Location destination = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        String actions = "1";
        Ride ride = new Ride(ride_id, rider_email, city, start_time, end_time, origin, destination,  actions);
        double rate = userRateCalculator.calculate_rate_for_ride(ride);
        System.out.println(rate);
        assertEquals(rate, 49.5);
    }*/

/*    @Test
    void calculate_rate_for_ride_2Brakes() {
        int ride_id = 1;
        String rider_email = "amit@gmail.com";
        String city = "Tel Aviv";
        LocalDateTime start_time = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.now();
        Location origin = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        Location destination = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        String actions = "1:1";
        Ride ride = new Ride(ride_id, rider_email, city, start_time, end_time, origin, destination,  actions);
        double rate = userRateCalculator.calculate_rate_for_ride(ride);
        System.out.println(rate);
        assertEquals(rate, 48.5);
    }*/

/*    @Test
    void calculate_rate_for_ride_3Brakes() {
        int ride_id = 1;
        String rider_email = "amit@gmail.com";
        String city = "Tel Aviv";
        LocalDateTime start_time = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.now();
        Location origin = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        Location destination = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        String actions = "1:1:1";
        Ride ride = new Ride(ride_id, rider_email, city, start_time, end_time, origin, destination,  actions);
        double rate = userRateCalculator.calculate_rate_for_ride(ride);
        System.out.println(rate);
        assertEquals(rate, 48);
    }*/

    /*@Test
    void calculate_rate_for_ride_4Brakes() {
        int ride_id = 1;
        String rider_email = "amit@gmail.com";
        String city = "Tel Aviv";
        LocalDateTime start_time = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.now();
        Location origin = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        Location destination = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        String actions = "1:1:1:1";
        Ride ride = new Ride(ride_id, rider_email, city, start_time, end_time, origin, destination,  actions);
        double rate = userRateCalculator.calculate_rate_for_ride(ride);
        System.out.println(rate);
        assertEquals(rate, 47);
    }

    @Test
    void calculate_rate_for_ride_5Brakes() {
        int ride_id = 1;
        String rider_email = "amit@gmail.com";
        String city = "Tel Aviv";
        LocalDateTime start_time = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.now();
        Location origin = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        Location destination = new Location(new BigDecimal("0.0"), new BigDecimal("0.0"));
        String actions = "1:1:1:1:1";
        Ride ride = new Ride(ride_id, rider_email, city, start_time, end_time, origin, destination,  actions);
        double rate = userRateCalculator.calculate_rate_for_ride(ride);
        System.out.println(rate);
        assertEquals(rate, 45);
    }*/

    @Test
    void update_tables() {
    }
}