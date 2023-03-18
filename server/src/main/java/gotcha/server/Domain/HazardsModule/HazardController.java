package gotcha.server.Domain.HazardsModule;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import gotcha.server.SafeRouteCalculatorModule.Route;
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
        this.stationaryHazardsList.remove(hazard_id);

    }

    /**
     * this method should check for every hazard if he already exist -
     * if yes, update him,
     * else, add this hazard.
     * @param hazard_info
     */
    @Override
    public void update_hazards(String hazard_info, int ride_id) {
        // TODO: 05/01/2023 : build from hazards info - change List<Hazard> to something else.
        List<StationaryHazard> hazards = new LinkedList<>();
        for (StationaryHazard hazard : hazards){
            Location location = null;
            String city = null;
            HazardType type = null;
            double size =  0;
            StationaryHazard current = find_hazard_if_exist(location, city, type);
            if (current == null){
                add_hazard(ride_id, location, city, type, size);
            }
            else
                update_hazard(current, size);
        }

    }


    private List<StationaryHazard> get_hazards(HazardType type){
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : this.stationaryHazardsList.values()){
            if (stationaryHazard.getType().equals(type)){
                list.add(stationaryHazard);
            }
        }
        return list;
    }

    private List<StationaryHazard> get_hazards(String city){
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : this.stationaryHazardsList.values()){
            if (stationaryHazard.getCity().equals(city)){
                list.add(stationaryHazard);
            }
        }
        return list;
    }
    private List<StationaryHazard> get_hazards(Location location, double radios){
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : this.stationaryHazardsList.values()){
            if (stationaryHazard.getLocation().equals(location, radios)){
                list.add(stationaryHazard);
            }
        }
        return list;
    }

    public List<StationaryHazard> get_hazards_in_route(Route route) {
        double radios = 0.0;
        // TODO: 17/03/2023 : radios
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : this.stationaryHazardsList.values()){
            for (Location junction : route.getJunctions()){
                if (stationaryHazard.getLocation().equals(junction, radios)){
                    list.add(stationaryHazard);
                }

            }
        }
        return list;
    }
}
