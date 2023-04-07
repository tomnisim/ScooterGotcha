package gotcha.server.Domain.HazardsModule;

import gotcha.server.Utils.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HazardRepository {
    private Map<Integer, StationaryHazard> stationaryHazardsList;

    public HazardRepository() {
        this.stationaryHazardsList = new ConcurrentHashMap<>();
    }
    public StationaryHazard add_hazard(StationaryHazard newHazard){
        return this.stationaryHazardsList.putIfAbsent(newHazard.getId(), newHazard);
    }

    public List<StationaryHazard> getAllHazards() {
        return  new ArrayList<>(stationaryHazardsList.values());
    }

    public void removeHazard(int hazardId) throws Exception {
        var result = stationaryHazardsList.remove(hazardId);
        if (result == null)
            throw new Exception("hazard with id:" + hazardId + " not found");
    }

}
