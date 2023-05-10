package gotcha.server.Domain.UserModule;

import javax.persistence.*;

@Entity
@Table(name = "availableRaspberryPiSerials")
public class RaspberryPiSerial {
    @Id
    @Column(unique = true)
    private String serial;

    public RaspberryPiSerial(String serial) {
        this.serial = serial;
    }

    public RaspberryPiSerial(){}

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
