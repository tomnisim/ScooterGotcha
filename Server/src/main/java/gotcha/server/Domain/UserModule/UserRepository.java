package gotcha.server.Domain.UserModule;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<String, User> allUsers;

    public UserRepository() {
        this.allUsers = new ConcurrentHashMap<>();
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(this.allUsers.values());
    }

    public User getUser(String userEmail) {
        return allUsers.getOrDefault(userEmail, null);
    }

    public void addUser(User user) {
        allUsers.put(user.get_email(), user);
    }

    public void removeUser(String userEmail) throws Exception {
        if (!allUsers.containsKey(userEmail))
            throw new Exception("user with email:" + userEmail + " not found");

        allUsers.remove(userEmail);
    }
}
