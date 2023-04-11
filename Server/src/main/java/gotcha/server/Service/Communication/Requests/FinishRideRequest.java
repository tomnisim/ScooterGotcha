package gotcha.server.Service.Communication.Requests;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.RidesModule.RidingAction;
import gotcha.server.Utils.Location;

import java.time.LocalDateTime;
import java.util.List;

public class FinishRideRequest {
    private String rpSerialNumber;
    private Location origin;
    private Location destination;
    private String city;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    List<StationaryHazard> hazards;
    List<RidingAction> ridingActions;

    // Default Constructor

    public FinishRideRequest() {}
    public FinishRideRequest(String rpSerialNumber, Location origin, Location destination, String city, LocalDateTime startTime, LocalDateTime endTime, List<StationaryHazard> hazards, List<RidingAction> ridingActions) {
        this.rpSerialNumber = rpSerialNumber;
        this.origin = origin;
        this.destination = destination;
        this.city = city;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hazards = hazards;
        this.ridingActions = ridingActions;
    }


    public List<RidingAction> getRidingActions() {
        return ridingActions;
    }

    public void setRidingActions(List<RidingAction> ridingActions) {
        this.ridingActions = ridingActions;
    }

    public String getRpSerialNumber() {
        return rpSerialNumber;
    }

    public void setRpSerialNumber(String rpSerialNumber) {
        this.rpSerialNumber = rpSerialNumber;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<StationaryHazard> getHazards() {
        return hazards;
    }

    public void setHazards(List<StationaryHazard> hazards) {
        this.hazards = hazards;
    }
}
