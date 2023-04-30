package gotcha.server.Domain.UserModule;

import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AvailableRaspberryPiSerialsRepository {
    private HashMap<String,RaspberryPiSerial> availableSerials;
    private IAvailableRaspberryPiSerialsRepository serialsJpaRepositry;

    public AvailableRaspberryPiSerialsRepository(IAvailableRaspberryPiSerialsRepository serialsJpaRepositry) {
        this.serialsJpaRepositry = serialsJpaRepositry;
        this.availableSerials = new HashMap<>();
        LoadFromDb();
    }

    public HashSet<String> getAllSerials() {
        return new HashSet<>(availableSerials.keySet());
    }

    private String getSerialFromDb(String serialNumber) throws UserNotFoundException {
        var result = serialsJpaRepositry.findById(serialNumber);
        if (result.isPresent()) {
            return result.get().getSerial();
        }
        else {
            throw new UserNotFoundException("serial:" + serialNumber + " not found");
        }
    }

    public RaspberryPiSerial addSerial(String serial) {
        var newSerial = new RaspberryPiSerial(serial);
        serialsJpaRepositry.save(newSerial);
        return availableSerials.putIfAbsent(serial,newSerial);
    }

    public void removeSerial(String serial) throws Exception {
        var result = availableSerials.remove(serial);
        if (result == null)
            throw new Exception("serial:" + serial + " not found");

        serialsJpaRepositry.delete(result);
    }

    public boolean isExists(String serial) {
        return availableSerials.containsKey(serial);
    }

    private void LoadFromDb() {
        var allSerials = serialsJpaRepositry.findAll();
        for(var serial : allSerials) {
            availableSerials.put(serial.getSerial(), serial);
        }
    }

    public boolean isDbEmpty() {
        return serialsJpaRepositry.count() == 0;
    }
}
