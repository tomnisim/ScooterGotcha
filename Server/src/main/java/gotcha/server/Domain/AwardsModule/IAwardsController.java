package gotcha.server.Domain.AwardsModule;

import java.util.Collection;

public interface IAwardsController {
    void add_award(String message,String admin_email, String[] emails);
    Collection<Award> view_awards();
    Award get_award(int award_id);
}
