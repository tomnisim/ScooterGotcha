package gotcha.server.Domain.StatisticsModule;

import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.AwardsModule.IAwardsController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.UserController;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class DailyStatistic {
    private LocalDate date;
    private int start_day__advertisements;
    private int total_advertisements;
    private int start_day__awards;
    private int total_awards;
    private int start_day__hazards;
    private int total_hazards;
    private int start_day__users_questions;
    private int total_users_questions;
    private int start_day__admin_answers;
    private int total_admin_answers;
    private int start_day__rides;
    private int total_rides;


    private int start_day__users; // registers
    private int total_users;
    private int start_day__admins;
    private int total_admins;
    private AtomicInteger online_users; // login
    private AtomicInteger online_guests; // logout
    private AtomicInteger shut_down_events;
    private AtomicInteger reset_events;




    // ----------------------- constructors ---------------------------------------------
    public DailyStatistic(int start_day__admins, int start_day__admin_answers,int start_day__advertisements,int start_day__awards,
                          int start_day__rides, int start_day__hazards,
                          int start_day__users, int start_day__users_questions) {
        this.date = LocalDate.now();

        this.start_day__advertisements = start_day__advertisements;
        this.start_day__awards = start_day__awards;
        this.start_day__hazards = start_day__hazards;
        this.start_day__users_questions = start_day__users_questions;
        this.start_day__admin_answers = start_day__admin_answers;
        this.start_day__rides = start_day__rides;
        this.start_day__users = start_day__users;
        this.start_day__admins = start_day__admins;

        this.update(start_day__admins, start_day__admin_answers, start_day__advertisements, start_day__awards, start_day__rides,
                start_day__hazards, start_day__users, start_day__users_questions);

        this.reset_events = new AtomicInteger(0);
        this.shut_down_events = new AtomicInteger(0);
        this.online_guests = new AtomicInteger(0);
        this.online_users = new AtomicInteger(0);
    }



    /**
     * Constructor for DB loading
     * @param date
     * @param start_day__advertisements
     * @param total_advertisements
     * @param start_day__awards
     * @param total_awards
     * @param start_day__hazards
     * @param total_hazards
     * @param start_day__users_questions
     * @param total_users_questions
     * @param start_day__admin_answers
     * @param total_admin_answers
     * @param start_day__rides
     * @param total_rides
     * @param start_day__users
     * @param total_users
     * @param start_day__admins
     * @param total_admins
     * @param online_users
     * @param online_guests
     * @param shut_down_events
     * @param reset_events
     */
    public void load(LocalDate date, int start_day__advertisements, int total_advertisements, int start_day__awards, int total_awards, int start_day__hazards, int total_hazards, int start_day__users_questions, int total_users_questions, int start_day__admin_answers, int total_admin_answers, int start_day__rides, int total_rides, int start_day__users, int total_users, int start_day__admins, int total_admins, int online_users, int online_guests, int shut_down_events, int reset_events) {
        this.date = date;
        this.start_day__advertisements = start_day__advertisements;
        this.total_advertisements = total_advertisements;
        this.start_day__awards = start_day__awards;
        this.total_awards = total_awards;
        this.start_day__hazards = start_day__hazards;
        this.total_hazards = total_hazards;
        this.start_day__users_questions = start_day__users_questions;
        this.total_users_questions = total_users_questions;
        this.start_day__admin_answers = start_day__admin_answers;
        this.total_admin_answers = total_admin_answers;
        this.start_day__rides = start_day__rides;
        this.total_rides = total_rides;
        this.start_day__users = start_day__users;
        this.total_users = total_users;
        this.start_day__admins = start_day__admins;
        this.total_admins = total_admins;
        this.online_users = new AtomicInteger(online_users);
        this.online_guests = new AtomicInteger(online_guests);
        this.shut_down_events = new AtomicInteger(shut_down_events);
        this.reset_events = new AtomicInteger(reset_events);
    }



    // --------------------- getters -----------------------------------


    public LocalDate getDate() {
        return date;
    }

    public int getNew_advertisements() {
        return total_advertisements - start_day__advertisements;
    }

    public int getTotal_advertisements() {
        return total_advertisements;
    }

    public int getNew_awards() {
        return total_awards - start_day__awards;
    }

    public int getTotal_awards() {
        return total_awards;
    }

    public int getNew_hazards() {
        return total_hazards - start_day__hazards;
    }

    public int getTotal_hazards() {
        return total_hazards;
    }

    public int getNew_users_questions() {
        return total_users_questions - start_day__users_questions;
    }

    public int getTotal_users_questions() {
        return total_users_questions;
    }

    public int getNew_admin_answers() {
        return total_admin_answers - start_day__admin_answers;
    }

    public int getTotal_admin_answers() {
        return total_admin_answers;
    }

    public int getNew_rides() {
        return total_rides - start_day__rides;
    }

    public int getTotal_rides() {
        return total_rides;
    }

    public int getNew_users() {
        return total_users - start_day__users;
    }

    public int getTotal_users() {
        return total_users;
    }

    public int getNew_admins() {
        return total_admins - start_day__admins;
    }

    public int getTotal_admins() {
        return total_admins;
    }

    public int getOnline_users() {
        return online_users.get();
    }

    public void incOnline_users() {
        this.online_users.incrementAndGet();
    }

    public int getOnline_guests() {
        return online_guests.get();
    }

    public void incOnline_guests() {
        this.online_guests.incrementAndGet();
    }

    public int getShut_down_events() {
        return shut_down_events.get();
    }

    public void incShut_down_events() {
        this.shut_down_events.incrementAndGet();
    }

    public int getReset_events() {
        return reset_events.get();
    }

    public void incReset_events() {
        this.reset_events.incrementAndGet();
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotal_advertisements(int total_advertisements) {
        this.total_advertisements = total_advertisements;
    }

    public void setTotal_awards(int total_awards) {
        this.total_awards = total_awards;
    }

    public void setTotal_hazards(int total_hazards) {
        this.total_hazards = total_hazards;
    }

    public void setTotal_users_questions(int total_users_questions) {
        this.total_users_questions = total_users_questions;
    }

    public void setTotal_admin_answers(int total_admin_answers) {
        this.total_admin_answers = total_admin_answers;
    }

    public void setTotal_rides(int total_rides) {
        this.total_rides = total_rides;
    }

    public void setTotal_users(int total_users) {
        this.total_users = total_users;
    }

    public void setTotal_admins(int total_admins) {
        this.total_admins = total_admins;
    }

    public void setOnline_users(int online_users) {
        this.online_users = new AtomicInteger(online_users);
    }

    public void setOnline_guests(int online_guests) {
        this.online_guests = new AtomicInteger(online_guests);
    }

    public void setShut_down_events(int shut_down_events) {
        this.shut_down_events = new AtomicInteger(shut_down_events);
    }

    public void setReset_events(int reset_events) {
        this.reset_events = new AtomicInteger(reset_events);
    }


    public void update(int total_admins, int total_admin_answers, int total_advertisements, int total_awards, int total_rides,
                       int total_hazards, int total_users, int total_users_questions) {
        this.setTotal_admins(total_admins);
        this.setTotal_advertisements(total_advertisements);
        this.setTotal_awards(total_awards);
        this.setTotal_admin_answers(total_admin_answers);
        this.setTotal_rides(total_rides);
        this.setTotal_users(total_users);
        this.setTotal_users_questions(total_users_questions);
        this.setTotal_hazards(total_hazards);

    }

    public DailyStatisticDAO getDAO() {
        int new_admins = getNew_admins();
        int new_users = getNew_users();
        int new_advertisements = getNew_advertisements();
        int new_awards = getNew_awards();
        int new_rides = getNew_rides();
        int new_users_questions = getNew_users_questions();
        int new_admin_answers = getNew_admin_answers();
        int new_hazards = getNew_hazards();

        return new DailyStatisticDAO(this.date, new_admins, total_admins, new_users, total_users, new_advertisements, total_advertisements,
                new_awards, total_awards, new_rides, total_rides, new_users_questions, total_users_questions,
                new_admin_answers,total_admin_answers, new_hazards, total_hazards, this.online_users.get(), this.online_guests.get(),this.shut_down_events.get(),this.reset_events.get());
    }
}
