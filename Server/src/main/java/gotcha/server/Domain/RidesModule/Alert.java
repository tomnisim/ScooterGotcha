package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDate;
import java.time.LocalTime;

public class Alert extends RidingAction {
    public Alert(LocalDate date, LocalTime time, Location location){
        super(date, time, location);
    }
}

