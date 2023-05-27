package gotcha.server.Domain.ExternalService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gotcha.server.Domain.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public abstract class MapsAdapter {
    final String mode = "bicycling";
    final String apiKey = "AIzaSyAlOyWPS8R9TY9lRqpejdUQvLKOPwDfwsE";

    public MapsAdapter(){}


    public abstract boolean handshake();

    /**
     *
     * @param origin_input after covert to Location object, location of rider
     * @param destination_input after covert to Location object
     * @param number_of_routes to return
     * @return list of size @number_of_routes from @origin to @destination
     */
    public List<Route> get_routes(String origin_input, String destination_input, int number_of_routes) throws IOException {
        List<Route> routes_list = new LinkedList<>();
        for (int i = 0; i < number_of_routes; i++){
            Route temp = this.get_route(origin_input, destination_input);
            routes_list.add(temp);
        }
        return routes_list;
    }

    /**
     * this method call Google Maps API
     * @param origin_address before covert to Location object, location of rider
     * @param destination_address before covert to Location object
     * @return a route from @origin to @destination
     */
    protected Route get_route(String origin_address, String destination_address) throws IOException {
        String origin_input = this.covert_address(origin_address);
        String destination_input = this.covert_address(destination_address);

        Route route = new Route();
        StringBuffer response = new StringBuffer();

        URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&mode=%s&key=%s",
                origin_input, destination_input, mode, apiKey));
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
        int duration = 0; // Minutes
        int distance = 0; // Meters
        for (int i = 0; i < steps.length(); i++) {
            JSONObject curr = steps.getJSONObject(i).getJSONObject("start_location");
            String lng_str = curr.get("lng").toString();
            String lat_str = curr.get("lat").toString();
            BigDecimal lng = new BigDecimal(lng_str);
            BigDecimal lat = new BigDecimal(lat_str);
            Location location = new Location(lng, lat);
            route.add_junction(location);
            distance = distance + (Integer)steps.getJSONObject(i).getJSONObject("distance").get("value");
            duration = duration + (Integer)steps.getJSONObject(i).getJSONObject("duration").get("value");
        }
        route.setDistance(distance);
        route.setDuration(duration);
        route.setOriginAddress(origin_address);
        route.setDestinationAddress(destination_address);
    return route;
    }


    public String locationToAddress1(Location location){
        double lat = location.getLatitude().doubleValue();
        double lng = location.getLongitude().doubleValue();
        // Build the URL for the Geocoding API request
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s", lat, lng, apiKey);

        // Send the Geocoding API request and read the response
        String response = "";
        try {
            URL requestUrl = new URL(url);
            Scanner scanner = new Scanner(requestUrl.openStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Parse the response as JSON and extract the formatted address
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.getJSONArray("results");
        String address = results.getJSONObject(0).getString("formatted_address");

        return address;
    }

    public String locationToAddress(Location location) throws IOException {
        double lat = location.getLatitude().doubleValue();
        double lng = location.getLongitude().doubleValue();
        String NOMINATIM_ENDPOINT = "https://nominatim.openstreetmap.org/reverse";
        String url = NOMINATIM_ENDPOINT + "?format=jsonv2&lat=" + lat + "&lon=" + lng;
        String json = getJSONFromURL(url);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        String address = "";

        if (rootNode.has("address")) {
            JsonNode addressNode = rootNode.get("address");

            if (addressNode.has("road")) {
                address += addressNode.get("road").asText();
            }
            if (addressNode.has("house_number")) {
                address += " " + addressNode.get("house_number").asText();
            }
            if (addressNode.has("city")) {
                address += ", " + addressNode.get("city").asText();
            }
            if (addressNode.has("state")) {
                address += ", " + addressNode.get("state").asText();
            }
            if (addressNode.has("country")) {
                address += ", " + addressNode.get("country").asText();
            }
        }
        return address;
        //return json.get("address").get("road").asText() + ", " + json.get("address").get("city").asText();
    }

    public String locationToCity(Location location) throws IOException {
        double lat = location.getLatitude().doubleValue();
        double lng = location.getLongitude().doubleValue();
        String NOMINATIM_ENDPOINT = "https://nominatim.openstreetmap.org/reverse";
        String url = NOMINATIM_ENDPOINT + "?format=jsonv2&lat=" + lat + "&lon=" + lng;
        String json = getJSONFromURL(url);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        String address = "";
        if (rootNode.has("address")) {
            JsonNode addressNode = rootNode.get("address");
            if (addressNode.has("city")) {
                address += addressNode.get("city").asText();
            }
        }
        return address;
    }


    protected String covert_address(String address){
//        String Origin =  "1 Rothschild Boulevard, Tel Aviv-Yafo, Israel";
//        String Destination = "10 HaYarkon St, Tel Aviv-Yafo, Israel";
//        Origin = Origin.replaceAll(" ", "+");
        return address.replaceAll(" ", "+");
    }

    private static String getJSONFromURL(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        InputStream in = conn.getInputStream();
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");
        String json = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return json;
    }
}
