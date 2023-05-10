package gotcha.server.Domain.UserModule;
/**
 * this DAO is for ADMIN application.
 */
public class WaitingRaspberryPiDTO {
    private int id;
    private String rp_serial_number;

    public WaitingRaspberryPiDTO(){}
    WaitingRaspberryPiDTO(int id, String rp_serial_number){
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
