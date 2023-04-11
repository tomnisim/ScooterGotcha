package gotcha.server.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "rate-config")
public class RiderRatingConfiguration {
    private Map<Integer, Integer> speed_changes_per_minute;
    private Map<Integer, Integer> brakes_per_minute;
    private Map<Integer, Integer> sharp_turns_per_minute;
    private Map<Integer, Integer> alerts_per_minute;

    public RiderRatingConfiguration(){}

    public Map<Integer, Integer> getSpeed_changes_per_minute() {
        return speed_changes_per_minute;
    }

    public void setSpeed_changes_per_minute(Map<Integer, Integer> speed_changes_per_minute) {
        this.speed_changes_per_minute = speed_changes_per_minute;
    }

    public Map<Integer, Integer> getBrakes_per_minute() {
        return brakes_per_minute;
    }

    public void setBrakes_per_minute(Map<Integer, Integer> brakes_per_minute) {
        this.brakes_per_minute = brakes_per_minute;
    }

    public Map<Integer, Integer> getSharp_turns_per_minute() {
        return sharp_turns_per_minute;
    }

    public void setSharp_turns_per_minute(Map<Integer, Integer> sharp_turns_per_minute) {
        this.sharp_turns_per_minute = sharp_turns_per_minute;
    }

    public Map<Integer, Integer> getAlerts_per_minute() {
        return alerts_per_minute;
    }

    public void setAlerts_per_minute(Map<Integer, Integer> alerts_per_minute) {
        this.alerts_per_minute = alerts_per_minute;
    }

    public Map<Integer, Integer> getOn_sidewalk() {
        return on_sidewalk;
    }

    public void setOn_sidewalk(Map<Integer, Integer> on_sidewalk) {
        this.on_sidewalk = on_sidewalk;
    }

    private Map<Integer, Integer> on_sidewalk;
}
