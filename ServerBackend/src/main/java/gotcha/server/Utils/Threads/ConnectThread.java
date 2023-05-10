package gotcha.server.Utils.Threads;


import gotcha.server.Utils.HttpUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import static gotcha.server.Utils.HttpUtility.METHOD_GET;
import static gotcha.server.Utils.HttpUtility.METHOD_POST;
import static java.net.HttpURLConnection.HTTP_OK;


public class ConnectThread implements Runnable {
    private String web_url;
    private int method;
    private HashMap<String, String> params;
    private HttpUtility.Callback callback;
    final String[] answer = new String[1];

    public ConnectThread(String web_url, int method, HashMap<String, String> params, HttpUtility.Callback callback) {
        this.web_url = web_url;
        this.method = method;
        this.params = params;
        this.callback = callback;
    }

    /**
     * Requirement 6
     * this thread task is to connect the external services by http post requests, with different parameters.
     */
    @Override
    public void run() {

        try {
            String url = web_url;
            // write GET params,append with url
            if (method == METHOD_GET && params != null) {
                for (Map.Entry < String, String > item: params.entrySet()) {
                    String key = URLEncoder.encode(item.getKey(), "UTF-8");
                    String value = URLEncoder.encode(item.getValue(), "UTF-8");
                    if (!url.contains("?")) {
                        url += "?" + key + "=" + value;
                    } else {
                        url += "&" + key + "=" + value;
                    }
                }
            }

            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // handle url encoded form data
            urlConnection.setRequestProperty("charset", "utf-8");
            if (method == METHOD_GET) {
                urlConnection.setRequestMethod("GET");
            } else if (method == METHOD_POST) {
                urlConnection.setDoOutput(true); // write POST params
                urlConnection.setRequestMethod("POST");
            }

            //write POST data
            if (method == METHOD_POST && params != null) {
                StringBuilder postData = new StringBuilder();
                for (Map.Entry < String, String > item: params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                urlConnection.getOutputStream().write(postDataBytes);

            }
            // server response code
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HTTP_OK && callback != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // callback success
                answer[0] = callback.OnSuccess(response.toString());
                reader.close(); // close BufferReader
            } else if (callback != null) {
                // callback error
                answer[0] = callback.OnError(responseCode, urlConnection.getResponseMessage());
            }

            urlConnection.disconnect(); // disconnect connection
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                // callback error
                answer[0] = callback.OnError(500, e.getLocalizedMessage());
            }
        }
    }

    /**
     * @return the answer we got from the external service.
     */
    public String get_value(){
        return this.answer[0];
    }

}
