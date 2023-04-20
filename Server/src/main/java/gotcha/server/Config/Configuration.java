package gotcha.server.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix="gotcha")
public class Configuration {
    private boolean firstTimeRunning;
    private String externalServiceMode;
    private String databaseMode;
    private String databaseUrl;
    private String databasePassword;
    private String systemEmail;
    private String systemEmailPassword;
    private String adminUserName;
    private String adminPassword;
    private String mapsAdapter;
    private int numberOfRoutes;
    private int minimumPasswordLength;
    private int maximumPasswordLength;
    private int minimumDistanceAlert;
    private int numberOfCoordinates;
    private String orYarukEmail;
    private Map<String, String> cities_emails;
    private double hazards_rate_threshold;
    private int hazards_time_to_report_in_minutes;
    private int statistics_time_to_update_in_minutes;

    private Map<String, ServerConfiguration> server;

    // <editor-fold desc="Accessor Methods">
    public boolean getFirstTimeRunning() {
        return firstTimeRunning;
    }

    public void setFirstTimeRunning(boolean firstTimeRunning) {
        this.firstTimeRunning = firstTimeRunning;
    }

    public String getExternalServiceMode() {
        return externalServiceMode;
    }

    public void setExternalServiceMode(String externalServiceMode) {
        this.externalServiceMode = externalServiceMode;
    }

    public String getDatabaseMode() {
        return databaseMode;
    }

    public void setDatabaseMode(String databaseMode) {
        this.databaseMode = databaseMode;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
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

    public String getMapsAdapter(){
        return mapsAdapter;
    }

    public void setMapsAdapter(String mapsAdapter){
        this.mapsAdapter = mapsAdapter;
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

    public Map<String, ServerConfiguration> getServer() {
        return server;
    }
    public boolean isFirstTimeRunning() {
        return firstTimeRunning;
    }

    public String getOrYarukEmail() {
        return orYarukEmail;
    }

    public void setOrYarukEmail(String orYarukEmail) {
        this.orYarukEmail = orYarukEmail;
    }

    public Map<String, String> getCities_emails() {
        return cities_emails;
    }

    public void setCities_emails(Map<String, String> cities_emails) {
        this.cities_emails = cities_emails;
    }
    public void setServer(Map<String, ServerConfiguration> server) {
        this.server = server;
    }
    // </editor-fold>


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
}
