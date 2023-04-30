package gotcha.server.Domain.RidesModule;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Utils.Exceptions.RideNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import gotcha.server.Utils.Location;

@Component
public class RidesController implements IRidesController {
    private final RidesRepository ridesRepository;

    public RidesController(RidesRepository ridesRepository)
    {
        this.ridesRepository = ridesRepository;
    }

    @Override
    public void load() {
        //TODO : Database
    }

    /**
     *
     * @param finishRideRequest - information about the ride from RP
     */
    @Override
    public Ride add_ride(FinishRideRequest finishRideRequest, String userEmail) throws Exception {
        Ride ride = new Ride(userEmail,finishRideRequest.getCity(), finishRideRequest.getStartTime(), finishRideRequest.getEndTime(),finishRideRequest.getOrigin(), finishRideRequest.getDestination(), finishRideRequest.getRidingActions());
        this.ridesRepository.addRide(ride, userEmail);
        return ride;
    }

    @Override
    public void remove_ride(int ride_id) throws RideNotFoundException {
        ridesRepository.removeRide(ride_id);
    }

    @Override
    public List<Ride> get_rides_by_email(String rider_email) {
        return ridesRepository.getAllRidesByRider(rider_email);
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
        for (Ride ride : ridesRepository.getAllRides())
        {
            if ((ride.getDate().isAfter(start_date) || ride.getDate().isEqual(start_date)) && (ride.getDate().isBefore(end_date) || ride.getDate().isEqual(end_date)))
            {
                rides_in_date_ranges.add(ride);
            }
        }
        return rides_in_date_ranges;

    }
    @Override
    public List<Ride> get_rides_by_city(String city)
    {
        List<Ride> rides_by_city = new ArrayList<Ride>();
        for (Ride ride : ridesRepository.getAllRides())
        {
            if (ride.getCity().equals(city))
            {
                rides_by_city.add(ride);
            }
        }
        return rides_by_city;

    }

    @Override
    public List<RideDTO> get_all_rides()
    {
        List<RideDTO> to_return = new ArrayList<>();
        for (Ride ride : ridesRepository.getAllRides()){
            to_return.add(new RideDTO(ride));
        }
        return to_return;
    }
    
    @Override
    public int get_number_of_rides(String rider_email){
        return this.get_rides_by_email(rider_email).size();
        
    }

}
