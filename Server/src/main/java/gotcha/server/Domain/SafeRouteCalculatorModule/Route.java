package gotcha.server.Domain.SafeRouteCalculatorModule;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.HazardsModule.StationaryHazardDAO;
import gotcha.server.Utils.Location;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Route {
    private List<Location> junctions;
    private int number_of_junctions;
    private List<StationaryHazard> hazardsInRoute;
    private int distance; // Meters
    private int duration; // Minutes
    private String originAddress;
    private String destinationAddress;
    public Route()
    {
        this.junctions = new LinkedList<Location>();
        this.number_of_junctions = 0;
        this.distance = 0;
        this.duration = 0;
        this.hazardsInRoute = new ArrayList<>();
        this.originAddress = "";
        this.destinationAddress = "";
    }

    public Route(List<Location> junctions, int distance, int duration, List<StationaryHazard> hazardsInRoute, String originAddress, String destinationAddress){
        this.junctions = junctions;
        this.number_of_junctions = junctions.size();
        this.duration = duration;
        this.distance = distance;
        this.hazardsInRoute = hazardsInRoute;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
    }

    public int getNumber_of_junctions() {
        return number_of_junctions;
    }

    public List<Location> getJunctions() {
        return junctions;
    }

    public void add_junction(Location junction) {
        this.junctions.add(junction);
        this.number_of_junctions++;
    }

    public void setJunctions(List<Location> junctions) {
        this.junctions = junctions;
    }

    public void setNumber_of_junctions(int number_of_junctions) {
        this.number_of_junctions = number_of_junctions;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<StationaryHazard> getHazardsInRoute() {
        return hazardsInRoute;
    }

    public void setHazardsInRoute(List<StationaryHazard> hazardsInRoute) {
        this.hazardsInRoute = hazardsInRoute;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }


}
