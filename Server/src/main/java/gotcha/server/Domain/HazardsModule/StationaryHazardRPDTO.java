package gotcha.server.Domain.HazardsModule;

import gotcha.server.Utils.LocationDTO;

/**
 * this DTO is Server -> Admin & Rider application.
 */

public class StationaryHazardRPDTO {
    private String type;
    private double size;
    private LocationDTO location;
    private byte[] frame;

    public StationaryHazardRPDTO(){}

    public StationaryHazardRPDTO(double size, LocationDTO location, String type, byte[] frame) {
        this.type = type;
        this.size = size;
        this.location = location;
        this.frame = frame;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public byte[] getFrame() {
        return frame;
    }

    public void setFrame(byte[] frame) {
        this.frame = frame;
    }
}
