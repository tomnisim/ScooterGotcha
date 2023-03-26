package gotcha.server.ExternalService;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component

public class MapsAdapterImpl implements MapsAdapter {
    @Override
    public boolean handshake() {
        return true;
    }





    public List<Route> get_routes(Location origin_destination, Location destination_input, int number_of_routes) {
        List<Route> routes_list = new LinkedList<>();
        for (int i = 0; i < number_of_routes; i++){
            // TODO: 17/03/2023 : the routes are the same, read API and change it to [number_of_routes] different routes.
            Route temp = this.get_route(origin_destination, destination_input);
            routes_list.add(temp);
        }
        return routes_list;
    }

    private Route get_route(Location origin_destination, Location destination_input) {
        Route route = new Route();
        String apiKey = "AIzaSyAlOyWPS8R9TY9lRqpejdUQvLKOPwDfwsE";
        // TODO: 18/03/2023 : Replace ORIGIN and DESTINATION with your desired locations
        String origin = "Toronto";
        String destination = "Montreal";
        final String mode = "bicycling";
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&mode=%s&key=%s", origin, destination, mode, apiKey));
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
                Object lng = curr.get("lng");
                Object lat = curr.get("lat");
                Location location = new Location((Double)lng, (Double)lat);
                route.add_junction(location);
            }
            System.out.println(response.toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return route;
    }
}
