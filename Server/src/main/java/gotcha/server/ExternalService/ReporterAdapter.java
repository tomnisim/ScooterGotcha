package gotcha.server.ExternalService;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Threads.SendEmailThread;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component

public class ReporterAdapter {
    public Map<String, String> cities_emails; // city is the key, email is the value.
    public String OR_YARUK_email;
    public String system_email;
    public String system_email_password;



    public ReporterAdapter(){
        this.cities_emails = new HashMap<>();
    }

    public void add_city(String city, String email){
        this.cities_emails.put(city, email);
    }

    public void remove_city(String city){
        this.cities_emails.remove(city);
    }
    public void setCities_emails(Map<String, String> cities_emails) {
        this.cities_emails = cities_emails;
    }
    public void setOR_YARUK_email(String OR_YARUK_email) {
        this.OR_YARUK_email = OR_YARUK_email;
    }

    public void setSystem_email(String system_email) {
        this.system_email = system_email;
    }

    public void setSystem_email_password(String system_email_password) {
        this.system_email_password = system_email_password;
    }

    public void report(StationaryHazard stationaryHazard) throws Exception {
        String city = stationaryHazard.getCity();
        if (!cities_emails.containsKey(city)){
            throw new Exception(String.format("The system not support report to this city : %s", city));
        }
        String location = stationaryHazard.getLocation().toString();
        String type = stationaryHazard.getType().toString();
        String size = String.valueOf(stationaryHazard.getSize());
        String photos = ""; // TODO: add photos in hazards.

        String city_message = this.build_message(city, location, type, size, photos, city);
        String or_yaruk_message = this.build_message(city, location, type, size, photos, "OR YARUK");

        String city_email = this.cities_emails.get(city);

        SendEmailThread cityEmailThread = new SendEmailThread(system_email, system_email_password, city_email, city_message);
        SendEmailThread orYarukEmailThread = new SendEmailThread(system_email, system_email_password, city_email, or_yaruk_message);
        Thread thread1 = new Thread(cityEmailThread);
        Thread thread2 = new Thread(orYarukEmailThread);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();


    }

    private String build_message(String city, String location, String type, String size, String photos, String client) {
        String messageBuilder = "Hello " + client + ",\n" +
                "Hazard Alert: " + type + " reported in " + location + ", " + city + ".\n" +
                "Size: " + size + "\n" +
                "Photos: " + photos + "\n" +
//        messageBuilder.delete(messageBuilder.length() - 2, messageBuilder.length()); // remove the last comma and space
                "Please take necessary precautions and stay safe!" +
                "Gotcha Team.";
        return messageBuilder;
    }


}
