package gotcha.server.Domain.HazardsModule;

import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.SystemLogger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HazardController implements IHazardController {
    private Map<Integer, StationaryHazard> stationaryHazardsList;
    private AtomicInteger id_counter;


    private static class SingletonHolder {
        private static HazardController instance = new HazardController();
    }
    public static HazardController get_instance() {
        return HazardController.SingletonHolder.instance;
    }

    public HazardController() {
        this.stationaryHazardsList = new ConcurrentHashMap<>();
        this.id_counter = new AtomicInteger(1);
    }

    @Override
    public void load(){

        SystemLogger.getInstance().add_log("Hazard controller loaded successfully");

    }

    public void add_hazard(int ride_id, Location location, String city, HazardType type, double size){
        int hazard_id = this.id_counter.incrementAndGet();
        StationaryHazard hazard = this.find_hazard_if_exist(location, city, type);
        if (hazard != null)
            this.update_hazard(hazard, size);
        else{
            StationaryHazard hazard_to_add = new StationaryHazard(hazard_id, ride_id, location, city, type, size);
            this.stationaryHazardsList.put(hazard_id, hazard_to_add);
        }

    }

    private void update_hazard(StationaryHazard hazard, double size) {
        hazard.setSize(size);
    }

    /**
     *
     * @param location
     * @param city
     * @param type
     * @return null if the hazard isnt exist in the system,
     * @return StationaryHazard if the hazard exist in the system.
     */
    private StationaryHazard find_hazard_if_exist(Location location, String city, HazardType type) {
        StationaryHazard hazard = null;
        for (StationaryHazard stationaryHazard : stationaryHazardsList.values()) {
            if (stationaryHazard.getCity().equals(city) && stationaryHazard.getType().equals(type) && stationaryHazard.getLocation().equals(location))
                hazard = stationaryHazard;
        }
        return hazard;
    }

    public void remove_hazard(int hazard_id){

    }

    // this private method will called from add_hazard / update_hazard and will update hazard type and size - will use Rating Module
    private void rate_hazard(){}

    private List<StationaryHazard> get_hazards(HazardType type){
        return new LinkedList<>();
    }
    private List<StationaryHazard> get_hazards(String city){
        return new LinkedList<>();
    }
    private List<StationaryHazard> get_hazards(Location location, double radios){
        return new LinkedList<>();
    }

    // TODO: 30/12/2022 : remove // after open class "Route"
    // TODO: 28/12/2022 : implement
//    public List<StationaryHazard> get_hazards_in_route(Route route) {
//        return null;
//    }
}
