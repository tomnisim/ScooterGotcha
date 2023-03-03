package gotcha.server.Domain.AdvertiseModule;
import java.time.LocalDateTime;
import java.util.*;
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
    public void add_advertise(LocalDateTime final_date, String owner, String message, String photo, String url){
        int advertise_id = this.id_counter.incrementAndGet();
        Advertise advertise = new Advertise(advertise_id, final_date, owner, message, photo, url);
        this.advertise_list.put(advertise_id, advertise);
    }
    @Override
    public void remove_advertise(int advertise_id){
        this.advertise_list.remove(advertise_id);
    }

    @Override
    public void update_advertise(int advertise_id, LocalDateTime final_date, String owner, String message, String photo, String url){
        Advertise advertise = this.advertise_list.get(advertise_id);
        advertise.setFinal_date(final_date);
        advertise.setOwner(owner);
        advertise.setMessage(message);
        advertise.setPhoto(photo);
        advertise.setUrl(url);

    }



    @Override
    public List<String> get_all_advertisements_for_admin(){
        List<String> to_return = new LinkedList<>();
        for (Advertise advertise : this.advertise_list.values())
        {
            to_return.add(advertise.toString_admin());
        }
        return to_return;
    }

    @Override
    public List<String> get_all_advertisements_for_user(){
        List<String> to_return = new LinkedList<>();
        for (Advertise advertise : this.advertise_list.values())
        {
            to_return.add(advertise.toString_user());
        }
        return to_return;
    }

}


