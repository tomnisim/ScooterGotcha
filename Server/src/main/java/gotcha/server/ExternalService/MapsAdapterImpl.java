package gotcha.server.ExternalService;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;

import java.util.List;

public class MapsAdapterImpl implements MapsAdapter {
    @Override
    public boolean handshake() {
        return false;
    }

    @Override
    public List<Route> get_routes(Location source, Location destination, int number_of_routes) {
        return null;
    }
}
