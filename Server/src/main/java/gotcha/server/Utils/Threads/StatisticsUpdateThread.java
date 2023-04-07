package gotcha.server.Utils.Threads;


import gotcha.server.Domain.StatisticsModule.StatisticsManager;
import gotcha.server.Utils.HttpUtility;
import gotcha.server.Utils.Logger.SystemLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static gotcha.server.Utils.HttpUtility.METHOD_GET;
import static gotcha.server.Utils.HttpUtility.METHOD_POST;
import static java.net.HttpURLConnection.HTTP_OK;


public class StatisticsUpdateThread implements Runnable {
    private StatisticsManager statisticsManager;
    private SystemLogger systemLogger;
    public StatisticsUpdateThread(StatisticsManager statisticsManager, SystemLogger systemLogger) {
        this.statisticsManager = statisticsManager;
        this.systemLogger = systemLogger;
    }

    @Override
    public void run() {
        this.statisticsManager.update_daily_statistic();
        this.systemLogger.add_log("Update Statistics for date :" + LocalDate.now().toString());
    }


}
