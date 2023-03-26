package gotcha.server.Domain.StatisticsModule;

import gotcha.server.Domain.UserModule.User;

import java.util.List;


public interface iStatisticsManager {
    void inc_login_count();
    void inc_logout_count();
    void inc_connect_system_count();
    void inc_register_count();
    Statistic get_system_statistics (List<User> allUsers);
}

