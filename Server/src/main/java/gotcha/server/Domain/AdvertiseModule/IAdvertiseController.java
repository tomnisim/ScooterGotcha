package gotcha.server.Domain.AdvertiseModule;

import java.time.LocalDate;
import java.util.List;

public interface IAdvertiseController {
    void load();

    Advertise add_advertise(LocalDate final_date, String owner, String message, String photo, String url);

    void remove_advertise(int advertise_id) throws Exception;

    void update_advertise(int advertise_id, LocalDate final_date, String owner, String message, String photo, String url) throws Exception;

    List<Advertise> get_all_advertisements_for_admin();

    List<String> get_all_advertisements_for_user();
}
