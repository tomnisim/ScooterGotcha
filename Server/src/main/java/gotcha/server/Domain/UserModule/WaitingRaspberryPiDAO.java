package gotcha.server.Domain.UserModule;

public class WaitingRaspberryPiDAO {
    private int id;
    private String rp_serial_number;

    public WaitingRaspberryPiDAO(){}
    WaitingRaspberryPiDAO(int id, String rp_serial_number){
        this.id = id;
        this.rp_serial_number = rp_serial_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRp_serial_number() {
        return rp_serial_number;
    }

    public void setRp_serial_number(String rp_serial_number) {
        this.rp_serial_number = rp_serial_number;
    }
}
