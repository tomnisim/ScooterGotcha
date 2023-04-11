package gotcha.server.Domain.HazardsModule;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IHazardController {
    void load();
    void remove_hazard(int hazard_id) throws Exception;
    void update_hazards(List<StationaryHazard> hazards, int ride_id) throws Exception;
    List<StationaryHazard> get_hazards_in_route(Route route);

    Collection<StationaryHazardDAO> view_hazards();


//    void update_hazard(StationaryHazard hazard, double size);

//    StationaryHazard find_hazard_if_exist(Location location, String city, HazardType type);
//    List<StationaryHazard> get_hazards(HazardType type);
//    List<StationaryHazard> get_hazards(String city);
//    List<StationaryHazard> get_hazards(Location location, double radios);
}
