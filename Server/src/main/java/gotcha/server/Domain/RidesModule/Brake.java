package gotcha.server.Domain.RidesModule;

public class Brake extends RidingAction{
    private Double start_speed;
    private Double final_speed;

    public Brake() {

    }

    public Brake(Double start_speed, Double final_speed) {
        // TODO: 27/03/2023 : add super call 
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
