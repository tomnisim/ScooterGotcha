package gotcha.server.Domain.StatisticsModule;


import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.AdvertiseModule.IAdvertiseController;
import gotcha.server.Domain.AwardsModule.AwardsController;
import gotcha.server.Domain.AwardsModule.IAwardsController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.IHazardController;
import gotcha.server.Domain.QuestionsModule.IQuestionController;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.IRidesController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.IUserController;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Domain.UserModule.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class StatisticsManager implements iStatisticsManager {
    private Map<LocalDate, DailyStatistic> dailyStatisticMap;
    private DailyStatistic current_daily_statistic;
    private GeneralStatistic generalStatistic;

    private QuestionController question_controller;
    private IRidesController rides_controller;
    private IUserController user_controller;
    private IAdvertiseController advertise_controller;
    private IAwardsController awards_controller;
    private IHazardController hazard_controller;

    @Autowired
    public StatisticsManager(UserController userController, HazardController hazardController,
                             AdvertiseController advertiseController, IAwardsController awardsController, RidesController ridesController,
                             QuestionController questionController) {
        this.question_controller = questionController;
        this.user_controller = userController;
        this.hazard_controller = hazardController;
        this.rides_controller = ridesController;
        this.advertise_controller = advertiseController;
        this.awards_controller = awardsController;


        this.generalStatistic = new GeneralStatistic(LocalDateTime.now().toString());
        this.dailyStatisticMap = new HashMap<>();
        update_daily_statistic();

        this.dailyStatisticMap.put(this.current_daily_statistic.getDate(), this.current_daily_statistic);


    }

    /**
     * this method should be called every day and save the previous daily statistics.
     */
    @Override
    public void update_daily_statistic(){
        int admins = user_controller.view_admins().size();
        int admins_answers = question_controller.getQuestion_ids_counter() - question_controller.get_all_open_questions().size();
        int advertisements = advertise_controller.get_all_advertisements_for_admin().size();
        int awards = awards_controller.view_awards().size();
        int rides = rides_controller.get_all_rides().size();
        int hazards = hazard_controller.view_hazards().size();
        int riders = user_controller.get_all_riders().size();
        int users_questions = question_controller.getQuestion_ids_counter();
        if (this.current_daily_statistic == null || !LocalDate.now().isEqual(this.current_daily_statistic.getDate())) {
            this.current_daily_statistic = new DailyStatistic(admins, admins_answers, advertisements, awards, rides, hazards, riders, users_questions);
            this.dailyStatisticMap.put(LocalDate.now(), this.current_daily_statistic);
        }
        else {
            this.current_daily_statistic.update(admins, admins_answers, advertisements, awards, rides, hazards, riders, users_questions);
        }
        this.generalStatistic.update(admins, admins_answers, advertisements, awards, rides, hazards, riders, users_questions);
    }




    @Override
    public DailyStatisticDAO get_current_daily_statistic() {
        update_daily_statistic();
        return this.current_daily_statistic.getDAO();
    }

    @Override
    public GeneralStatistic get_general_statistic() {
        update_daily_statistic();
        return this.generalStatistic;
    }

    @Override
    public List<DailyStatisticDAO> get_all_daily_statistic() {
        ArrayList<DailyStatisticDAO> list_to_return = new ArrayList<>();
        for (DailyStatistic dailyStatistic : this.dailyStatisticMap.values()){
            list_to_return.add(dailyStatistic.getDAO());
        }
        return list_to_return;
    }

    @Override
    public void inc_login_count() {
        this.current_daily_statistic.incOnline_users();

    }

    @Override
    public void inc_logout_count() {
        this.current_daily_statistic.incOnline_guests();

    }


    @Override
    public void inc_shut_down_count() {
        this.current_daily_statistic.incShut_down_events();

    }

    @Override
    public void inc_reset_count() {
        this.current_daily_statistic.incReset_events();

    }


}
