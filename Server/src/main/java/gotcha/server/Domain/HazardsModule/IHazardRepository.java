package gotcha.server.Domain.HazardsModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHazardRepository extends JpaRepository<StationaryHazard, Integer> {
}
