package gotcha.server.Domain.AdvertiseModule;
import java.util.concurrent.atomic.AtomicInteger;

public class AdvertiseController {
    private AtomicInteger id_counter;

    private static class SingletonHolder {
        private static AdvertiseController instance = new AdvertiseController();
    }
    public static AdvertiseController get_instance() {
        return AdvertiseController.SingletonHolder.instance;
    }

    public AdvertiseController() {
        this.id_counter = new AtomicInteger(1);
    }

    public void load() {
    }
}


