package gotcha.server.Domain.StatisticsModule;


import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.Utils.Password.PasswordManagerImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StatisticsManager implements iStatisticsManager {

    private Long id;
    private String init_time;
    private AtomicInteger login_count;
    private AtomicInteger logout_count;
    private AtomicInteger connect_system_count;
    private AtomicInteger register_count;

    public StatisticsManager() {
        this.init_time = LocalDateTime.now().toString();
        this.login_count = new AtomicInteger(0);
        this.logout_count = new AtomicInteger(0);
        this.connect_system_count = new AtomicInteger(0);
        this.register_count = new AtomicInteger(0);
    }

    private long get_total_minutes_system_on() {
        return ChronoUnit.MINUTES.between(LocalDateTime.parse(init_time), LocalDateTime.now());
    }

    private long get_login_statistics() {
        long mins = get_total_minutes_system_on();
        if (login_count.get() == 0)
            return 0;
        return mins / login_count.get();
    }

    private long get_logout_statistics() {
        long mins = get_total_minutes_system_on();
        if (logout_count.get() == 0)
            return 0;
        return mins / logout_count.get();
    }

    private long get_connect_system_statistics() {
        long mins = get_total_minutes_system_on();
        if (connect_system_count.get() == 0)
            return 0;
        return mins / connect_system_count.get();
    }

    private long get_register_statistics() {
        long mins = get_total_minutes_system_on();
        if (register_count.get() == 0)
            return 0;
        return mins / register_count.get();
    }

    public void inc_login_count() {
        this.login_count.incrementAndGet();
    }

    public void inc_logout_count() {
        this.logout_count.incrementAndGet();
    }

    public void inc_connect_system_count() {
        this.connect_system_count.incrementAndGet();
    }

    public void inc_register_count() {
        this.register_count.incrementAndGet();
    }

    public Statistic get_system_statistics(List<User> allUsers) {
        LocalDateTime init_system_time = LocalDateTime.parse(init_time);
        long login_per_minutes = get_login_statistics();
        long logout_per_minutes = get_logout_statistics();
        long connect_per_minutes = get_connect_system_statistics();
        long register_per_minutes = get_register_statistics();
        int num_of_users = allUsers.size();
        int num_of_online_users = 0;
        for (User user : allUsers){
            if (user.is_logged_in())
                num_of_online_users++;
        }
        return new Statistic(init_system_time,login_per_minutes, logout_per_minutes, connect_per_minutes,
                    register_per_minutes, num_of_users, num_of_online_users);

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
