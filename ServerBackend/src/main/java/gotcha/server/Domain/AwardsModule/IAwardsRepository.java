package gotcha.server.Domain.AwardsModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAwardsRepository extends JpaRepository<Award, Integer> {
}
