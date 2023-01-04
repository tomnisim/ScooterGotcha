package gotcha.server.Domain.AdvertiseModule;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AdvertiseController implements IAdvertiseController {
    private AtomicInteger id_counter;
    private Map<Integer, Advertise> advertise_list;

    private static class SingletonHolder {
        private static AdvertiseController instance = new AdvertiseController();
    }
    public static AdvertiseController get_instance() {
        return AdvertiseController.SingletonHolder.instance;
    }

    public AdvertiseController() {
        this.id_counter = new AtomicInteger(1);
        this.advertise_list = new HashMap<>();
    }



    @Override
    public void load() {}


    @Override
    public void add_advertise(LocalDateTime final_date, String owner, String message, String photo){
        int advertise_id = this.id_counter.incrementAndGet();
        Advertise advertise = new Advertise(advertise_id, final_date, owner, message, photo);
        this.advertise_list.put(advertise_id, advertise);
    }
    @Override
    public void remove_advertise(int advertise_id){
        this.advertise_list.remove(advertise_id);
    }

    @Override
    public void update_advertise(int advertise_id, LocalDateTime final_date, String owner, String message, String photo){
        Advertise advertise = this.advertise_list.get(advertise_id);
        advertise.setFinal_date(final_date);
        advertise.setOwner(owner);
        advertise.setMessage(message);
        advertise.setPhoto(photo);
    }
}


