package gotcha.server.Domain.AdvertiseModule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
@Component
public class AdvertiseController implements IAdvertiseController {
    private final AdvertiseRepository advertiseRepository;

    public AdvertiseController(AdvertiseRepository advertiseRepository) {
        this.advertiseRepository = advertiseRepository;
    }

    @Override
    public void load() {}

    @Override
    public Advertise add_advertise(LocalDate final_date, String owner, String message, String photo, String url) throws Exception {
        Advertise advertise = new Advertise(final_date, owner, message, photo, url);
        this.advertiseRepository.addAdvertisement(advertise);
        return advertise;
    }
    @Override
    public void remove_advertise(int advertise_id) throws Exception {
        this.advertiseRepository.removeAdvertisement(advertise_id);
    }

    @Override
    public void update_advertise(int advertise_id, LocalDate final_date, String owner, String message, String photo, String url) throws Exception {
        advertiseRepository.updateAdvertise(advertise_id,final_date, owner, message, photo, url);
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
    public List<AdvertiseDAO> get_all_advertisements_for_user(){
        List<AdvertiseDAO> to_return = new LinkedList<>();
        for (Advertise advertise : advertiseRepository.getAllAdvertisements())
        {
            to_return.add(new AdvertiseDAO(advertise));
        }
        return to_return;
    }

    @Override
    public void add_click(int id) throws Exception {
        this.advertiseRepository.addClick(id);
    }
}


