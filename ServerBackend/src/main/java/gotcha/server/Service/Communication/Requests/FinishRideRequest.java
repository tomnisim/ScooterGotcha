package gotcha.server.Service.Communication.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.HazardsModule.StationaryHazardRPDTO;
import gotcha.server.Domain.RidesModule.RidingAction;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;

import java.time.LocalDateTime;
import java.util.List;

public class FinishRideRequest {
    private String rpSerialNumber;
    private LocationDTO origin;
    private LocationDTO destination;
    @JsonFormat(pattern="yyyy-MM-dd;HH:mm", timezone="UTC")
    private LocalDateTime startTime;
    @JsonFormat(pattern="yyyy-MM-dd;HH:mm", timezone="UTC")
    private LocalDateTime endTime;
    List<StationaryHazardRPDTO> hazards;
    List<RidingAction> ridingActions;
    List<LocationDTO> junctions;


    // Default Constructor

    public FinishRideRequest() {}
    public FinishRideRequest(String rpSerialNumber, LocationDTO origin, LocationDTO destination,
                             LocalDateTime startTime, LocalDateTime endTime, List<StationaryHazardRPDTO> hazards,
                             List<RidingAction> ridingActions, List<LocationDTO> junctions) {
        this.rpSerialNumber = rpSerialNumber;
        this.origin = origin;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hazards = hazards;
        this.ridingActions = ridingActions;
        this.junctions = junctions;
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

    public LocationDTO getOrigin() {
        return origin;
    }

    public void setOrigin(LocationDTO origin) {
        this.origin = origin;
    }

    public LocationDTO getDestination() {
        return destination;
    }

    public void setDestination(LocationDTO destination) {
        this.destination = destination;
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

    public List<StationaryHazardRPDTO> getHazards() {
        return hazards;
    }

    public void setHazards(List<StationaryHazardRPDTO> hazards) {
        this.hazards = hazards;
    }

    public List<LocationDTO> getJunctions() {
        return junctions;
    }

    public void setJunctions(List<LocationDTO> junctions) {
        this.junctions = junctions;
    }
}
