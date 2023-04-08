package gotcha.server.Domain.RidesModule;

public class SharpTurn extends RidingAction{
    private Double start_direction;
    private Double final_direction;
    public SharpTurn() {
    }
    public SharpTurn(Double start_direction, Double final_direction) {
        // TODO: 27/03/2023 : add super call 
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
