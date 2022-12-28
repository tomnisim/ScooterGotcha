package gotcha.server.ExternalService;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;

import java.util.List;

public interface MapsAdapter {
    boolean handshake();
    List<Route> get_routes(Location source, Location destination, int number_of_routes);

}
