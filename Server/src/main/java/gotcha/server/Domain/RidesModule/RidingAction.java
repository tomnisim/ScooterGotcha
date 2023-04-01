package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class RidingAction {
    private LocalDate date;
    private LocalTime time;
    private Location location;

    public RidingAction(LocalDate date, LocalTime time, Location location){
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
