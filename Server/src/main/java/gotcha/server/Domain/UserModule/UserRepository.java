package gotcha.server.Domain.UserModule;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<String, User> allUsers;
    private Map<String, String> usersEmailByRaspberryPi;

    public UserRepository() {
        this.allUsers = new ConcurrentHashMap<>();
        this.usersEmailByRaspberryPi = new ConcurrentHashMap<>();
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(this.allUsers.values());
    }

    public User getUserByEmail(String userEmail) {
        return allUsers.getOrDefault(userEmail, null);
    }

    public User addUser(User user) {
        return allUsers.putIfAbsent(user.get_email(), user);
    }

    public void removeUser(String userEmail) throws Exception {
        var result = allUsers.remove(userEmail);
        if (result == null)
            throw new Exception("user with email:" + userEmail + " not found");
    }

    public String assignRpToUser(String raspberryPiSerialNumber, String userEmail) {
        return usersEmailByRaspberryPi.putIfAbsent(raspberryPiSerialNumber, userEmail);
    }

    public User getUserByRpSerialNumber(String rpSerialNumber) {
        var userEmail = usersEmailByRaspberryPi.getOrDefault(rpSerialNumber, null);
        return allUsers.getOrDefault(userEmail,null);
    }
}
