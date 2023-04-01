package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDate;
import java.time.LocalTime;

public class Brake extends RidingAction{
    private Double start_speed;
    private Double final_speed;

    public Brake(LocalDate date, LocalTime time, Location location, Double start_speed, Double final_speed) {
        super(date, time, location);
        this.start_speed = start_speed;
        this.final_speed = final_speed;
    }


    // ------------------------------------------ Getters & Setters ----------------------------------------------------------

    public Double getStart_speed() {
        return start_speed;
    }

    public void setStart_speed(Double start_speed) {
        this.start_speed = start_speed;
    }

    public Double getFinal_speed() {
        return final_speed;
    }

    public void setFinal_speed(Double final_speed) {
        this.final_speed = final_speed;
    }
}
