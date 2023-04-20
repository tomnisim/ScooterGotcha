package gotcha.server.Domain.ExternalService;

import gotcha.server.Domain.SafeRouteCalculatorModule.Route;
import org.springframework.stereotype.Component;


@Component

public class MapsAdapterRealTime extends MapsAdapter {
    @Override
    public boolean handshake()
    {
        String origin = "Toronto";
        String destination = "Montreal";
        try{
            Route test_route = super.get_route(origin, destination);
            return test_route.getJunctions().size() == 341;
        }
        catch (Exception e){
            return false;
        }
    }


}

