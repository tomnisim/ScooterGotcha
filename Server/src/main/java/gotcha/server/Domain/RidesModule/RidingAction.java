package gotcha.server.Domain.RidesModule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gotcha.server.Utils.Location;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actionType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpeedChange.class, name = "BRAKE"),
        @JsonSubTypes.Type(value = SharpTurn.class, name = "SHARP_TURN")
})
public abstract class RidingAction {
    private LocalDateTime time;
    private Location location;

    public RidingAction(LocalDateTime time, Location location) {
        this.time = time;
        this.location = location;
    }

    public RidingAction() {
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
