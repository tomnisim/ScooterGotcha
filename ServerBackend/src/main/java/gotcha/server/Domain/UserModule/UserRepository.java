package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.RatingModule.UserRateCalculator;
import gotcha.server.Domain.RidesModule.Ride;
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

    private User getUserFromDb(String userEmail) throws UserNotFoundException {
        var result = usersJpaRepositry.findById(userEmail);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new UserNotFoundException("user with email:" + userEmail + " not found");
        }
    }

    public User addUser(User user) {
        usersJpaRepositry.save(user);
        return allUsers.putIfAbsent(user.get_email(), user);
    }

    public void removeUser(String userEmail) throws UserNotFoundException {
        var result = allUsers.remove(userEmail);
        if (result == null)
            throw new UserNotFoundException("user with email:" + userEmail + " not found");

        usersJpaRepositry.delete(result);
    }

    public String assignRpToUser(String raspberryPiSerialNumber, String userEmail) {
        return usersEmailByRaspberryPi.putIfAbsent(raspberryPiSerialNumber, userEmail);
    }

    public User getUserByRpSerialNumber(String rpSerialNumber) throws Exception {
        var userEmail = usersEmailByRaspberryPi.getOrDefault(rpSerialNumber, "");
        return allUsers.getOrDefault(userEmail,null);
    }

    public void LoadFromDb() {
        var usersInDb = usersJpaRepositry.findAll();
        for(var user : usersInDb) {
            if (!user.is_admin())
            {
                usersEmailByRaspberryPi.put(((Rider)user).getRaspberryPiSerialNumber(), user.get_email());
            }
            allUsers.put(user.get_email(), user);
        }
    }

    public void changeUserPassword(String userEmail, String newPasswordToken) throws UserNotFoundException {
        var user = getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("user with email:" + userEmail + " not found");
        }
        user.change_password_token(newPasswordToken);
        usersJpaRepositry.save(user);
    }

    public void updateUserRating(String userEmail, Ride ride, int number_of_rides, UserRateCalculator userRateCalculator) throws Exception {
        var user = getUserByEmail(userEmail);
        if (user.is_admin())
            throw new Exception("user is admin");

        ((Rider) user).update_rating(ride, number_of_rides, userRateCalculator);
        usersJpaRepositry.save(user);
    }

    public void notifyUser(User userToNotify, Notification notification) {
        userToNotify.notify_user(notification);
        usersJpaRepositry.save(userToNotify);
    }

    public boolean isDbEmpty() {
        return usersJpaRepositry.count() == 0;
    }
}
