package gotcha.server.Domain.HazardsModule;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IHazardController {
    void load();
    void add_hazard(int ride_id, Location location, String city, HazardType type, double size);
    void remove_hazard(int hazard_id);
    void update_hazards(String hazard_info, int ride_id);
    List<StationaryHazard> get_hazards_in_route(Route route);

     Collection<StationaryHazardDAO> view_hazards();


//    void update_hazard(StationaryHazard hazard, double size);

//    StationaryHazard find_hazard_if_exist(Location location, String city, HazardType type);

//    List<StationaryHazard> get_hazards(HazardType type);
//    List<StationaryHazard> get_hazards(String city);
//    List<StationaryHazard> get_hazards(Location location, double radios);
//    List<StationaryHazard> view_hazards();

    void report_hazard(int hazardId) throws Exception;
}
