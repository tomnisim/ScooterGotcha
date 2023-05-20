package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@DiscriminatorValue("SharpTurn")
public class SharpTurn extends RidingAction{

    @Column(name="startDirection", nullable = false)
    private Double start_direction;

    @Column(name="finalDirection", nullable = false)
    private Double final_direction;
    public SharpTurn() {
    }
    public SharpTurn(LocalDateTime time, Location location, Double start_direction, Double final_direction) {
        super(time,location);
        this.start_direction = start_direction;
        this.final_direction = final_direction;
    }

    // ------------------------------------------ Getters & Setters ----------------------------------------------------------


    public Double getStart_direction() {
        return start_direction;
    }

    public void setStart_direction(Double start_direction) {
        this.start_direction = start_direction;
    }

    public Double getFinal_direction() {
        return final_direction;
    }

    public void setFinal_direction(Double final_direction) {
        this.final_direction = final_direction;
    }
}
