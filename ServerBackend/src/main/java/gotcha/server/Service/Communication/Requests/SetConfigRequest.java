package gotcha.server.Service.Communication.Requests;

import java.util.Map;

/**
 * LoginRequest is a data transfer object (DTO) that represents the
 * login request payload containing the email and password for
 * authentication.
 */

public class SetConfigRequest {
    private String systemEmail;
    private String systemEmailPassword;
    private String adminUserName;
    private String adminPassword;
    private int numberOfRoutes;
    private int minimumPasswordLength;
    private int maximumPasswordLength;
    private int minimumDistanceAlert;
    private int numberOfCoordinates;
    private String orYarukEmail;
    private double hazards_rate_threshold;
    private int hazards_time_to_report_in_minutes;
    private int statistics_time_to_update_in_minutes;
    private String alert;
    private double duration;

    // Default constructor (required for deserialization)
    public SetConfigRequest() {
    }

    public SetConfigRequest(String systemEmail, String systemEmailPassword, String adminUserName, String adminPassword, int numberOfRoutes, int minimumPasswordLength, int maximumPasswordLength, int minimumDistanceAlert, int numberOfCoordinates, String orYarukEmail, double hazards_rate_threshold, int hazards_time_to_report_in_minutes, int statistics_time_to_update_in_minutes, String alert, double duration) {
        this.systemEmail = systemEmail;
        this.systemEmailPassword = systemEmailPassword;
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;
        this.numberOfRoutes = numberOfRoutes;
        this.minimumPasswordLength = minimumPasswordLength;
        this.maximumPasswordLength = maximumPasswordLength;
        this.minimumDistanceAlert = minimumDistanceAlert;
        this.numberOfCoordinates = numberOfCoordinates;
        this.orYarukEmail = orYarukEmail;
        this.hazards_rate_threshold = hazards_rate_threshold;
        this.hazards_time_to_report_in_minutes = hazards_time_to_report_in_minutes;
        this.statistics_time_to_update_in_minutes = statistics_time_to_update_in_minutes;
        this.alert = alert;
        this.duration = duration;
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }

    public String getSystemEmailPassword() {
        return systemEmailPassword;
    }

    public void setSystemEmailPassword(String systemEmailPassword) {
        this.systemEmailPassword = systemEmailPassword;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public int getNumberOfRoutes() {
        return numberOfRoutes;
    }

    public void setNumberOfRoutes(int numberOfRoutes) {
        this.numberOfRoutes = numberOfRoutes;
    }

    public int getMinimumPasswordLength() {
        return minimumPasswordLength;
    }

    public void setMinimumPasswordLength(int minimumPasswordLength) {
        this.minimumPasswordLength = minimumPasswordLength;
    }

    public int getMaximumPasswordLength() {
        return maximumPasswordLength;
    }

    public void setMaximumPasswordLength(int maximumPasswordLength) {
        this.maximumPasswordLength = maximumPasswordLength;
    }

    public int getMinimumDistanceAlert() {
        return minimumDistanceAlert;
    }

    public void setMinimumDistanceAlert(int minimumDistanceAlert) {
        this.minimumDistanceAlert = minimumDistanceAlert;
    }

    public int getNumberOfCoordinates() {
        return numberOfCoordinates;
    }

    public void setNumberOfCoordinates(int numberOfCoordinates) {
        this.numberOfCoordinates = numberOfCoordinates;
    }

    public String getOrYarukEmail() {
        return orYarukEmail;
    }

    public void setOrYarukEmail(String orYarukEmail) {
        this.orYarukEmail = orYarukEmail;
    }

    public double getHazards_rate_threshold() {
        return hazards_rate_threshold;
    }

    public void setHazards_rate_threshold(double hazards_rate_threshold) {
        this.hazards_rate_threshold = hazards_rate_threshold;
    }

    public int getHazards_time_to_report_in_minutes() {
        return hazards_time_to_report_in_minutes;
    }

    public void setHazards_time_to_report_in_minutes(int hazards_time_to_report_in_minutes) {
        this.hazards_time_to_report_in_minutes = hazards_time_to_report_in_minutes;
    }

    public int getStatistics_time_to_update_in_minutes() {
        return statistics_time_to_update_in_minutes;
    }

    public void setStatistics_time_to_update_in_minutes(int statistics_time_to_update_in_minutes) {
        this.statistics_time_to_update_in_minutes = statistics_time_to_update_in_minutes;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    // Custom constructor



}

