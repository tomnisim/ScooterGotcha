package gotcha.server.Domain.HazardsModule;

import gotcha.server.Utils.Location;

public class StationaryHazard {
    private int id;
    private int ride_id;
    private Location location;
    private String city;
    private HazardType type;
    private double size;

    public StationaryHazard(int id, int ride_id, Location location, String city, HazardType type, double size) {
        this.id = id;
        this.ride_id = ride_id;
        this.location = location;
        this.city = city;
        this.type = type;
        this.size = size;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getRide_id() {
        return ride_id;
    }
    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public HazardType getType() {
        return type;
    }
    public void setType(HazardType type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }


}
