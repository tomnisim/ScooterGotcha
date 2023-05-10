package gotcha.server.Domain.StatisticsModule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface IDailyStatisticsRepository extends JpaRepository<DailyStatistic, LocalDate> {
}
