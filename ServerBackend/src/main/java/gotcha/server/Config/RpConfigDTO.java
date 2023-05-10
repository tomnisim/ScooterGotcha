package gotcha.server.Config;

import java.util.Map;

public class RpConfigDTO {
    private double alert_duration;
    private String alert_type;
    private int minimum_distance_to_alert;
    private int number_of_coordinates;
    private String host;
    private String port;
    private String protocol;

    public RpConfigDTO(Configuration configuration){
        this.number_of_coordinates = configuration.getNumberOfCoordinates();
        this.minimum_distance_to_alert = configuration.getMinimumDistanceAlert();
        this.alert_type = configuration.getAlert();
        this.alert_duration = configuration.getDuration();
        Map<String, ServerConfiguration> temp = configuration.getServer();
        ServerConfiguration serverConfiguration = temp.get("default");
        this.host = serverConfiguration.getHost();
        this.port = serverConfiguration.getPort();
        this.protocol = serverConfiguration.getProtocol();
    }

    public double getAlert_duration() {
        return alert_duration;
    }

    public void setAlert_duration(double alert_duration) {
        this.alert_duration = alert_duration;
    }

    public String getAlert_type() {
        return alert_type;
    }

    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    public int getMinimum_distance_to_alert() {
        return minimum_distance_to_alert;
    }

    public void setMinimum_distance_to_alert(int minimum_distance_to_alert) {
        this.minimum_distance_to_alert = minimum_distance_to_alert;
    }

    public int getNumber_of_coordinates() {
        return number_of_coordinates;
    }

    public void setNumber_of_coordinates(int number_of_coordinates) {
        this.number_of_coordinates = number_of_coordinates;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
