package gotcha.server.Domain.HazardsModule;

import gotcha.server.Domain.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.SystemLogger;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static gotcha.server.Utils.Cities.city_permutation;
import static gotcha.server.Utils.Utils.resizeByteArray;

@Component
public class HazardController implements IHazardController {
    private double HAZARD_THRESHOLD_RATE = 0;
    private final SystemLogger systemLogger;
    private final HazardRepository hazardRepository;
    private final ReporterAdapter reporterAdapter;


    @Autowired
    public HazardController(SystemLogger systemLogger, HazardRepository hazardRepository, ReporterAdapter reporterAdapter) {
        this.systemLogger = systemLogger;
        this.hazardRepository = hazardRepository;
        this.reporterAdapter = reporterAdapter;
     }


    @Override
    public void add_hazard(int rideId, Location location, String city, HazardType type, double size, byte[] photo) throws Exception {
        var newHazard = new StationaryHazard(rideId, location, city, type, size);
        this.hazardRepository.addHazard(newHazard, photo);
    }

    public void update_hazard(StationaryHazard hazard, double size) {
        this.hazardRepository.updateHazard(hazard, size);
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
        String city_perm = city_permutation(city);
        StationaryHazard hazard = null;
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()) {
            if (city_permutation(stationaryHazard.getCity()).equals(city_perm) && stationaryHazard.getType().equals(type) && stationaryHazard.getLocation().equals(location))
                return stationaryHazard;
        }
        return null;
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
     * @param city
     * */
    @Override
    public void update_hazards(List<StationaryHazardRPDTO> hazards, int ride_id, String city) throws Exception {
        for (StationaryHazardRPDTO hazard : hazards){
            Location location = new Location(hazard.getLocation());
            HazardType type = HazardType.valueOf(hazard.getType());
            double size = hazard.getSize();
            StationaryHazard current = find_hazard_if_exist(location, city, type);
            if (current == null){

                    add_hazard(ride_id, location, city, type, size, hazard.getFrame());
            }
            else{
                // TODO: update photo and remove comment
                // update_hazard(current, size);
                add_hazard(ride_id, location, city, type, size, hazard.getFrame());
            }
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



    public List<StationaryHazard> get_hazards(){
        return hazardRepository.getAllHazards();
    }

    public List<StationaryHazardDTO> get_hazards(String city){
        LinkedList<StationaryHazardDTO> list = new LinkedList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()){

            if (city_permutation(stationaryHazard.getCity()).equals(city_permutation(city))){
                list.add(new StationaryHazardDTO(stationaryHazard));
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

    public List<StationaryHazard> get_hazards_in_route1(Route route) {
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

    public List<StationaryHazard> get_hazards_in_route(Route route) {
        List<StationaryHazard> hazards = hazardRepository.getAllHazards();
        List<Location> junctions = route.getJunctions();
        // Create a LineString from the set of junctions
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate[] coordinates = junctions.stream()
                .map(location -> new Coordinate(location.getLongitude().doubleValue(), location.getLatitude().doubleValue()))
                .toArray(Coordinate[]::new);
        LineString lineString = geometryFactory.createLineString(coordinates);

        // Create a list to hold the locations on the road
        List<StationaryHazard> hazardsOnRoad = new ArrayList<>();

        // Iterate over the input locations and check if each one is on the road
        for (StationaryHazard hazard: hazards) {
            Location location = hazard.getLocation();
            Coordinate coordinate = new Coordinate(location.getLongitude().doubleValue(), location.getLatitude().doubleValue());
            Point point = geometryFactory.createPoint(coordinate);
            if (lineString.isWithinDistance(point, 0.0001)) { // adjust tolerance as needed
                hazardsOnRoad.add(hazard);
            }
        }

        // Return the list of locations on the road
        return hazardsOnRoad;
    }

    @Override

    public Collection<StationaryHazardDTO> view_hazards() {
        Collection<StationaryHazardDTO> list_to_return = new ArrayList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()){
            list_to_return.add(stationaryHazard.getDTO());
        }
        return list_to_return;
    }

    @Override
    public void report_hazard(int hazardId) throws Exception {
        StationaryHazard stationaryHazard = hazardRepository.getHazardById(hazardId);
        this.getReporterAdapter().report(stationaryHazard);
        hazardRepository.setReportOnHazard(hazardId);

    }

    @Override
    public List<Integer> auto_report() {
        List<Integer> ids = new ArrayList<>();
        for (StationaryHazard stationaryHazard : hazardRepository.getAllHazards()){
            if (stationaryHazard.getRate() > HAZARD_THRESHOLD_RATE && !stationaryHazard.isReport()){
                try
                {
                    int id = stationaryHazard.getId();
                    this.report_hazard(id);
                    ids.add(id);
                }
                catch (Exception e)
                {

                }
            }
        }
        return ids;
    }




    public ReporterAdapter getReporterAdapter() {
        return reporterAdapter;
    }

    public void setHAZARD_THRESHOLD_RATE(double HAZARD_THRESHOLD_RATE) {
        this.HAZARD_THRESHOLD_RATE = HAZARD_THRESHOLD_RATE;
    }

    public boolean isDbEmpty() {
        return hazardRepository.isDbEmpty();
    }
}
