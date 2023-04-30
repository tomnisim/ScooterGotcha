package gotcha.server.Domain.RidesModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRidesRepository extends JpaRepository<Ride, Integer> {
    @Query("SELECT r FROM Ride r JOIN FETCH r.actions")
    List<Ride> findAllWithActions();

    @Query("SELECT r FROM Ride r JOIN FETCH r.junctions")
    List<Ride> findAllWithJunctions();

    @Query("SELECT r FROM Ride r JOIN FETCH r.actions WHERE r.ride_id = :rideId")
    Optional<Ride> findByIdWithActions(@Param("rideId") int rideId);

    @Query("SELECT r FROM Ride r JOIN FETCH r.junctions WHERE r.ride_id = :rideId")
    Optional<Ride> findByIdWithJunctions(@Param("rideId") int rideId);
}
