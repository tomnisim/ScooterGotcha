package gotcha.server.Domain.HazardsModule;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;

import java.util.List;

public interface IHazardController {
    void load();
    void add_hazard(int ride_id, Location location, String city, HazardType type, double size);
    void remove_hazard(int hazard_id);
    void update_hazards(String hazard_info, int ride_id);
    List<StationaryHazard> get_hazards_in_route(Route route);


//    void update_hazard(StationaryHazard hazard, double size);
//    StationaryHazard find_hazard_if_exist(Location location, String city, HazardType type);

//    List<StationaryHazard> get_hazards(HazardType type);
//    List<StationaryHazard> get_hazards(String city);
//    List<StationaryHazard> get_hazards(Location location, double radios);
}
