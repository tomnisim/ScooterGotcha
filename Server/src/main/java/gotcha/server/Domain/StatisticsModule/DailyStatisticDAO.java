package gotcha.server.Domain.StatisticsModule;

import java.time.LocalDate;

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

    public DailyStatisticDAO(LocalDate date, int newAdmins, int totalAdmins, int newUsers, int totalUsers, int newAdvertisements, int totalAdvertisements, int newAwards, int totalAwards, int newRides, int totalRides, int newUsersQuestions, int totalUsersQuestions, int newAdminAnswers, int totalAdminAnswers, int newHazards, int totalHazards) {
        this.date = date;
        this.newAdmins = newAdmins;
        this.totalAdmins = totalAdmins;
        this.newUsers = newUsers;
        this.totalUsers = totalUsers;
        this.newAdvertisements = newAdvertisements;
        this.totalAdvertisements = totalAdvertisements;
        this.newAwards = newAwards;
        this.totalAwards = totalAwards;
        this.newRides = newRides;
        this.totalRides = totalRides;
        this.newUsersQuestions = newUsersQuestions;
        this.totalUsersQuestions = totalUsersQuestions;
        this.newAdminAnswers = newAdminAnswers;
        this.totalAdminAnswers = totalAdminAnswers;
        this.newHazards = newHazards;
        this.totalHazards = totalHazards;
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
}
