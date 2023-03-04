package TradingSystem.server.Domain.Statistics;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Statistic {
    public String init_system_time;
    private long login_per_minutes;
    private long logout_per_minutes;
    private long connect_per_minutes;
    private long register_per_minutes;
    private long buy_cart_per_minutes;
    private int num_of_users;
    private int num_of_onlines;
    private int num_of_guests;
    private int num_of_non_managers_and_owners;
    private int managers_but_not_owners_or_founders;
    private int owners_or_founders;

    // ----------------------- constructors ---------------------------------------------


    public Statistic(LocalDateTime init_system_time, long login_per_minutes, long logout_per_minutes, long connect_per_minutes, long register_per_minutes, long buy_cart_per_minutes, int num_of_users, int num_of_onlines, int num_of_guests, int num_of_non_managers_and_owners, int managers_but_not_owners_or_founders, int owners_or_founders) {
        this.init_system_time = init_system_time.toString();
        this.login_per_minutes = login_per_minutes;
        this.logout_per_minutes = logout_per_minutes;
        this.connect_per_minutes = connect_per_minutes;
        this.register_per_minutes = register_per_minutes;
        this.buy_cart_per_minutes = buy_cart_per_minutes;
        this.num_of_users = num_of_users;
        this.num_of_onlines = num_of_onlines;
        this.num_of_guests = num_of_guests;
        this.num_of_non_managers_and_owners = num_of_non_managers_and_owners;
        this.managers_but_not_owners_or_founders = managers_but_not_owners_or_founders;
        this.owners_or_founders = owners_or_founders;
    }

    public Statistic() {
    }

    // --------------------- getters -----------------------------------

    public int getNum_of_users() {
        return num_of_users;
    }

    public int getNum_of_onlines() {
        return num_of_onlines;
    }

    public String getInit_system_time() {
        return init_system_time;
    }

    public long getLogin_per_minutes() {
        return login_per_minutes;
    }

    public long getLogout_per_minutes() {
        return logout_per_minutes;
    }

    public long getConnect_per_minutes() {
        return connect_per_minutes;
    }

    public long getRegister_per_minutes() {
        return register_per_minutes;
    }

    public long getBuy_cart_per_minutes() {
        return buy_cart_per_minutes;
    }

    public int getNum_of_guests() {
        return num_of_guests;
    }

    public int getNum_of_non_managers_and_owners() {
        return num_of_non_managers_and_owners;
    }

    public int getManagers_but_not_owners_or_founders() {
        return managers_but_not_owners_or_founders;
    }

    public int getOwners_or_founders() {
        return owners_or_founders;
    }

    // ------------------------------- setters ------------------------------------

    public void setInit_system_time(String init_system_time) {
        this.init_system_time = init_system_time;
    }

    public void setLogin_per_minutes(long login_per_minutes) {
        this.login_per_minutes = login_per_minutes;
    }

    public void setLogout_per_minutes(long logout_per_minutes) {
        this.logout_per_minutes = logout_per_minutes;
    }

    public void setConnect_per_minutes(long connect_per_minutes) {
        this.connect_per_minutes = connect_per_minutes;
    }

    public void setRegister_per_minutes(long register_per_minutes) {
        this.register_per_minutes = register_per_minutes;
    }

    public void setBuy_cart_per_minutes(long buy_cart_per_minutes) {
        this.buy_cart_per_minutes = buy_cart_per_minutes;
    }

    public void setNum_of_users(int num_of_users) {
        this.num_of_users = num_of_users;
    }

    public void setNum_of_onlines(int num_of_onlines) {
        this.num_of_onlines = num_of_onlines;
    }

    public void setNum_of_guests(int num_of_guests) {
        this.num_of_guests = num_of_guests;
    }

    public void setNum_of_non_managers_and_owners(int num_of_non_managers_and_owners) {
        this.num_of_non_managers_and_owners = num_of_non_managers_and_owners;
    }

    public void setManagers_but_not_owners_or_founders(int managers_but_not_owners_or_founders) {
        this.managers_but_not_owners_or_founders = managers_but_not_owners_or_founders;
    }

    public void setOwners_or_founders(int owners_or_founders) {
        this.owners_or_founders = owners_or_founders;
    }
}
