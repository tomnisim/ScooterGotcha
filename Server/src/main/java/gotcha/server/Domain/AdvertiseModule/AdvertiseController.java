package gotcha.server.Domain.AdvertiseModule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class AdvertiseController implements IAdvertiseController {
    private AtomicInteger id_counter;
    private Map<Integer, Advertise> advertise_list;

    public AdvertiseController() {
        this.id_counter = new AtomicInteger(1);
        this.advertise_list = new HashMap<>();
    }

    @Override
    public void load() {}


    @Override
    public Advertise add_advertise(LocalDate final_date, String owner, String message, String photo, String url){
        int advertise_id = this.id_counter.incrementAndGet();
        Advertise advertise = new Advertise(advertise_id, final_date, owner, message, photo, url);
        this.advertise_list.put(advertise_id, advertise);
        return advertise;
    }
    @Override
    public void remove_advertise(int advertise_id){
        this.advertise_list.remove(advertise_id);
    }

    @Override
    public void update_advertise(int advertise_id, LocalDate final_date, String owner, String message, String photo, String url) throws Exception {
        Advertise advertise = this.advertise_list.get(advertise_id);
        advertise.setFinal_date(final_date);
        advertise.setOwner(owner);
        advertise.setMessage(message);
        advertise.setPhoto(photo);
        advertise.setUrl(url);

    }



    @Override
    public List<Advertise> get_all_advertisements_for_admin(){
        List<Advertise> to_return = new LinkedList<>();
        for (Advertise advertise : this.advertise_list.values())
        {
            to_return.add(advertise);
        }
        return to_return;
    }

    @Override
    public List<AdvertiseDAO> get_all_advertisements_for_user(){
        List<AdvertiseDAO> to_return = new LinkedList<>();
        for (Advertise advertise : this.advertise_list.values())
        {
            to_return.add(new AdvertiseDAO(advertise));
        }
        return to_return;
    }

    @Override
    public void add_click(int id) {
        this.advertise_list.get(id).add_click();
    }

}


