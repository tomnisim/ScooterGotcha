package gotcha.server.SafeRouteCalculatorModule;

import gotcha.server.Utils.Location;

public class Route {
    private Location[] junctions;
    private Location start_junction;
    private Location finish_junction;
    public Route()
    {

    }
    public Route(Location start_junction, Location finish_junction, Location[] junctions)
    {
        this.start_junction = start_junction;
        this.finish_junction = finish_junction;
        this.junctions = junctions;
    }

}
