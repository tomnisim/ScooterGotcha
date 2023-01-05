package gotcha.server.Domain.RidesModule;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Exceptions.RideNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RidesController {
    private AtomicInteger id_counter;
    private Map<Integer, Ride> rides;
    private static class SingletonHolder {
        private static RidesController instance = new RidesController();
    }
    public static RidesController get_instance() {
        return RidesController.SingletonHolder.instance;
    }

    public RidesController()
    {
        this.id_counter = new AtomicInteger(1);
    }

    public void load() {
        //TODO
    }

    /**
     *
     * @param ride_info - information about the ride from RP
     * @param hazard_info - information about the hazards during the ride from RP
     */
    public Ride add_ride(String ride_info, String hazard_info)
    {
        int ride_id = this.id_counter.incrementAndGet();
        Ride ride = new Ride(); // TODO - extract the information from the JSON
        this.rides.put(ride_id, ride);
        return ride;

    }

    public void remove_ride(int ride_id) throws RideNotFoundException {
        if (!this.rides.containsKey(ride_id))
        {
            throw new RideNotFoundException("No Such Ride");
        }
        this.rides.remove(ride_id);

    }

    public List<Ride> get_rides(int rider_id)
    {
        List<Ride> rides_by_rider_id = new ArrayList<>();
        for (Ride ride : this.rides.values())
        {
            if (ride.getRider_id() == rider_id)
            {
                rides_by_rider_id.add(ride);
            }
        }
        return rides_by_rider_id;

    }

    public List<Ride> get_rides(LocalDate[] dates_ranges)
    {
        LocalDate start_date = dates_ranges[0];
        LocalDate end_date = dates_ranges[1];
        List<Ride> rides_in_date_ranges = new ArrayList<Ride>();
        for (Ride ride : this.rides.values())
        {
            if ((ride.getDate().isAfter(start_date) || ride.getDate().isEqual(start_date)) && (ride.getDate().isBefore(end_date) || ride.getDate().isEqual(end_date)))
            {
                rides_in_date_ranges.add(ride);
            }
        }
        return rides_in_date_ranges;

    }
    public List<Ride> get_rides(String city)
    {
        List<Ride> rides_by_city = new ArrayList<Ride>();
        for (Ride ride : this.rides.values())
        {
            if (ride.getCity() == city)
            {
                rides_by_city.add(ride);
            }
        }
        return rides_by_city;

    }
    public List<Ride> get_all_rides()
    {
        return new ArrayList<>(this.rides.values());
    }

}
