package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Exceptions.RideNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RidesControllerTest {
    private  RidesController ride_controller;
    public RidesControllerTest()
    {
        this.ride_controller = new RidesController();


    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void add_ride() {
        int prev_size = this.ride_controller.get_all_rides().size();
        ride_controller.add_ride("", "");
        assertEquals(prev_size+1, this.ride_controller.get_all_rides().size());
    }

    @Test
    void remove_ride() throws RideNotFoundException {
        ride_controller.add_ride("", "");
        List<Ride> rides = this.ride_controller.get_all_rides();
        int ride_id=1;
        for (Ride ride: rides)
        {
            ride_id = ride.getRide_id();
        }
        int prev_size = this.ride_controller.get_all_rides().size();
        ride_controller.remove_ride(ride_id);
        assertEquals(prev_size-1, this.ride_controller.get_all_rides().size());
    }

    @Test
    void get_rides_by_rider_id() {
    }

    @Test
    void get_rides_by_city() {
    }

    @Test
    void get_rides_by_date_ranges() {
    }
}