package gotcha.server.Domain.AdvertiseModule;

import java.time.LocalDateTime;
import java.util.List;

public interface IAdvertiseController {
    void load();

    Advertise add_advertise(LocalDateTime final_date, String owner, String message, String photo, String url);

    void remove_advertise(int advertise_id);

    void update_advertise(int advertise_id, LocalDateTime final_date, String owner, String message, String photo, String url);

    List<Advertise> get_all_advertisements_for_admin();

    List<String> get_all_advertisements_for_user();
}
