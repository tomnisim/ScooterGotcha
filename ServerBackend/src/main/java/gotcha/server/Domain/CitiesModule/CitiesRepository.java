package gotcha.server.Domain.CitiesModule;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CitiesRepository {
    private Map<Integer, City> allCities;
    private ICitiesRepository citiesJpaRepository;
    public CitiesRepository(ICitiesRepository citiesJpaRepository) {
        this.allCities = new HashMap<>();
        this.citiesJpaRepository = citiesJpaRepository;
        LoadFromDb();
    }

    public void addCityName(int placeId, String cityName) throws Exception {
        var city = allCities.get(placeId);
        if (city == null) {
            city = getCityFromDb(placeId);
        }
        city.addName(cityName);
    }

    // a function that adds a new city if not already present to the list and saves it in the db
    public void addCity(City city) {
        citiesJpaRepository.save(city);
        allCities.put(city.getPlaceId(), city);
    }

    // a function that returns a list of city names based on a placeId, throws an exception if the placeId is not found
    public List<String> getCityNames(int placeId) throws Exception {
        var city = allCities.get(placeId);
        if (city == null) {
            city = getCityFromDb(placeId);
        }
        return new ArrayList<>(city.getNames());
    }

    // a function that deletes the city
    public void deleteCity(int placeId) throws Exception {
        var city = allCities.remove(placeId);
        if (city == null) {
            city = getCityFromDb(placeId);
        }
        citiesJpaRepository.delete(city);
    }

    private City getCityFromDb(int placeId) throws Exception {
        var result = citiesJpaRepository.findById(placeId);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new Exception("city with placeId:" + placeId + " not found");
        }
    }

    private void LoadFromDb() {
        var allCities = citiesJpaRepository.findAll();
        for(var city : allCities) {
            this.allCities.put(city.getPlaceId(), city);
        }
    }
}
