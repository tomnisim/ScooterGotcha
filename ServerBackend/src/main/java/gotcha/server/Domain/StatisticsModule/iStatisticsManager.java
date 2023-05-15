package gotcha.server.Domain.StatisticsModule;

import java.util.List;


public interface iStatisticsManager {

    void update_daily_statistic();
    DailyStatisticDTO get_current_daily_statistic();
    GeneralStatistic get_general_statistic();
    List<DailyStatisticDTO> get_all_daily_statistic();

    void inc_login_count();
    void inc_logout_count();
    void inc_shut_down_count();
    void inc_reset_count();





}

