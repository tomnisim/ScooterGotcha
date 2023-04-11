package gotcha.server.Domain.HazardsModule;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class HazardController implements IHazardController {
    private final SystemLogger systemLogger;
    private final HazardRepository hazardRepository;
    @Autowired
    public HazardController(SystemLogger systemLogger, HazardRepository hazardRepository) {
        this.systemLogger = systemLogger;
        this.hazardRepository = hazardRepository;
    }

    @Override
    public void load(){
        systemLogger.add_log("Hazard controller loaded successfully");
    }


    @Override
    public void add_hazard(int ride_id,Location location, String city, HazardType hazardType, double size) throws Exception {
        var hazard = new StationaryHazard(ride_id, location, city, hazardType, size);
        this.hazardRepository.add_hazard(hazard);
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
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()) {
            if (stationaryHazard.getCity().equals(city) && stationaryHazard.getType().equals(type) && stationaryHazard.getLocation().equals(location))
                hazard = stationaryHazard;
        }
        return hazard;
    }
    public void remove_hazard(int hazard_id) throws Exception {
        this.hazardRepository.removeHazard(hazard_id);
    }

    /**
     * this method should check for every hazard if he already exist -
     * if yes, update him,
     * else, add this hazard.
     * @param hazards
     * @param ride_id
     */
    @Override
    public void update_hazards(List<StationaryHazard> hazards, int ride_id) throws Exception {
        for (StationaryHazard hazard : hazards){
            Location location = hazard.getLocation();
            String city = hazard.getCity();
            HazardType type = hazard.getType();
            double size = hazard.getSize();
            StationaryHazard current = find_hazard_if_exist(location, city, type);
            if (current == null){
                add_hazard(hazard.getRide_id(), hazard.getLocation(), hazard.getCity(), hazard.getType(), hazard.getSize());
            }
            else
                update_hazard(current, size);
        }
    }


    private List<StationaryHazard> get_hazards(HazardType type) {
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()) {
            if (stationaryHazard.getType().equals(type)) {
                list.add(stationaryHazard);
            }
        }
        return list;
    }




    private List<StationaryHazard> get_hazards(String city){
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()){
            if (stationaryHazard.getCity().equals(city)){
                list.add(stationaryHazard);
            }
        }
        return list;
    }
    private List<StationaryHazard> get_hazards(Location location){
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()){
            if (stationaryHazard.getLocation().equals(location)){
                list.add(stationaryHazard);
            }
        }
        return list;
    }

    public List<StationaryHazard> get_hazards_in_route(Route route) {
        LinkedList<StationaryHazard> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()){
            for (Location junction : route.getJunctions()){
                if (stationaryHazard.getLocation().equals(junction)){
                    list.add(stationaryHazard);
                }

            }
        }
        return list;
    }
    @Override

    public Collection<StationaryHazardDAO> view_hazards() {
        Collection<StationaryHazardDAO> list_to_return = new ArrayList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()){
            list_to_return.add(stationaryHazard.getDAO());
        }
        return list_to_return;
    }
}
