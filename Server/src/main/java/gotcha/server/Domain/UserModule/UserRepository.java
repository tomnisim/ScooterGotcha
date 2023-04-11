package gotcha.server.Domain.UserModule;

import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<String, User> allUsers;
    private final IUserRepository usersJpaRepositry;
    private Map<String, String> usersEmailByRaspberryPi;

    public UserRepository(IUserRepository usersJpaRepositry) {
        this.allUsers = new ConcurrentHashMap<>();
        this.usersEmailByRaspberryPi = new ConcurrentHashMap<>();
        this.usersJpaRepositry = usersJpaRepositry;
        LoadFromDb();
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(this.allUsers.values());
    }

    public User getUserByEmail(String userEmail) throws UserNotFoundException {
        var result = allUsers.get(userEmail);
        if (result == null) {
            return getUserFromDb(userEmail);
        }
        return result;
    }

    public User getUserFromDb(String userEmail) throws UserNotFoundException {
        var result = usersJpaRepositry.findById(userEmail);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new UserNotFoundException("user with email:" + userEmail + " not found");
        }
    }

    public User addUser(User user) {
        return allUsers.putIfAbsent(user.get_email(), user);
    }

    public void removeUser(String userEmail) throws Exception {
        var result = allUsers.remove(userEmail);
        if (result == null)
            throw new Exception("user with email:" + userEmail + " not found");

        usersJpaRepositry.save(result);
    }

    public String assignRpToUser(String raspberryPiSerialNumber, String userEmail) {
        return usersEmailByRaspberryPi.putIfAbsent(raspberryPiSerialNumber, userEmail);
    }

    public User getUserByRpSerialNumber(String rpSerialNumber) {
        var userEmail = usersEmailByRaspberryPi.getOrDefault(rpSerialNumber, "");
        return allUsers.getOrDefault(userEmail,null);
    }

    public void LoadFromDb() {
        var usersInDb = usersJpaRepositry.findAll();
        for(var user : usersInDb) {
            allUsers.put(user.get_email(), user);
        }
    }
}
