package gotcha.server.Domain.RidesModule;

public class SharpTurn {
    private Double start_direction;
    private Double final_direction;
    public SharpTurn() {
    }
    public SharpTurn(Double start_direction, Double final_direction) {
        this.start_direction = start_direction;
        this.final_direction = final_direction;
    }



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
