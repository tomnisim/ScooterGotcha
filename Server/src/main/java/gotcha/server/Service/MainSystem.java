package gotcha.server.Service;

import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.ExternalService.MapsAdapterImpl;
import gotcha.server.Utils.Exceptions.ExitException;
import gotcha.server.Utils.Logger.SystemLogger;
import gotcha.server.Utils.Utils;
import org.springframework.cglib.core.Local;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainSystem {
    public static String EXTERNAL_SERVICE_MODE, DATABASE_MODE, DATABASE_URL, DATABASE_PASSWORD, SYSTEM_EMAIL, SYSTEM_EMAIL_PASSWORD, ADMIN_USER_NAME,
            ADMIN_PASSWORD;
    public static int MINIMUM_PASSWORD_LENGTH, MAXIMUM_PASSWORD_LENGTH, MINIMUM_DISTANCE_ALERT, NUMBER_OF_ROUTES;
    public static boolean FIRST_TIME_RUNNING;

    private MapsAdapter maps_adapter;

    public MainSystem(String system_config_path) throws Exception {
        this.init_server(system_config_path); // TODO: 29/12/2022 : check when we should call init_first_time.
    }

    public void init_server(String config_file_path) throws Exception {
        SystemLogger.getInstance().add_log("Start Init Server");
        SystemLogger.getInstance().add_log("Configuration File Path: "+config_file_path);

        set_static_vars(config_file_path);
        set_external_services();
        connect_to_external_services();
        create_rp_config_file();
        connect_database();
        load_database();
        if (FIRST_TIME_RUNNING)
            set_first_admin();
    }



    /**
     * reading the data from the configuration file.
     * @param config_file_path the path of the configuration file.
     * @throws ExitException if the format file is unmatched.
     */

    public void set_static_vars(String config_file_path) throws ExitException {
        try {
            File file = new File(config_file_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                String[] instruction_parts = instruction.split(":");
                switch (instruction_parts[0]){
                    case "FIRST_TIME_RUNNING":
                        FIRST_TIME_RUNNING = Utils.string_to_boolean(instruction_parts[1]);
                        break;

                    case "MINIMUM_PASSWORD_LENGTH":
                        MINIMUM_PASSWORD_LENGTH = Utils.string_to_int(instruction_parts[1]);
                        break;
                    case "MAXIMUM_PASSWORD_LENGTH":
                        MAXIMUM_PASSWORD_LENGTH = Utils.string_to_int(instruction_parts[1]);
                        break;
                    case "MINIMUM_DISTANCE_ALERT":
                        MINIMUM_DISTANCE_ALERT = Utils.string_to_int(instruction_parts[1]);
                        break;
                    case "NUMBER_OF_ROUTES":
                        NUMBER_OF_ROUTES = Utils.string_to_int(instruction_parts[1]);
                        break;
                    case "EXTERNAL_SERVICE_MODE":
                        EXTERNAL_SERVICE_MODE = instruction_parts[1];
                        break;
                    case "DATABASE_MODE":
                        DATABASE_MODE = instruction_parts[1];
                        break;
                    case "DATABASE_URL":
                        DATABASE_URL = instruction_parts[1];
                        break;
                    case "DATABASE_PASSWORD":
                        DATABASE_PASSWORD = instruction_parts[1];
                        break;
                    case "SYSTEM_EMAIL":
                        SYSTEM_EMAIL = instruction_parts[1];
                        break;
                    case "SYSTEM_EMAIL_PASSWORD":
                        SYSTEM_EMAIL_PASSWORD = instruction_parts[1];
                        break;
                    case "ADMIN_USER_NAME":
                        ADMIN_USER_NAME = instruction_parts[1];
                        break;
                    case "ADMIN_PASSWORD":
                        ADMIN_PASSWORD = instruction_parts[1];
                        break;

                }

            }
        }
        catch (FileNotFoundException e) {throw new ExitException("Config File - File Not Found");}
        if (EXTERNAL_SERVICE_MODE == null) {throw new ExitException("Config File - Format File Unmatched.");}
    }



    /**
     * this method crate adapters to the external services.
     * EXTERNAL_SERVICE_MODE - "external_services:demo" or "external_services:real"
     * @throws ExitException if the input is illegal.
     */
    private void set_external_services() throws ExitException {
        if (EXTERNAL_SERVICE_MODE.equals("tests")){
            SystemLogger.getInstance().add_log("Set Tests External Services");
            // TODO: 28/12/2022 : implement here new maps adapter implementation when MapsAdapter interface is ready.
            this.maps_adapter = new MapsAdapterImpl();
        }
        else if (EXTERNAL_SERVICE_MODE.equals("real")){
            SystemLogger.getInstance().add_log("Set Real External Services");
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
        SystemLogger.getInstance().add_log("System Start Connect To External Services");
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
        if (DATABASE_MODE.equals("tests")){
            // TODO: 30/12/2022 : have to connect to DB with DB_URL & DB_password.
            System.out.println(DATABASE_URL);
            System.out.println(DATABASE_PASSWORD);
            HibernateUtils.set_tests_mode();
            SystemLogger.getInstance().add_log("Tests Database Connected Successfully");
        }

        else if (DATABASE_MODE.equals("real")){
            // TODO: 30/12/2022 : have to connect to DB with DB_URL & DB_password.
            System.out.println(DATABASE_URL);
            System.out.println(DATABASE_PASSWORD);
            HibernateUtils.set_normal_use();
            SystemLogger.getInstance().add_log("Real Database Connected Successfully");

        }
        else {
            throw new ExitException("System Config File - Illegal Database Mode.");
        }
    }
    private void load_database() {
        // TODO: 29/12/2022 : load all controllers & set admins in the system.
        UserController.get_instance().load();
        HazardController.get_instance().load();
        RidesController.get_instance().load();
        AdvertiseController.get_instance().load();
    }

    private void create_rp_config_file() {
        System.out.println("Here we should create Config//rp_config.txt File.");
        System.out.println(MINIMUM_DISTANCE_ALERT);
    }
    private void set_first_admin() throws Exception {
        LocalDate birth_date = LocalDate.now();
        UserController.get_instance().add_first_admin(ADMIN_USER_NAME, ADMIN_PASSWORD, "0546794211",birth_date,"male");
    }
}
