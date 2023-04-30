package gotcha.server.Domain.StatisticsModule;

import java.util.List;


public interface iStatisticsManager {

    void update_daily_statistic();
    DailyStatisticDAO get_current_daily_statistic();
    GeneralStatistic get_general_statistic();
    List<DailyStatisticDAO> get_all_daily_statistic();

    void inc_login_count();
    void inc_logout_count();
    void inc_shut_down_count();
    void inc_reset_count();





}

