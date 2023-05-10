package gotcha.server.Domain.AdvertiseModule;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Exceptions.RideNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AdvertiseRepository{
    private Map<Integer, Advertise> advertise_list;
    private final IAdvertiseRepository advertiseJpaRepository;

    @Autowired
    public AdvertiseRepository(IAdvertiseRepository advertiseJpaRepository){
        this.advertise_list = new ConcurrentHashMap<>();
        this.advertiseJpaRepository = advertiseJpaRepository;
        LoadFromDB();
    }

    public void addAdvertisement(Advertise newAdvertisement) throws Exception {
        advertiseJpaRepository.save(newAdvertisement);
        var addRideResult = this.advertise_list.putIfAbsent(newAdvertisement.getId(), newAdvertisement);
        if (addRideResult != null) {
            throw new Exception("Advertisement already exists");
        }
    }

    public List<Advertise> getAllAdvertisements() {
        return  new ArrayList<>(advertise_list.values());
    }


    public void removeAdvertisement(int advertisementId) throws Exception {
        var result = advertise_list.remove(advertisementId);
        if (result == null)
            throw new Exception("advertisement with id:" + advertisementId + " not found");
        advertiseJpaRepository.delete(result);
    }

    public Advertise getAdvertise(int advertisementId) throws Exception {
        var result = advertise_list.get(advertisementId);
        if (result == null) {
            return getAdvertiseFromDb(advertisementId);
        }
        return result;
    }

    private Advertise getAdvertiseFromDb(int advertisementId) throws Exception {
        var result = advertiseJpaRepository.findById(advertisementId);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new Exception("advertisement with id:" + advertisementId + " not found");
        }
    }

    private void LoadFromDB() {
        var advertisesInDb = advertiseJpaRepository.findAll();
        for(var advertisement : advertisesInDb) {
            advertise_list.put(advertisement.getId(), advertisement);
        }
    }

    public void updateAdvertise(int advertiseId, LocalDate final_date, String owner, String message, String photo, String url) throws Exception {
        var advertise = getAdvertise(advertiseId);
        advertise.setFinal_date(final_date);
        advertise.setOwner(owner);
        advertise.setMessage(message);
        advertise.setPhoto(photo);
        advertise.setUrl(url);
        advertiseJpaRepository.save(advertise);
    }

    public void addClick(int id) throws Exception {
        getAdvertise(id).add_click();
    }

    public boolean isDbEmpty() {
        return advertiseJpaRepository.count() == 0;
    }
}
