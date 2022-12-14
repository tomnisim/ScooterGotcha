package gotcha.server.Domain.RidesModule;

import java.util.concurrent.atomic.AtomicInteger;

public class RidesController {
    private AtomicInteger id_counter;

    private static class SingletonHolder {
        private static RidesController instance = new RidesController();
    }
    public static RidesController get_instance() {
        return RidesController.SingletonHolder.instance;
    }

    public RidesController() {
        this.id_counter = new AtomicInteger(1);
    }

    public void load() {
    }
}
