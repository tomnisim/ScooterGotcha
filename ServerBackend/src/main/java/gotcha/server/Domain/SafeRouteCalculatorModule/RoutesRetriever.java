package gotcha.server.Domain.SafeRouteCalculatorModule;

import gotcha.server.Config.Configuration;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.ExternalService.MapsAdapter;
import gotcha.server.Domain.ExternalService.MapsAdapterImpl;
import gotcha.server.Domain.ExternalService.MapsAdapterRealTime;
import gotcha.server.Utils.Cities;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static gotcha.server.Utils.AddressConverter.convertToEnglish;
import static gotcha.server.Utils.Cities.city_permutation;

@Component
public class RoutesRetriever {
    private final MapsAdapter google_maps;
    private final HazardController hazard_controller;
    private final Configuration configuration;

    @Autowired
    public RoutesRetriever(HazardController hazardController, Configuration configuration)
    {
        this.configuration = configuration;
        this.hazard_controller = hazardController;
        if (configuration.getMapsAdapter().equals("develop"))
        {
            this.google_maps = new MapsAdapterImpl();
        }
        else
        {
            this.google_maps = new MapsAdapterRealTime();
        }

    }

    /**
     *
     * @param source - location of rider
     * @param destination - location to navigate.
     * @return List of routs sort by safe rate - index 0 for safest route
     */
    public List<Route> fetch_safe_routes(String source, String destination) throws IOException {
        List<Route> routes = this.google_maps.get_routes(source, destination, configuration.getNumberOfRoutes());
        return this.rate_routes(routes);
    }

    /**
     * @param routes from origin to dest.
     * @return sorted list of routes by acceding hazards rate order.
     */
    private List<Route> rate_routes(List<Route> routes) {

        Hashtable<Double, Route> routes_by_rating = new Hashtable<Double, Route>();
        for (Route route : routes)
        {
            double rate = 0.0;
            List<StationaryHazard> hazards_in_route = this.hazard_controller.get_hazards_in_route(route);
            rate = 0.0;
            for (StationaryHazard hazard : hazards_in_route)
            {
                rate += hazard.getRate();
            }
            routes_by_rating.put(rate, route);
            route.setHazardsInRoute(hazards_in_route);
        }
        List<Double> ratings = Collections.list(routes_by_rating.keys());
        Collections.sort(ratings);
        List<Route> sorted_routes_by_safety = new ArrayList<Route>();
        for (Double rate: ratings)
        {
            sorted_routes_by_safety.add(routes_by_rating.get(rate));
        }
        return sorted_routes_by_safety;
    }

    public String[] getAddresses(LocationDTO origin, LocationDTO destination) {
        String[] answer = new String[2];
        try{
            answer[0] = this.google_maps.locationToAddress(new Location(origin));
            answer[1] = this.google_maps.locationToAddress(new Location(destination));
            //answer[0] = convertToEnglish(answer[0]);
           // answer[1] = convertToEnglish(answer[1]);
        }
        catch (Exception e){
            answer[0] = "";
            answer[1] = "";
            System.out.println(e.getMessage());
        }
        return answer;
    }

    public String getCity(LocationDTO origin){
        // TODO: 01/05/2023 : Hebrew / English 
        String answer;
        try{
            answer =  this.google_maps.locationToCity(new Location(origin));
            answer = city_permutation(answer);
            //answer = convertToEnglish(answer);
        }
        catch (Exception e){
            answer = "default";
        }
        return answer;

    }


}
