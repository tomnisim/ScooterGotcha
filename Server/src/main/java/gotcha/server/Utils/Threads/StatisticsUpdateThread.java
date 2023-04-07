package gotcha.server.Utils.Threads;


import gotcha.server.Domain.StatisticsModule.StatisticsManager;
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
    private StatisticsManager statisticsManager;
    public StatisticsUpdateThread(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
    }

    @Override
    public void run() {
        this.statisticsManager.update_daily_statistic();
    }


}
