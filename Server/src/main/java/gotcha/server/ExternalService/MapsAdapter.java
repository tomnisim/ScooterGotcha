package gotcha.server.ExternalService;

import gotcha.server.Utils.Location;

import java.util.List;

public interface MapsAdapter {
    boolean handshake();
    // TODO: 30/12/2022 : remove // after open class "Route"
//    List<Route> get_routes(Location source, Location destination, int number_of_routes);

}
