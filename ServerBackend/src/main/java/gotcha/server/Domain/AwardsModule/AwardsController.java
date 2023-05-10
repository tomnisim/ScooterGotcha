package gotcha.server.Domain.AwardsModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AwardsController implements IAwardsController {
    private final AwardsRepository awardsRepository;
    @Autowired
    public AwardsController(AwardsRepository awardsRepository) {
        this.awardsRepository = awardsRepository;
    }

    @Override
    public void add_award(String message, String admin_email, List<String> emails) throws Exception {
        Award award = new Award(message, admin_email, emails);
        awardsRepository.addAward(award);
    }

    @Override
    public Collection<Award> view_awards() {
        return awardsRepository.getAllAwards();
    }

    @Override
    public Award get_award(int award_id) throws Exception {
        return awardsRepository.getAward(award_id);
    }


}


