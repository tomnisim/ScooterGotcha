package gotcha.server.Service;

import gotcha.server.Config.Configuration;
import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.StatisticsModule.StatisticsManager;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.Domain.ExternalService.MapsAdapter;
import gotcha.server.Domain.ExternalService.MapsAdapterImpl;
import gotcha.server.Domain.HazardsModule.ReporterAdapter;
import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Utils.Exceptions.ExitException;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.SystemLogger;
import gotcha.server.Utils.Threads.HazardsReporterThread;
import gotcha.server.Utils.Threads.StatisticsUpdateThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MainSystem {
    private final Configuration configuration;
    private final UserController userController;
    private final HazardController hazardController;
    private final RidesController ridesController;
    private final AdvertiseController advertiseController;
    private final QuestionController questionController;
    private final StatisticsManager statisticsManager;
    private final SystemLogger systemLogger;
    private final ErrorLogger errorLogger;
    private final MapsAdapter maps_adapter;

    @Autowired
    public MainSystem(Configuration configuration, UserController userController, HazardController hazardController, RidesController ridesController, AdvertiseController advertiseController, SystemLogger systemLogger, ErrorLogger errorLogger, MapsAdapterImpl mapsAdapter, StatisticsManager statisticsManager, QuestionController questionController){
        this.configuration = configuration;
        this.userController = userController;
        this.hazardController = hazardController;
        this.ridesController = ridesController;
        this.advertiseController = advertiseController;
        this.questionController = questionController;
        this.statisticsManager = statisticsManager;
        this.systemLogger = systemLogger;
        this.errorLogger = errorLogger;
        this.maps_adapter = mapsAdapter;
    }

    public void init_server() throws Exception {
        systemLogger.add_log("Start Init Server");
        connect_to_external_services();
        create_rp_config_file();
        connect_database();
        load_database();
        if (configuration.getFirstTimeRunning())
            set_first_admin();
        set_statistics_update_thread();
        set_reporter_engine();
        this.hazardController.setHAZARD_THRESHOLD_RATE(configuration.getHazards_rate_threshold());
        begin_instructions();
        systemLogger.add_log("Finish Init Server");
    }

    private void set_reporter_engine() {
        ReporterAdapter reporterAdapter = hazardController.getReporterAdapter();
        reporterAdapter.setSystem_email(configuration.getSystemEmail());
        reporterAdapter.setSystem_email_password(configuration.getSystemEmailPassword());
        reporterAdapter.setOR_YARUK_email(configuration.getOrYarukEmail());
        reporterAdapter.setCities_emails(configuration.getCities_emails());
        try{
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            HazardsReporterThread hazardsReporterThread = new HazardsReporterThread(hazardController, systemLogger);
            // TODO: change seconds -> minutes
            executorService.scheduleAtFixedRate(hazardsReporterThread, 0, configuration.getHazards_time_to_report_in_minutes(), TimeUnit.SECONDS);
        }
        catch (Exception e) {
            errorLogger.add_log("fail to update statistics :"+e.getMessage());
        }
        systemLogger.add_log("Finish Loading Reporter Engine");

    }


    /** Connect the system to the external services after set the services according the configuration file.
     * @throws ExitException if the handshake fail.
     */
    private void connect_to_external_services() throws ExitException {
        systemLogger.add_log("System Start Connect To External Services");
        boolean connect_to_external_systems = this.maps_adapter.handshake();
        if (!connect_to_external_systems) // have to exit
        {
            throw new ExitException("Cant Connect To The External Systems");
        }
    }


    /**
     * this method init system database,
     * if the demo option on, the system will init data from the data config file,
     *      the init can failed and system keep running without data.
     * if the real option on, the method will try to connect the real database.
     * DATABASE_MODE - configuration instruction - "database:demo" or "database:real".
     * @throws ExitException if the connection to DB fail OR wrong format of the config instruction.
     */


    private void connect_database() throws ExitException {
        if (configuration.getDatabaseMode().equals("tests")){
            // TODO: 30/12/2022 : have to connect to DB with DB_URL & DB_password.
//            System.out.println(configuration.getDatabaseUrl());
//            System.out.println(configuration.getDatabasePassword());
            HibernateUtils.set_tests_mode();
            systemLogger.add_log("Tests Database Connected Successfully");
        }

        else if (configuration.getDatabaseMode().equals("real")){
            // TODO: 30/12/2022 : have to connect to DB with DB_URL & DB_password.
//            System.out.println(configuration.getDatabaseUrl());
//            System.out.println(configuration.getDatabasePassword());
            HibernateUtils.set_normal_use();
            systemLogger.add_log("Real Database Connected Successfully");

        }
        else {
            throw new ExitException("System Config File - Illegal Database Mode.");
        }
    }

    // TODO: Not sure we need to load the database here, Need to create a Repository object according to Spring Boot guidelines to access DB
    private void load_database() {
        // TODO: 29/12/2022 : load all controllers & set admins in the system.
        userController.load();
        hazardController.load();
        ridesController.load();
        advertiseController.load();
    }

    private void create_rp_config_file() {
//        System.out.println(configuration.getMinimumDistanceAlert());
    }
    private void set_first_admin() throws Exception {
        LocalDate birth_date = LocalDate.now();
        // TODO: 3/26/2023 : Need to add all parameters to config file
        userController.add_first_admin(configuration.getAdminUserName(), "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
    }

    /**
     * this method will make the statistics module updated every STATISTIC_TIME_TO_UPDATE minutes.
     */
    private void set_statistics_update_thread() {
        try{
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            StatisticsUpdateThread statistics_update_thread = new StatisticsUpdateThread(statisticsManager, systemLogger);
            // TODO: change seconds -> minutes
            executorService.scheduleAtFixedRate(statistics_update_thread, 0, configuration.getStatistics_time_to_update_in_minutes(), TimeUnit.SECONDS);
        }
        catch (Exception e) {
            errorLogger.add_log("fail to update statistics :"+e.getMessage());
        }
    }


    private void begin_instructions() throws Exception {
        BigDecimal lng = new BigDecimal("34.801402");
        BigDecimal lat = new BigDecimal("31.265106");
        Location origin = new Location(lng, lat);

        BigDecimal lng1 = new BigDecimal("34.797558");
        BigDecimal lat1 = new BigDecimal("31.267604");
        Location dest = new Location(lng1, lat1);


        BigDecimal lng111 = new BigDecimal("34.80283154");
        BigDecimal lat111 = new BigDecimal("32.1246251");
        Location origin111 = new Location(lng111, lat111);

        BigDecimal lng222 = new BigDecimal("34.79586550");
        BigDecimal lat222 = new BigDecimal("32.11289542");
        Location dest222 = new Location(lng222, lat222);

        ArrayList hazards = new ArrayList();

        LocalDateTime start_time = LocalDateTime.now();
        this.hazardController.add_hazard(5, origin, "Tel-Aviv", HazardType.PoleTree, 16.5);
        this.hazardController.add_hazard(5, origin, "Tel-Aviv", HazardType.RoadSign, 7);
        this.hazardController.add_hazard(5, origin, "Tel-Aviv", HazardType.RoadSign, 12);
        this.hazardController.add_hazard(6, origin, "Netanya", HazardType.pothole, 12);
        this.hazardController.add_hazard(6, origin, "Netanya", HazardType.pothole, 14);
        this.hazardController.add_hazard(6, origin, "Netanya", HazardType.RoadSign, 3);
        LocalDate birth_date = LocalDate.of(1995, 05 , 05);
        LocalDate issue = LocalDate.of(2025, 05 , 05);
        String password = "AaAa12345";
        userController.add_rp_serial_number("first");
        userController.add_rp_serial_number("first1");
        userController.add_rp_serial_number("first12");
        userController.add_rp_serial_number("first123");
        userController.add_rp_serial_number("first1234");
        userController.add_rp_serial_number("first12345");



        userController.register("email@gmail.com", password, "name", "last", "0546794211",
                birth_date, "male", "type", issue, "first");
        userController.register("email1@gmail.com", password, "name", "last", "0546794211",
                birth_date, "male", "type", issue, "first1");
        userController.register("email12@gmail.com", password, "name", "last", "0546794211",
                birth_date, "male", "type", issue, "first12");
        userController.register("email123@gmail.com", password, "name", "last", "0546794211",
                birth_date, "male", "type", issue, "first123");
//        (String rpSerialNumber, Location origin, Location destination, String city, LocalDateTime startTime, LocalDateTime endTime, List<StationaryHazard> hazards, List< RidingAction > ridingActions) {
//            this.rpSerialNumber = rpSerialNumber;
//        FinishRideRequest finishRideReq = new FinishRideRequest("first", origin, dest, "Netanya", start_time, start_time, hazards, new ArrayList<>(), new ArrayList<>());
//        FinishRideRequest finishRideReq2 = new FinishRideRequest("first", origin111, dest222, "Tel-Aviv", start_time, start_time, hazards, new ArrayList<>(), new ArrayList<>());
//        ridesController.add_ride(finishRideReq, "email@gmail.com");
//        ridesController.add_ride(finishRideReq2, "email@gmail.com");

        userController.add_first_admin("admin1@gmail.com", "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
        userController.add_first_admin("admin12@gmail.com", "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
        userController.add_first_admin("admin123@gmail.com", "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
       userController.send_question_to_admin("email@gmail.com", "Happy Birthday");
       userController.send_question_to_admin("email@gmail.com", "Happy Birthday with answer");
       this.questionController.answer_user_question(1, "because", "admin@admin.com");
        this.advertiseController.add_advertise(birth_date, "owner", "message", "https://picsum.photos/id/237/200/200", "www.walla.com");

        this.advertiseController.add_advertise(birth_date, "owner", "message", "https://picsum.photos/id/238/200/200", "www.walla.com");
//        Facade user_facade = new Facade();
//        Facade admin_facade = new Facade();
//        user_facade.register(EMAIL, PASSWORD, NAME, LAST_NAME, BIRTH_DATE, PHONE, GENDER);
//        user_facade.login(EMAIL, PASSWORD);
//        admin_facade.login(configuration.getAdminUserName(), configuration.getAdminPassword());
////        admin_facade.add_advertisement(start_time, "owner", "mes", "photo", "url");
////        admin_facade.add_advertisement(start_time, "owner2", "mes2", "photo2", "url2");
////        user_facade.add_user_question("????");
////        admin_facade.add_admin(EMAIL+"mmm", PASSWORD, PHONE, BIRTH_DAY, GENDER);
//        admin_facade.logout();

//        user_facade.finish_ride(1, origin, dest, city, start_time, end_time, hazards);
    }
}
