package gotcha.server.Utils.Threads;


import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.s3.S3Service;
import gotcha.server.Utils.Logger.SystemLogger;

import java.util.List;

import static gotcha.server.Utils.PotholeWidthDetection.detect;


public class HazardsSizeCalculatorThread implements Runnable {
    private HazardController hazardController;
    private SystemLogger systemLogger;
    private S3Service s3Service;
    public HazardsSizeCalculatorThread(HazardController hazardController, SystemLogger systemLogger, S3Service s3Service) {
        this.hazardController = hazardController;
        this.systemLogger = systemLogger;
        this.s3Service = s3Service;
    }

    @Override
    public void run() {
        this.systemLogger.add_log("Start Update Hazards Size");
        List<StationaryHazard> hazards = this.hazardController.get_hazards();
        for (StationaryHazard hazard : hazards){
            if (hazard.getSize() <= 0){
                try
                {
                    byte[] frame = s3Service.getImage(hazard.getPhotoS3Key());
                    double[] sizes = detect(frame);
                    hazardController.update_hazard(hazard, sizes[0], frame);
                    this.systemLogger.add_log(String.format("Update Hazard with id : %s to Size: %s", hazard.getId(), hazard.getSize()));
                }
                catch (Exception e){
                    this.systemLogger.add_log(String.format("Failed Update Hazard with id : %s", hazard.getId()));
                    this.systemLogger.add_log(e.getMessage());
                }

            }
        }

    }


}
