package gotcha.server.Service;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Domain.UserModule.User;

import java.util.Collection;

public class UserContext {
    private User loggedUser;

    public UserContext(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isLoggedIn() {
        return loggedUser != null;
    }

    public boolean isAdmin() {
        return isLoggedIn() && loggedUser.is_admin();
    }
    public String get_email() {return loggedUser.get_email();}
    public Collection<Notification> get_notifications() {return loggedUser.get_notifications();}
}
