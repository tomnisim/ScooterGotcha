package gotcha.server.Domain.AdvertiseModule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class AdvertiseController implements IAdvertiseController {
    private final AdvertiseRepository advertiseRepository;

    public AdvertiseController(AdvertiseRepository advertiseRepository) {
        this.advertiseRepository = advertiseRepository;
    }

    @Override
    public void load() {}

    @Override
    public Advertise add_advertise(LocalDateTime final_date, String owner, String message, String photo, String url) throws Exception {
        Advertise advertise = new Advertise(final_date, owner, message, photo, url);
        this.advertiseRepository.addAdvertisement(advertise);
        return advertise;
    }
    @Override
    public void remove_advertise(int advertise_id) throws Exception {
        this.advertiseRepository.removeAdvertisement(advertise_id);
    }

    @Override
    public void update_advertise(int advertise_id, LocalDateTime final_date, String owner, String message, String photo, String url) throws Exception {
        Advertise advertise = advertiseRepository.getAdvertise(advertise_id);
        advertise.setFinal_date(final_date);
        advertise.setOwner(owner);
        advertise.setMessage(message);
        advertise.setPhoto(photo);
        advertise.setUrl(url);

    }



    @Override
    public List<Advertise> get_all_advertisements_for_admin(){
        List<Advertise> to_return = new LinkedList<>();
        for (Advertise advertise : advertiseRepository.getAllAdvertisements())
        {
            to_return.add(advertise);
        }
        return to_return;
    }

    @Override
    public List<String> get_all_advertisements_for_user(){
        List<String> to_return = new LinkedList<>();
        for (Advertise advertise : advertiseRepository.getAllAdvertisements())
        {
            to_return.add(advertise.toString_user());
        }
        return to_return;
    }

}


