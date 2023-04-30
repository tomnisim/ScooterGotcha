package gotcha.server.Domain.RidesModule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRidesRepository extends JpaRepository<Ride, Integer> {
}
