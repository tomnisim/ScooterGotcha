package gotcha.server.ExternalService;

import gotcha.server.SafeRouteCalculatorModule.Route;
import gotcha.server.Utils.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


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

