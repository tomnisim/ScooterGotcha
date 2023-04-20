package gotcha.server.Domain.RidesModule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gotcha.server.Utils.Location;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actionType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpeedChange.class, name = "BRAKE"),
        @JsonSubTypes.Type(value = SharpTurn.class, name = "SHARP_TURN")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "action_type")
public abstract class RidingAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="time")
    private LocalDateTime time;
    @Embedded
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
