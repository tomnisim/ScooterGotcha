package gotcha.server.Domain.SafeRouteCalculatorModule;

import gotcha.server.Utils.Location;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Route {
    private List<Location> junctions;
    private int number_of_junctions;
    public Route()
    {
        this.junctions = new LinkedList<Location>();
        this.number_of_junctions = 0;
    }
    public Route(Location start_junction, Location finish_junction, List<Location> junctions)
    {
        this.junctions = junctions;
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

}
