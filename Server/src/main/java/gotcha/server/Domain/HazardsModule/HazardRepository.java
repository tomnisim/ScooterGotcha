package gotcha.server.Domain.HazardsModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HazardRepository {
    private Map<Integer, StationaryHazard> stationaryHazardsList;
    private final IHazardRepository hazardJpaRepository;

    @Autowired
    public HazardRepository(IHazardRepository hazardJpaRepository) {
        this.stationaryHazardsList = new ConcurrentHashMap<>();
        this.hazardJpaRepository = hazardJpaRepository;
        LoadFromDB();
    }

    public void addHazard(StationaryHazard newHazard) throws Exception {
        hazardJpaRepository.save(newHazard);
        var addRideResult = this.stationaryHazardsList.putIfAbsent(newHazard.getId(), newHazard);
        if (addRideResult != null) {
            throw new Exception("hazard already exists");
        }
    }

    public List<StationaryHazard> getAllHazards() {
        return  new ArrayList<>(stationaryHazardsList.values());
    }


    public void removeHazard(int hazardId) throws Exception {
        var result = stationaryHazardsList.remove(hazardId);
        if (result == null)
            throw new Exception("hazard with id:" + hazardId + " not found");
        hazardJpaRepository.delete(result);
    }

    public StationaryHazard getHazard(int hazardId) throws Exception {
        var result = stationaryHazardsList.get(hazardId);
        if (result == null) {
            return getHazardFromDb(hazardId);
        }
        return result;
    }

    private StationaryHazard getHazardFromDb(int hazardId) throws Exception {
        var result = hazardJpaRepository.findById(hazardId);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new Exception("hazard with id:" + hazardId + " not found");
        }
    }

    private void LoadFromDB() {
        var hazardsInDb = hazardJpaRepository.findAll();
        for(var hazard : hazardsInDb) {
            stationaryHazardsList.put(hazard.getId(), hazard);
        }
    }

    public StationaryHazard getHazardById(int hazardId) {
        return stationaryHazardsList.getOrDefault(hazardId, null);
    }

}
