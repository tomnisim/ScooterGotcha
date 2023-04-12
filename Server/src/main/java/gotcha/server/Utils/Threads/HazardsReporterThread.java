package gotcha.server.Utils.Threads;


import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.StatisticsModule.StatisticsManager;
import gotcha.server.Utils.Logger.SystemLogger;

import java.time.LocalDate;
import java.util.List;


public class HazardsReporterThread implements Runnable {
    private HazardController hazardController;
    private SystemLogger systemLogger;
    public HazardsReporterThread(HazardController hazardController, SystemLogger systemLogger) {
        this.hazardController = hazardController;
        this.systemLogger = systemLogger;
    }

    @Override
    public void run() {
        List<Integer> ids_list = this.hazardController.auto_report();
        this.systemLogger.add_log(String.format("Report hazards with ids : %s", ids_list.toString()));
    }


}
