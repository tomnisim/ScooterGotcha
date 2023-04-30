package gotcha.server.Domain.StatisticsModule;

import java.time.LocalDate;
/**
 * this DAO is for ADMIN application.
 */
public class DailyStatisticDAO {

    private LocalDate date;
    private int newAdmins;
    private int totalAdmins;
    private int newUsers;
    private int totalUsers;
    private int newAdvertisements;
    private int totalAdvertisements;
    private int newAwards;
    private int totalAwards;
    private int newRides;
    private int totalRides;
    private int newUsersQuestions;
    private int totalUsersQuestions;
    private int newAdminAnswers;
    private int totalAdminAnswers;
    private int newHazards;
    private int totalHazards;
    private int loginEvents;
    private int logoutEvents;
    private int shutDownEvents;
    private int resetEvents;

    public DailyStatisticDAO(DailyStatistic dailyStatistic) {
        this.date = dailyStatistic.getDate();
        this.newAdmins = dailyStatistic.getNew_admins();
        this.totalAdmins = dailyStatistic.getTotal_admins();
        this.newUsers = dailyStatistic.getNew_users();
        this.totalUsers = dailyStatistic.getTotal_users();
        this.newAdvertisements = dailyStatistic.getNew_advertisements();
        this.totalAdvertisements = dailyStatistic.getTotal_advertisements();
        this.newAwards = dailyStatistic.getNew_awards();
        this.totalAwards = dailyStatistic.getTotal_awards();
        this.newRides = dailyStatistic.getNew_rides();
        this.totalRides = dailyStatistic.getTotal_rides();
        this.newUsersQuestions = dailyStatistic.getNew_users_questions();
        this.totalUsersQuestions = dailyStatistic.getTotal_users_questions();
        this.newAdminAnswers = dailyStatistic.getNew_admin_answers();
        this.totalAdminAnswers = dailyStatistic.getTotal_admin_answers();
        this.newHazards = dailyStatistic.getNew_hazards();
        this.totalHazards = dailyStatistic.getTotal_hazards();
        this.loginEvents = dailyStatistic.getOnline_users();
        this.logoutEvents = dailyStatistic.getOnline_guests();
        this.resetEvents = dailyStatistic.getReset_events();
        this.shutDownEvents = dailyStatistic.getShut_down_events();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNewAdmins() {
        return newAdmins;
    }

    public void setNewAdmins(int newAdmins) {
        this.newAdmins = newAdmins;
    }

    public int getTotalAdmins() {
        return totalAdmins;
    }

    public void setTotalAdmins(int totalAdmins) {
        this.totalAdmins = totalAdmins;
    }

    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getNewAdvertisements() {
        return newAdvertisements;
    }

    public void setNewAdvertisements(int newAdvertisements) {
        this.newAdvertisements = newAdvertisements;
    }

    public int getTotalAdvertisements() {
        return totalAdvertisements;
    }

    public void setTotalAdvertisements(int totalAdvertisements) {
        this.totalAdvertisements = totalAdvertisements;
    }

    public int getNewAwards() {
        return newAwards;
    }

    public void setNewAwards(int newAwards) {
        this.newAwards = newAwards;
    }

    public int getTotalAwards() {
        return totalAwards;
    }

    public void setTotalAwards(int totalAwards) {
        this.totalAwards = totalAwards;
    }

    public int getNewRides() {
        return newRides;
    }

    public void setNewRides(int newRides) {
        this.newRides = newRides;
    }

    public int getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(int totalRides) {
        this.totalRides = totalRides;
    }

    public int getNewUsersQuestions() {
        return newUsersQuestions;
    }

    public void setNewUsersQuestions(int newUsersQuestions) {
        this.newUsersQuestions = newUsersQuestions;
    }

    public int getTotalUsersQuestions() {
        return totalUsersQuestions;
    }

    public void setTotalUsersQuestions(int totalUsersQuestions) {
        this.totalUsersQuestions = totalUsersQuestions;
    }

    public int getNewAdminAnswers() {
        return newAdminAnswers;
    }

    public void setNewAdminAnswers(int newAdminAnswers) {
        this.newAdminAnswers = newAdminAnswers;
    }

    public int getTotalAdminAnswers() {
        return totalAdminAnswers;
    }

    public void setTotalAdminAnswers(int totalAdminAnswers) {
        this.totalAdminAnswers = totalAdminAnswers;
    }

    public int getNewHazards() {
        return newHazards;
    }

    public void setNewHazards(int newHazards) {
        this.newHazards = newHazards;
    }

    public int getTotalHazards() {
        return totalHazards;
    }

    public void setTotalHazards(int totalHazards) {
        this.totalHazards = totalHazards;
    }

    public int getLoginEvents() {
        return loginEvents;
    }

    public void setLoginEvents(int loginEvents) {
        this.loginEvents = loginEvents;
    }

    public int getLogoutEvents() {
        return logoutEvents;
    }

    public void setLogoutEvents(int logoutEvents) {
        this.logoutEvents = logoutEvents;
    }

    public int getShutDownEvents() {
        return shutDownEvents;
    }

    public void setShutDownEvents(int shutDownEvents) {
        this.shutDownEvents = shutDownEvents;
    }

    public int getResetEvents() {
        return resetEvents;
    }

    public void setResetEvents(int resetEvents) {
        this.resetEvents = resetEvents;
    }
}
