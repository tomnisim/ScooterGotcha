package gotcha.server.Domain.AwardsModule;

import gotcha.server.Domain.AdvertiseModule.Advertise;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AwardsController implements IAwardsController {
    private AtomicInteger id_counter;
    private Map<Integer, Award> award_map;

    public AwardsController() {
        this.id_counter = new AtomicInteger(1);
        this.award_map = new HashMap<>();
    }

    @Override
    public void add_award(String message, String admin_email, String[] emails) {
        int award_id = this.id_counter.incrementAndGet();
        Award award = new Award(award_id, message, admin_email, emails);
        this.award_map.put(award_id, award);
    }

    @Override
    public Collection<Award> view_awards() {
        return this.award_map.values();
    }

    @Override
    public Award get_award(int award_id) {
        return this.award_map.get(award_id);

    }


}


