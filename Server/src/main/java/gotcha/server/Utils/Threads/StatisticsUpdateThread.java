package gotcha.server.Utils.Threads;


import gotcha.server.Utils.HttpUtility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static gotcha.server.Utils.HttpUtility.METHOD_GET;
import static gotcha.server.Utils.HttpUtility.METHOD_POST;
import static java.net.HttpURLConnection.HTTP_OK;


public class StatisticsUpdateThread implements Runnable {

    public StatisticsUpdateThread() {

    }

    /**
     * Requirement 6
     * this thread task is to connect the external services by http post requests, with different parameters.
     */
    @Override
    public void run() {
    }


}
