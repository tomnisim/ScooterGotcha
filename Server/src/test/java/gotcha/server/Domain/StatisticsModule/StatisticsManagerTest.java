package gotcha.server.Domain.StatisticsModule;

import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.AwardsModule.AwardsController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsManagerTest {
    private StatisticsManager statisticsManager;
    private HazardController hazardController;
    private AdvertiseController advController;
    private AwardsController awardController;
    private RidesController ridesController;
    private QuestionController questionController;
    private UserController userController;
    @BeforeEach
    void setUp() {

        statisticsManager = new StatisticsManager(userController, hazardController, advController, awardController,
                ridesController, questionController);
    }

    @Test
    void update_daily_statistic() {
    }

    @Test
    void get_current_daily_statistic() {
    }

    @Test
    void get_general_statistic() {
    }

    @Test
    void get_all_daily_statistic() {
    }
}