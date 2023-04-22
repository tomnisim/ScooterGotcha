package gotcha.server.Domain.RidesModule;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Exceptions.RideNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RidesRepository {
    private Map<Integer, Ride> rides; // maps ride_id to ride
    private Map<String, Map<Integer,Ride>> rides_by_rider; // maps rider_email to List of his rides

    public RidesRepository() {
        rides = new ConcurrentHashMap<>();
        rides_by_rider = new ConcurrentHashMap<>();
    }

    public void add_ride(Ride newRide, String userEmail) throws Exception {
        var addRideResult = this.rides.putIfAbsent(newRide.getRide_id(), newRide);
        if (addRideResult != null) {
            throw new Exception("Ride already exists");
        }
        rides_by_rider.computeIfAbsent(userEmail, k -> new HashMap<>()).putIfAbsent(newRide.getRide_id(), newRide);
    }

    public List<Ride> getAllRides() {
        return  new ArrayList<>(rides.values());
    }

    public List<Ride> getAllRidesByRider(String riderEmail) {
        if (!rides_by_rider.containsKey(riderEmail))
        {
            rides_by_rider.put(riderEmail, new HashMap<>());

        }
        return new ArrayList<>(rides_by_rider.get(riderEmail).values());
    }

    public void removeRide(int rideId) throws RideNotFoundException {
        var result = rides.remove(rideId);
        if (result == null)
            throw new RideNotFoundException("hazard with id:" + rideId + " not found");
        var userEmail = result.getRider_email();
        rides_by_rider.get(userEmail).remove(rideId);
    }


}
