package gotcha.server.Domain.SafeRouteCalculatorModule;

import gotcha.server.Utils.Location;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Route {
    private List<Location> junctions;
    private int number_of_junctions;
    private int distance; // Meters
    private int duration; // Minutes
    public Route()
    {
        this.junctions = new LinkedList<Location>();
        this.number_of_junctions = 0;
        this.distance = 0;
        this.duration = 0;
    }

    public Route(List<Location> junctions, int distance, int duration){
        this.junctions = junctions;
        this.number_of_junctions = junctions.size();
        this.duration = duration;
        this.distance = distance;
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
}
