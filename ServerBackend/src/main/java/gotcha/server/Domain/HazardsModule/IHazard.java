package gotcha.server.Domain.HazardsModule;

import gotcha.server.Utils.Location;

public interface IHazard {
    void setPhoto(byte[] photo);

    byte[] getPhoto();

    int getId();

    void setId(int id);

    int getRide_id();

    void setRide_id(int ride_id);

    Location getLocation();

    void setLocation(Location location);

    String getCity();

    void setCity(String city);

    HazardType getType();

    void setType(HazardType type);

    double getSize();

    void setSize(double size);

    double getRate();

    void calculateRate();

    void setRate(double rate);

    boolean isReport();

    void setReport(boolean report);

    String getPhotoS3Key();

    void setPhotoS3Key(String photoS3Key);
}
