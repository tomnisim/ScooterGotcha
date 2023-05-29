package gotcha.server.Utils.Threads;


import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Logger.SystemLogger;

import java.util.List;

import static gotcha.server.Utils.PotholeWidthDetection.detect;


public class HazardsSizeCalculatorThread implements Runnable {
    private HazardController hazardController;
    private SystemLogger systemLogger;
    public HazardsSizeCalculatorThread(HazardController hazardController, SystemLogger systemLogger) {
        this.hazardController = hazardController;
        this.systemLogger = systemLogger;
    }

    @Override
    public void run() {
        List<StationaryHazard> hazards = this.hazardController.get_hazards();
        for (StationaryHazard hazard : hazards){
            hazard.setSize(detect(hazard.getPhotoS3Key()));
        }
        this.systemLogger.add_log(String.format("Report hazards with ids");
    }


}
