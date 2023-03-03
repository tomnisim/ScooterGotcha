package gotcha.server.ExternalService;

import gotcha.server.Utils.Location;

import java.net.http.HttpClient;
import java.util.List;

public class MapsAdapterImpl implements MapsAdapter {
    @Override
    public boolean handshake() {
        return true;
    }

    // TODO: 30/12/2022 : remove // after open class "Route"


//    public List<Route> get_routes(Location source, Location destination, int number_of_routes) {
//        HttpClient client = new HttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=YOUR_API_KEY")
//                .method("GET", body)
//                .build();
//        Response response = client.newCall(request).execute();
//        return null;
//    }
}
