package TradingSystem.server.Domain.Statistics;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import TradingSystem.server.Domain.UserModule.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class StatisticsManager implements iStatisticsManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String init_time;
    private AtomicInteger login_count;
    private AtomicInteger logout_count;
    private AtomicInteger connect_system_count;
    private AtomicInteger register_count;
    private AtomicInteger buy_cart_count;

    public StatisticsManager() {
        this.init_time = LocalDateTime.now().toString();
        this.login_count = new AtomicInteger(0);
        this.logout_count = new AtomicInteger(0);
        this.connect_system_count = new AtomicInteger(0);
        this.register_count = new AtomicInteger(0);
        this.buy_cart_count = new AtomicInteger(0);
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

    private long get_buy_cart_statistics() {
        long mins = get_total_minutes_system_on();
        if (buy_cart_count.get() == 0)
            return 0;
        return mins / buy_cart_count.get();
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

    public void inc_buy_cart_count() {
        this.buy_cart_count.incrementAndGet();
    }

    public Statistic get_system_statistics(Map<String, User> users, Map<Integer, User> onlineUsers) {
        LocalDateTime init_system_time = LocalDateTime.parse(init_time);
        long login_per_minutes = get_login_statistics();
        long logout_per_minutes = get_logout_statistics();
        long connect_per_minutes = get_connect_system_statistics();
        long register_per_minutes = get_register_statistics();
        long buy_cart__per_minutes = get_buy_cart_statistics();
        int num_of_guests = 0;
        int num_of_non_managers_and_owners = 0;
        int managers_but_not_owners_or_founders = 0;
        int owners_or_founders = 0;
        for(User u: onlineUsers.values()){
            if(u.getIsGuest().get())
                num_of_guests++;
            if(!u.check_if_manager() && !u.check_if_owner() && !u.check_if_founder())
                num_of_non_managers_and_owners++;
            if(u.check_if_manager() && (!u.check_if_founder() && !u.check_if_owner()))
                managers_but_not_owners_or_founders++;
            if(u.check_if_owner() || u.check_if_founder())
                owners_or_founders++;
        }
        return new Statistic(init_system_time, login_per_minutes, logout_per_minutes, connect_per_minutes, register_per_minutes, buy_cart__per_minutes,users.size(),onlineUsers.size(),
                num_of_guests,num_of_non_managers_and_owners,managers_but_not_owners_or_founders,owners_or_founders);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
