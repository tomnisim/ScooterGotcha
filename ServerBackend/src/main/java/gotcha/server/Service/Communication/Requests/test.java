package gotcha.server.Service.Communication.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.HazardsModule.StationaryHazardRPDTO;
import gotcha.server.Domain.RidesModule.RidingAction;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;

import java.time.LocalDateTime;
import java.util.List;

public class test {
    private byte[] frame;


    // Default Constructor

    public test() {}
    public test(byte[] frame) {
        this.frame =frame;
    }


    public byte[] getFrame() {
        return frame;
    }

    public void setFrame(byte[] frame) {
        this.frame = frame;
    }
}
