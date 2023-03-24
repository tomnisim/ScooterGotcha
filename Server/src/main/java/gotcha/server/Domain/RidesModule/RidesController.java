package gotcha.server.Domain.RidesModule;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Exceptions.RideNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RidesController implements IRidesController {
    private AtomicInteger id_counter;
    private Map<Integer, Ride> rides; // maps ride_id to ride
    private Map<String, List<Ride>> rides_by_rider; // maps rider_email to List of his rides
    static class SingletonHolder {
        static RidesController instance = new RidesController();
    }

    public RidesController()
    {
        this.id_counter = new AtomicInteger(1);
    }

    @Override
    public void load() {
        //TODO
    }

    public static RidesController get_instance() {
        return RidesController.SingletonHolder.instance;
    }

    /**
     *
     * @param ride_info - information about the ride from RP
     */
    @Override
    public Ride add_ride(String ride_info)
    {
        int ride_id = this.id_counter.incrementAndGet();
        Ride ride = new Ride(); // TODO - extract the information from the JSON
        this.rides.put(ride_id, ride);
        String rider_email = ride.getRider_email();
        if (!rides_by_rider.containsKey(rider_email))
        {
            rides_by_rider.put(rider_email, new ArrayList<>());
        }
        rides_by_rider.get(rider_email).add(ride);
        return ride;

    }

    @Override
    public void remove_ride(int ride_id) throws RideNotFoundException {
        if (!this.rides.containsKey(ride_id))
        {
            throw new RideNotFoundException("No Such Ride");
        }
        Ride ride = this.rides.get(ride_id);
        this.rides_by_rider.get(ride.getRider_email()).remove(ride);
        this.rides.remove(ride_id);

    }

    @Override
    public List<Ride> get_rides(int rider_email)
    {
        return rides_by_rider.get(rider_email);
    }

    /**
     *
     * @param dates_ranges array of 2 elements - first element is start date , second element is end_date
     * @return
     */
    @Override
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
    @Override
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
    @Override
    public List<Ride> get_all_rides()
    {
        return new ArrayList<>(this.rides.values());
    }
    
    @Override
    public int get_number_of_rides(String rider_id){
        // TODO: 05/01/2023 : tom - change fields, add field of <user_email, List<Ride>> 
        return this.get_rides(rider_id).size();
        
    }

}
