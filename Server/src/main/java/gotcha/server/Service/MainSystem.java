package gotcha.server.Service;

import gotcha.server.Config.Configuration;
import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.StatisticsModule.StatisticsManager;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.ExternalService.MapsAdapterImpl;
import gotcha.server.ExternalService.ReporterAdapter;
import gotcha.server.Utils.Exceptions.ExitException;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.SystemLogger;
import gotcha.server.Utils.Threads.ConnectThread;
import gotcha.server.Utils.Threads.StatisticsUpdateThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
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
    private final StatisticsManager statisticsManager;
    private final SystemLogger systemLogger;
    private final ErrorLogger errorLogger;
    private final MapsAdapter maps_adapter;

    @Autowired
    public MainSystem(Configuration configuration, UserController userController, HazardController hazardController, RidesController ridesController, AdvertiseController advertiseController, SystemLogger systemLogger, ErrorLogger errorLogger, MapsAdapterImpl mapsAdapter, StatisticsManager statisticsManager){
        this.configuration = configuration;
        this.userController = userController;
        this.hazardController = hazardController;
        this.ridesController = ridesController;
        this.advertiseController = advertiseController;
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
        begin_instructions();
        systemLogger.add_log("Finish Init Server");
    }

    private void set_reporter_engine() {
        ReporterAdapter reporterAdapter = hazardController.getReporterAdapter();
        reporterAdapter.setSystem_email(configuration.getSystemEmail());
        reporterAdapter.setSystem_email_password(configuration.getSystemEmailPassword());
        reporterAdapter.setOR_YARUK_email(configuration.getOrYarukEmail());
        reporterAdapter.setCities_emails(configuration.getCities_emails());
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
            System.out.println(configuration.getDatabaseUrl());
            System.out.println(configuration.getDatabasePassword());
            HibernateUtils.set_tests_mode();
            systemLogger.add_log("Tests Database Connected Successfully");
        }

        else if (configuration.getDatabaseMode().equals("real")){
            // TODO: 30/12/2022 : have to connect to DB with DB_URL & DB_password.
            System.out.println(configuration.getDatabaseUrl());
            System.out.println(configuration.getDatabasePassword());
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
        System.out.println("Here we should create Config//rp_config.txt File.");
        System.out.println(configuration.getMinimumDistanceAlert());
    }
    private void set_first_admin() throws Exception {
        LocalDate birth_date = LocalDate.now();
        // TODO: 3/26/2023 : Need to add all parameters to config file
        userController.add_first_admin(configuration.getAdminUserName(), "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
    }

    /**
     * this method will make the statistics module updated every 24 hours.
     */
    private void set_statistics_update_thread() {
        try{
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            StatisticsUpdateThread statistics_update_thread = new StatisticsUpdateThread();
            executorService.scheduleAtFixedRate(statistics_update_thread, 0, 24, TimeUnit.HOURS);
        }
        catch (Exception e) {
            errorLogger.add_log("fail to update statistics :"+e.getMessage());
        }
    }


    private void begin_instructions() {
        String EMAIL = "moskoga@gmail.com";
        String PASSWORD = "123456Aa";
        String NAME = "AMIT";
        String LAST_NAME = "MOSKO";
        String BIRTH_DATE = "19-04-95";
        LocalDate BIRTH_DAY = LocalDate.of(1995, 4,19);
        String PHONE = "0546794211";
        String GENDER = "MALE";
        BigDecimal lng = new BigDecimal("0.0");
        BigDecimal lat = new BigDecimal("0.0");
        Location origin = new Location(lng, lat);
        Location dest = new Location(lng, lat);
        String city = "B7";
        LocalDateTime start_time = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.now();
        StationaryHazard hazard = new StationaryHazard(5,6,origin,city, HazardType.PoleTree, 16.5);
        ArrayList<StationaryHazard> hazards = new ArrayList<>();
        hazards.add(hazard);
        //
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
