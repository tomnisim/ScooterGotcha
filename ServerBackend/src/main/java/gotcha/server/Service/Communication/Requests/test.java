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
    private byte[] data;
    public byte[] getData() {
        return data;
    }



    // Default Constructor

    public test() {}
    public test(byte[] data) {
        this.data = data;
    }

    public void setData(byte [] data) {
        this.data = data;
    }
}
