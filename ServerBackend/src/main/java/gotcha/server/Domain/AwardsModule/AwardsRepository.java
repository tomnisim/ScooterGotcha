package gotcha.server.Domain.AwardsModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AwardsRepository {
    private Map<Integer, Award> award_map;
    private final IAwardsRepository awardsJpaRepository;

    @Autowired
    public AwardsRepository(IAwardsRepository awardsJpaRepository) {
        this.awardsJpaRepository = awardsJpaRepository;
        this.award_map = new ConcurrentHashMap<>();
        LoadFromDB();
    }

    public void addAward(Award newAward) throws Exception {
        awardsJpaRepository.save(newAward);
        var addRideResult = this.award_map.putIfAbsent(newAward.getId(), newAward);
        if (addRideResult != null) {
            throw new Exception("Advertisement already exists");
        }
    }

    public List<Award> getAllAwards() {
        return  new ArrayList<>(award_map.values());
    }


    public void removeAward(int awardId) throws Exception {
        var result = award_map.remove(awardId);
        if (result == null)
            throw new Exception("advertisement with id:" + awardId + " not found");
        awardsJpaRepository.delete(result);
    }

    public Award getAward(int awardId) throws Exception {
        var result = award_map.get(awardId);
        if (result == null) {
            return getAwardFromDb(awardId);
        }
        return result;
    }

    private Award getAwardFromDb(int awardId) throws Exception {
        var result = awardsJpaRepository.findById(awardId);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new Exception("advertisement with id:" + awardId + " not found");
        }
    }

    private void LoadFromDB() {
        var awardsInDb = awardsJpaRepository.findAll();
        for(var award : awardsInDb) {
            award_map.put(award.getId(), award);
        }
    }
}
