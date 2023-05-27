package gotcha.server.Service;

import gotcha.server.Config.Configuration;
import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.QuestionsModule.QuestionController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.SafeRouteCalculatorModule.RoutesRetriever;
import gotcha.server.Domain.StatisticsModule.StatisticsManager;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.Domain.ExternalService.MapsAdapter;
import gotcha.server.Domain.ExternalService.MapsAdapterImpl;
import gotcha.server.Domain.HazardsModule.ReporterAdapter;
import gotcha.server.Domain.s3.S3Service;
import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Utils.Exceptions.ExitException;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;
import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.SystemLogger;
import gotcha.server.Utils.Threads.HazardsReporterThread;
import gotcha.server.Utils.Threads.StatisticsUpdateThread;
import gotcha.server.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static gotcha.server.Utils.Utils.generateSerialNumber;

@Component
public class MainSystem {
    private final Configuration configuration;
    private final UserController userController;
    private final HazardController hazardController;
    private final RidesController ridesController;
    private final AdvertiseController advertiseController;
    private final QuestionController questionController;
    private final StatisticsManager statisticsManager;
    private final RoutesRetriever routesRetriever;
    private final SystemLogger systemLogger;
    private final ErrorLogger errorLogger;
    private final MapsAdapter maps_adapter;
    private final S3Service s3Service;

    @Autowired
    public MainSystem(Configuration configuration, UserController userController, HazardController hazardController, RidesController ridesController, AdvertiseController advertiseController, SystemLogger systemLogger, ErrorLogger errorLogger, MapsAdapterImpl mapsAdapter, StatisticsManager statisticsManager, QuestionController questionController, RoutesRetriever routesRetriever, S3Service s3Service){
        this.configuration = configuration;
        this.userController = userController;
        this.hazardController = hazardController;
        this.ridesController = ridesController;
        this.advertiseController = advertiseController;
        this.questionController = questionController;
        this.statisticsManager = statisticsManager;
        this.routesRetriever = routesRetriever;
        this.systemLogger = systemLogger;
        this.errorLogger = errorLogger;
        this.maps_adapter = mapsAdapter;
        this.s3Service = s3Service;
    }

    public void init_server() throws Exception {
        systemLogger.add_log("Start Init Server");
        connect_to_external_services();
//        if (configuration.getFirstTimeRunning())
//            set_first_admin();
        set_statistics_update_thread();
        set_reporter_engine();
        this.hazardController.setHAZARD_THRESHOLD_RATE(configuration.getHazards_rate_threshold());
        //createSerials();
        begin_instructions();
        //saveAllHazardsImages();
        systemLogger.add_log("Finish Init Server");
    }

    private void saveAllHazardsImages() {
        var allHazards = hazardController.view_hazards();
        int i=1;
        for (var hazard  : allHazards) {
            var imageBytes = hazard.getPhoto();
            try {
                var targetDirectory = "src/main/java/gotcha/server/Service/" + "hazardImage" + i + ".jpg";
                i++;
                Files.write(Path.of(targetDirectory), imageBytes, StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private void createSerials() throws Exception {
        for (int i=1;i<1001;i++) {
            this.userController.add_rp_serial_number(String.valueOf(i));
        }
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
            executorService.scheduleAtFixedRate(hazardsReporterThread, 0, configuration.getHazards_time_to_report_in_minutes(), TimeUnit.MINUTES);
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



    private void set_first_admin() throws Exception {
        LocalDate birth_date = LocalDate.now();
        userController.add_first_admin(configuration.getAdminUserName(), "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
    }

    /**
     * this method will make the statistics module updated every STATISTIC_TIME_TO_UPDATE minutes.
     */
    private void set_statistics_update_thread() {
        try{
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            StatisticsUpdateThread statistics_update_thread = new StatisticsUpdateThread(statisticsManager, systemLogger);
            executorService.scheduleAtFixedRate(statistics_update_thread, 0, configuration.getStatistics_time_to_update_in_minutes(), TimeUnit.MINUTES);
        }
        catch (Exception e) {
            errorLogger.add_log("fail to update statistics :"+e.getMessage());
        }
    }


    private void begin_instructions() throws Exception {
        this.userController.add_rp_serial_number(generateSerialNumber());
        this.userController.add_rp_serial_number(generateSerialNumber());
        this.userController.add_rp_serial_number(generateSerialNumber());
        this.userController.add_rp_serial_number(generateSerialNumber());
        this.userController.add_rp_serial_number(generateSerialNumber());
        this.userController.add_rp_serial_number(generateSerialNumber());
        this.userController.add_rp_serial_number(generateSerialNumber());
        this.userController.add_rp_serial_number("first");
        this.userController.add_rp_serial_number("first1");
        this.userController.add_rp_serial_number("first12");
        this.userController.add_rp_serial_number("first123");
        this.userController.add_rp_serial_number("first1234");
        this.userController.add_rp_serial_number("first12345");
        this.userController.add_rp_serial_number("first123456");

        BigDecimal lng = new BigDecimal("34.801402");
        BigDecimal lat = new BigDecimal("31.265106");
        Location origin = new Location(lng, lat);

        BigDecimal lng1 = new BigDecimal("34.797558");
        BigDecimal lat1 = new BigDecimal("31.267604");
        Location dest = new Location(lng1, lat1);
        Location hazard_location = new Location( new BigDecimal("34.769943"),  new BigDecimal("32.063047"));
        Location hazard_location2 = new Location( new BigDecimal("34.765202"),  new BigDecimal("32.068184"));
        Location hazard_location3 = new Location( new BigDecimal("34.769849"),  new BigDecimal("32.0636256"));

        String[] addresses = this.routesRetriever.getAddresses(new LocationDTO(origin), new LocationDTO(dest));
        String originAddress = addresses[0];
        String destAddress = addresses[1];
        String[] addresses1 = this.routesRetriever.getAddresses(new LocationDTO(hazard_location2), new LocationDTO(hazard_location3));
        String originAddress1 = addresses1[0];
        String destAddress1 = addresses1[1];


        BigDecimal lng111 = new BigDecimal("34.80283154");
        BigDecimal lat111 = new BigDecimal("32.1246251");
        Location origin111 = new Location(lng111, lat111);

        BigDecimal lng222 = new BigDecimal("34.79586550");
        BigDecimal lat222 = new BigDecimal("32.11289542");
        Location dest222 = new Location(lng222, lat222);

        ArrayList hazards = new ArrayList();




        //this.hazardController.add_hazard(515, origin, "Tel-Aviv", HazardType.PoleTree, 16.5, photo);

        byte[] imageData;

        try {
            // Get the path of the image file relative to the MainSystem class
            Path imagePath = Paths.get("src/main/java/gotcha/server/Service/pothole1.jpg");

            // Read all bytes from the image file into a byte array
            imageData = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            System.err.println("Error reading image file: " + e.getMessage());
            imageData = new byte[0]; // fallback to an empty array if there's an error
        }

        LocalDateTime start_time = LocalDateTime.now();
//        if (this.hazardController.isDbEmpty()) {
//            this.hazardController.add_hazard(5, origin, "Tel-Aviv", HazardType.PoleTree, 16.5, imageData);
//            this.hazardController.add_hazard(5, origin, "Tel-Aviv", HazardType.RoadSign, 7,imageData);
//            this.hazardController.add_hazard(5, origin, "Tel-Aviv", HazardType.RoadSign, 12, imageData);
//            this.hazardController.add_hazard(6, origin, "Netanya", HazardType.pothole, 12, imageData);
//            this.hazardController.add_hazard(6, origin, "Netanya", HazardType.pothole, 14, imageData);
//            this.hazardController.add_hazard(6, origin, "Netanya", HazardType.RoadSign, 3, imageData);
//            s3Service.saveAllImages();
//        }
        LocalDate birth_date = LocalDate.of(1995, 05 , 05);
        LocalDate issue = LocalDate.of(2025, 05 , 05);
        String password = "AaAa12345";
        if (userController.isSerialsTableEmpty()) {
            userController.add_rp_serial_number("first");
            userController.add_rp_serial_number("first1");
            userController.add_rp_serial_number("first12");
            userController.add_rp_serial_number("first123");
            userController.add_rp_serial_number("first1234");
            userController.add_rp_serial_number("first12345");
        }

        if (userController.isUsersTableEmpty()) {
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


            userController.add_first_admin("admin1@gmail.com", "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
            userController.add_first_admin("admin12@gmail.com", "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
            userController.add_first_admin("admin123@gmail.com", "name" , "name", configuration.getAdminPassword(), "0546794211",birth_date,"male");
        }
//        FinishRideRequest finishRideReq = new FinishRideRequest("first", new LocationDTO(origin), new LocationDTO(dest), start_time, start_time.plusMinutes(47), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        FinishRideRequest finishRideReq2 = new FinishRideRequest("first", new LocationDTO(hazard_location2), new LocationDTO(hazard_location3), start_time, start_time.plusMinutes(47), hazards, new ArrayList<>(), new ArrayList<>());
//        ridesController.add_ride(finishRideReq, "email@gmail.com", originAddress, destAddress, "Netanya");
//        ridesController.add_ride(finishRideReq2, "email@gmail.com", originAddress1, destAddress1,"Tel-Aviv");
//       userController.send_question_to_admin("email@gmail.com", "Happy Birthday");
//        userController.send_question_to_admin("email@gmail.com", "Happy Birthday with answer");
//        userController.reply_to_user_question("admin1@gmail.com", "Happy Birthday with answer 1", 1);
//        userController.reply_to_user_question("admin1@gmail.com", "Happy Birthday with answer 2 ", 2);
        //this.questionController.answer_user_question(1, "because", "admin@admin.com");
//       if (this.advertiseController.isDbEmpty()) {
//           this.advertiseController.add_advertise(birth_date, "owner", "message", "https://picsum.photos/id/237/200/200", "www.walla.com");
//           this.advertiseController.add_advertise(birth_date, "owner", "message", "https://picsum.photos/id/238/200/200", "www.walla.com");
//       }
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
