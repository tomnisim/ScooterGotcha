package gotcha.server.Service;

import gotcha.server.Config.Configuration;
import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.ExternalService.MapsAdapterImpl;
import gotcha.server.Utils.Exceptions.ExitException;
import gotcha.server.Utils.Logger.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MainSystem {
    private final Configuration configuration;
    private final UserController userController;
    private final HazardController hazardController;
    private final RidesController ridesController;
    private final AdvertiseController advertiseController;
    private final SystemLogger systemLogger;
    private MapsAdapter maps_adapter;

    @Autowired
    public MainSystem(Configuration configuration, UserController userController, HazardController hazardController, RidesController ridesController, AdvertiseController advertiseController, SystemLogger systemLogger){
        this.configuration = configuration;
        this.userController = userController;
        this.hazardController = hazardController;
        this.ridesController = ridesController;
        this.advertiseController = advertiseController;
        this.systemLogger = systemLogger;
    }

    public void init_server() throws Exception {
        systemLogger.add_log("Start Init Server");
        configuration.getAdminPassword();
        //set_static_vars(config_file_path);
        set_external_services();
        connect_to_external_services();
        create_rp_config_file();
        connect_database();
        load_database();
        if (configuration.getFirstTimeRunning())
            set_first_admin();
    }

    /**
     * this method crate adapters to the external services.
     * EXTERNAL_SERVICE_MODE - "external_services:demo" or "external_services:real"
     * @throws ExitException if the input is illegal.
     */
    private void set_external_services() throws ExitException {
        if (configuration.getExternalServiceMode().equals("tests")){
            systemLogger.add_log("Set Tests External Services");
            // TODO: 28/12/2022 : implement here new maps adapter implementation when MapsAdapter interface is ready.
            this.maps_adapter = new MapsAdapterImpl();
        }
        else if (configuration.getExternalServiceMode().equals("real")){
            systemLogger.add_log("Set Real External Services");
            this.maps_adapter = new MapsAdapterImpl();
        }
        else {
            throw new ExitException("System Config File - Illegal External Services Data.");
        }
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

    // TODO: Not sure we need to load the database here
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
        userController.add_first_admin(configuration.getAdminUserName(), configuration.getAdminPassword(), "0546794211",birth_date,"male");
    }
}
