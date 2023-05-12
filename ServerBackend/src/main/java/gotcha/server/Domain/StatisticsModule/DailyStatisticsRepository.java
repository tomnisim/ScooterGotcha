package gotcha.server.Domain.StatisticsModule;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class DailyStatisticsRepository {
    private IDailyStatisticsRepository statisticsJpaRepository;
    private HashMap<LocalDate, DailyStatistic> dailyStatistics;

    public DailyStatisticsRepository(IDailyStatisticsRepository statisticsJpaRepository) {
        this.statisticsJpaRepository = statisticsJpaRepository;
        this.dailyStatistics = new HashMap<>();
        LoadFromDb();
    }

    public void addDailyStatistic(DailyStatistic statistic) {
        dailyStatistics.putIfAbsent(statistic.getDate(), statistic);
        statisticsJpaRepository.save(statistic);
    }

    public void deleteStatistic(LocalDate date) throws Exception {
        var statistic = dailyStatistics.remove(date);
        if (statistic == null) {
            throw new Exception("statistic from date:" + date.toString() + " is not found");
        }
        statisticsJpaRepository.delete(statistic);
    }

    public void saveStatistic(DailyStatistic statistic) {
        dailyStatistics.put(statistic.getDate(), statistic);
        statisticsJpaRepository.save(statistic);
    }

    private void LoadFromDb() {
        var allStatistics = statisticsJpaRepository.findAll();
        for(var statistic : allStatistics) {
            dailyStatistics.put(statistic.getDate(), statistic);
        }
    }

    public List<DailyStatistic> getAllStatistics() {
        return new ArrayList<>(dailyStatistics.values());
    }
}
