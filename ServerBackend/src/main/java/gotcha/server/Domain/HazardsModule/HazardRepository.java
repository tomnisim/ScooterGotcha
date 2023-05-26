package gotcha.server.Domain.HazardsModule;

import gotcha.server.Domain.s3.S3Service;
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
    private final S3Service s3Service;

    @Autowired
    public HazardRepository(IHazardRepository hazardJpaRepository, S3Service s3Service) {
        this.stationaryHazardsList = new ConcurrentHashMap<>();
        this.hazardJpaRepository = hazardJpaRepository;
        this.s3Service = s3Service;
        LoadFromDB();
    }

    public void addHazard(StationaryHazard newHazard, byte[] photo) throws Exception {
        hazardJpaRepository.save(newHazard);
        var photoS3Key = "hazardImage"+newHazard.getId();
        newHazard.setPhotoS3Key(photoS3Key);
        newHazard.setPhoto(photo);
        s3Service.putImage(photoS3Key, photo);
        // update the hazard with the s3 key
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

    public StationaryHazard getHazardById(int hazardId) throws Exception {
        var result = stationaryHazardsList.get(hazardId);
        if (result == null) {
            return getHazardFromDb(hazardId);
        }
        return result;
    }

    public void setReportOnHazard(int hazardId) throws Exception {
        var hazard = getHazardById(hazardId);
        hazard.setReport(true);
        hazardJpaRepository.save(hazard);
    }

    private StationaryHazard getHazardFromDb(int hazardId) throws Exception {
        var result = hazardJpaRepository.findById(hazardId);
        if (result.isPresent()) {
            var hazard = result.get();
            hazard.setPhoto(s3Service.getImage(hazard.getPhotoS3Key()));
            return hazard;
        }
        else {
            throw new Exception("hazard with id:" + hazardId + " not found");
        }
    }

    private void LoadFromDB() {
        var hazardsInDb = hazardJpaRepository.findAll();
        for(var hazard : hazardsInDb) {
            hazard.setPhoto(s3Service.getImage(hazard.getPhotoS3Key()));
            stationaryHazardsList.put(hazard.getId(), hazard);
        }
    }

    public void updateHazard(StationaryHazard hazardToUpdate, double newSize) {
        hazardToUpdate.setSize(newSize);
        hazardToUpdate.setReport(false);
        hazardJpaRepository.save(hazardToUpdate);
    }

    public boolean isDbEmpty() {
        return hazardJpaRepository.count() == 0;
    }
}
