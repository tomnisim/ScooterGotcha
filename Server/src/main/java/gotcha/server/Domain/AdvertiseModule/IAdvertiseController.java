package gotcha.server.Domain.AdvertiseModule;

import java.time.LocalDateTime;

public interface IAdvertiseController {
    void load();

    void add_advertise(LocalDateTime final_date, String owner, String message, String photo);

    void remove_advertise(int advertise_id);

    void update_advertise(int advertise_id, LocalDateTime final_date, String owner, String message, String photo);
}
