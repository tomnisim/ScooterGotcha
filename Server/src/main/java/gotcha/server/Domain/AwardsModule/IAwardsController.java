package gotcha.server.Domain.AwardsModule;

import java.util.Collection;

public interface IAwardsController {
    void add_award(String message,String admin_email, String[] emails) throws Exception;
    Collection<Award> view_awards();
    Award get_award(int award_id) throws Exception;
}
