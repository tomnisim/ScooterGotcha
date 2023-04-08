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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component

public class MapsAdapterImpl extends MapsAdapter {
    @Override
    public boolean handshake()
    {
        return true;
    }

}
