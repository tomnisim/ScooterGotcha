package gotcha.server.ExternalService;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;

import java.util.List;

public interface MapsAdapter {
    boolean handshake();
    List<Route> get_routes(String source, String destination, int number_of_routes);

}
