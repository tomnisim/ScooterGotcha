package gotcha.server.Domain.AdvertiseModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdvertiseRepository extends JpaRepository<Advertise,Integer> {
}
