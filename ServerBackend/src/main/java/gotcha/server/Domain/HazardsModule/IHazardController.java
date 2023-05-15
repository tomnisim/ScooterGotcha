package gotcha.server.Domain.HazardsModule;

import gotcha.server.Domain.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;

import java.util.Collection;
import java.util.List;

public interface IHazardController {
    void remove_hazard(int hazard_id) throws Exception;
    void update_hazards(List<StationaryHazardRPDTO> hazards, int ride_id, String city) throws Exception;
    List<StationaryHazard> get_hazards_in_route(Route route);
    void add_hazard(int rideId, Location location, String city, HazardType type, double size,  byte[] photo) throws Exception;

    Collection<StationaryHazardDTO> view_hazards();


//    void update_hazard(StationaryHazard hazard, double size);

//    StationaryHazard find_hazard_if_exist(Location location, String city, HazardType type);

//    List<StationaryHazard> get_hazards(HazardType type);
    List<StationaryHazardDTO> get_hazards(String city);
//    List<StationaryHazard> get_hazards(Location location, double radios);
//    List<StationaryHazard> view_hazards();

    void report_hazard(int hazardId) throws Exception;

    List<Integer> auto_report();

}
