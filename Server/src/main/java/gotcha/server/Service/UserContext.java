package gotcha.server.Service;

import gotcha.server.Domain.UserModule.User;

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
}
