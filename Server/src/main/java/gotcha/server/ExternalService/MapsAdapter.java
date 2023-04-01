package gotcha.server.ExternalService;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public abstract class MapsAdapter {
    final String mode = "bicycling";
    final String apiKey = "AIzaSyAlOyWPS8R9TY9lRqpejdUQvLKOPwDfwsE";


    public abstract boolean handshake();

    /**
     *
     * @param origin_destination after covert to Location object, location of rider
     * @param destination_input after covert to Location object
     * @param number_of_routes to return
     * @return list of size @number_of_routes from @origin to @destination
     */
    public List<Route> get_routes(String origin_destination, String destination_input, int number_of_routes) {
        List<Route> routes_list = new LinkedList<>();
        for (int i = 0; i < number_of_routes; i++){
            // TODO: 17/03/2023 : the routes are the same, read API and change it to [number_of_routes] different routes.
            Route temp = this.get_route(origin_destination, destination_input);
            routes_list.add(temp);
        }
        return routes_list;
    }

    /**
     * this method call google maps API
     * @param origin_destination after covert to Location object, location of rider
     * @param destination_input after covert to Location object
     * @return a route from @origin to @destination
     */
    protected Route get_route(String origin_destination, String destination_input) {
        Route route = new Route();
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&mode=%s&key=%s",
                    origin_destination, destination_input, mode, apiKey));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }


            in.close();
            // Parse the JSON response
            String jsonString = response.toString();
            JSONObject data = new JSONObject(jsonString);
            JSONArray routes = data.getJSONArray("routes");
            JSONObject ja =  routes.getJSONObject(0);
            JSONArray a = ja.getJSONArray("legs");
            JSONObject jo = a.getJSONObject(0);
            JSONArray steps = jo.getJSONArray("steps");

            for (int i = 0; i < steps.length(); i++) {
                JSONObject curr = steps.getJSONObject(i).getJSONObject("start_location");
                BigDecimal lng = (BigDecimal) curr.get("lng");
                BigDecimal lat = (BigDecimal) curr.get("lat");
                Location location = new Location(lng, lat);
                route.add_junction(location);
            }
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return route;
    }

}
