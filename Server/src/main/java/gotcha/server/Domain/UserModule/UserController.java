package gotcha.server.Domain.UserModule;

import java.util.concurrent.atomic.AtomicInteger;

public class UserController {
    private AtomicInteger id_counter;

    private static class SingletonHolder {
        private static UserController instance = new UserController();
    }
    public static UserController get_instance() {
        return UserController.SingletonHolder.instance;
    }

    public UserController() {
        this.id_counter = new AtomicInteger(1);
    }

    public void load() {
    }
}
