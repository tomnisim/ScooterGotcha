package gotcha.server.Domain.RidesModule;

import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Utils.Exceptions.RideNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IRidesController {

    Ride add_ride(FinishRideRequest finishRideRequest, String userEmail, String originAddress, String destinationAddress) throws Exception;

    void remove_ride(int ride_id) throws RideNotFoundException;

    List<Ride> get_rides_by_email(String rider_email);

    List<Ride> get_rides(LocalDate[] dates_ranges);

    List<Ride> get_rides_by_city(String city);

    List<RideDTO> get_all_rides();

    int get_number_of_rides(String rider_id);
}
