package gotcha.server.SafeRouteCalculatorModule;

import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.ExternalService.MapsAdapterImpl;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Location;

import java.util.*;

public class RoutesRetriever {
    private Formula formula;
    private MapsAdapterImpl google_maps;
    private HazardController hazard_controller;
    public RoutesRetriever(Formula formula)
    {
        this.formula = formula;
        this.google_maps = new MapsAdapterImpl(); // TODO - fetch google maps class properly
        this.hazard_controller = HazardController.get_instance();

    }

    /**
     *
     * @param source
     * @param destination
     * @return List of routs sort by safe rate - index 0 for safest route
     */
    public List<Route> fetch_safe_routes(Location source, Location destination)
    {
        // TODO - add number_of_routes to utils , from Appendix A
        List<Route> routes = this.google_maps.get_routes(source, destination, number_of_routes); // TODO - API request to get routes
        List<Route> sorted_routes_by_safty = this.rate_routes(routes);
        return sorted_routes_by_safty; //TODO - maybe return the rating for each route to show it to the rider
    }

    private List<Route> rate_routes(List<Route> routes) {

        Hashtable<Double, Route> routes_by_rating = new Hashtable<Double, Route>();
        for (Route route : routes)
        {
            Double rate = 0.0;
            List<Double> hazards_rating = this.hazard_controller.get_hazard_ratings_in_route(route);
            rate = formula.evaluate(hazards_rating); // TODO - add avarage function to the formula, and apply it on the hazrd ratong list
            routes_by_rating.put(rate, route);
        }
        List<Double> ratings = Collections.list(routes_by_rating.keys());
        Collections.sort(ratings);
        List<Route> sorted_routes_by_safty = new ArrayList<Route>();
        for (Double rate: ratings)
        {
            sorted_routes_by_safty.add(routes_by_rating.get(rate));
        }
        return sorted_routes_by_safty;


    }
}
