package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sidewalk extends RidingAction {

    public Sidewalk(LocalDate date, LocalTime time, Location location){
        super(date, time, location);
    }
}
