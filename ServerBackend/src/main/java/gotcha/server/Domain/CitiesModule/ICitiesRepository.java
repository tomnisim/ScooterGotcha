package gotcha.server.Domain.CitiesModule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitiesRepository extends JpaRepository<City, Integer> {
}
