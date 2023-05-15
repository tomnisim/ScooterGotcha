package gotcha.server.Domain.StatisticsModule;


import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.AdvertiseModule.IAdvertiseController;
import gotcha.server.Domain.AwardsModule.IAwardsController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.IHazardController;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.IRidesController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.IUserController;
import gotcha.server.Domain.UserModule.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class StatisticsManager implements iStatisticsManager {
    private final DailyStatisticsRepository dailyStatisticsRepository;
    private final GeneralStatisticRepository generalStatisticRepository;
    private DailyStatistic current_daily_statistic;
    private GeneralStatistic generalStatistic;

    private QuestionController question_controller;
    private IRidesController rides_controller;
    private IUserController user_controller;
    private IAdvertiseController advertise_controller;
    private IAwardsController awards_controller;
    private IHazardController hazard_controller;

    private final ReentrantLock onlineUsersLock = new ReentrantLock();
    private final ReentrantLock onlineGuestsLock = new ReentrantLock();
    private final ReentrantLock shutDownEventsLock = new ReentrantLock();
    private final ReentrantLock resetEventsLock = new ReentrantLock();

    @Autowired
    public StatisticsManager(UserController userController, HazardController hazardController,
                             AdvertiseController advertiseController, IAwardsController awardsController, RidesController ridesController,
                             QuestionController questionController, DailyStatisticsRepository dailyStatisticsRepository, GeneralStatisticRepository generalStatisticRepository) {
        this.question_controller = questionController;
        this.user_controller = userController;
        this.hazard_controller = hazardController;
        this.rides_controller = ridesController;
        this.advertise_controller = advertiseController;
        this.awards_controller = awardsController;
        this.generalStatisticRepository = generalStatisticRepository;
        this.generalStatistic = generalStatisticRepository.findById(1L).orElse(new GeneralStatistic());
        this.dailyStatisticsRepository = dailyStatisticsRepository;
        update_daily_statistic();
        this.dailyStatisticsRepository.addDailyStatistic(this.current_daily_statistic);
    }

    /**
     * this method should be called every day and save the previous daily statistics.
     */
    @Override
    public void update_daily_statistic(){
        int admins = user_controller.view_admins().size();
        // TODO: 4/11/2023 : Add function in repository to fetch latest id
        //int admins_answers = question_controller.getQuestion_ids_counter() - question_controller.get_all_open_questions().size();
        int admins_answers = 5;
        int advertisements = advertise_controller.get_all_advertisements_for_admin().size();
        int awards = awards_controller.view_awards().size();
        int rides = rides_controller.get_all_rides().size();
        int hazards = hazard_controller.view_hazards().size();
        int riders = user_controller.get_all_riders().size();
        //int users_questions = question_controller.getQuestion_ids_counter();
        int users_questions = 5;
        if (this.current_daily_statistic == null || !LocalDate.now().isEqual(this.current_daily_statistic.getDate())) {
            this.current_daily_statistic = new DailyStatistic(admins, admins_answers, advertisements, awards, rides, hazards, riders, users_questions);
            dailyStatisticsRepository.addDailyStatistic(current_daily_statistic);
        }
        else {
            this.current_daily_statistic.update(admins, admins_answers, advertisements, awards, rides, hazards, riders, users_questions);
        }
        this.generalStatistic.update(admins, admins_answers, advertisements, awards, rides, hazards, riders, users_questions);
        this.generalStatisticRepository.save(generalStatistic);
    }

    @Override
    public DailyStatisticDTO get_current_daily_statistic() {
        update_daily_statistic();
        return this.current_daily_statistic.getDTO();
    }

    @Override
    public GeneralStatistic get_general_statistic() {
        update_daily_statistic();
        return this.generalStatistic;
    }

    @Override
    public List<DailyStatisticDTO> get_all_daily_statistic() {
        ArrayList<DailyStatisticDTO> list_to_return = new ArrayList<>();
        for (DailyStatistic dailyStatistic : dailyStatisticsRepository.getAllStatistics()){
            list_to_return.add(dailyStatistic.getDTO());
        }
        return list_to_return;
    }

    @Override
    public void inc_login_count() {
        onlineUsersLock.lock();
        try {
            current_daily_statistic.setOnline_users(current_daily_statistic.getOnline_users() + 1);
        } finally {
            onlineUsersLock.unlock();
        }
    }

    @Override
    public void inc_logout_count() {
        onlineGuestsLock.lock();
        try {
            current_daily_statistic.setOnline_guests(current_daily_statistic.getOnline_guests() + 1);
        } finally {
            onlineGuestsLock.unlock();
        }
    }

    @Override
    public void inc_shut_down_count() {
        shutDownEventsLock.lock();
        try {
            current_daily_statistic.setShut_down_events(current_daily_statistic.getShut_down_events() + 1);
        } finally {
            shutDownEventsLock.unlock();
        }
    }

    @Override
    public void inc_reset_count() {
        resetEventsLock.lock();
        try {
            current_daily_statistic.setReset_events(current_daily_statistic.getReset_events() + 1);
        } finally {
            resetEventsLock.unlock();
        }
    }
}
