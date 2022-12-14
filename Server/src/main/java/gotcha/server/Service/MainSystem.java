package gotcha.server.Service;

import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.AdvertiseModule.AdvertiseController;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.RidesModule.RidesController;
import gotcha.server.Domain.UserModule.UserController;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.ExternalService.MapsAdapterImpl;
import gotcha.server.ExternalService.MapsAdapterTests;
import gotcha.server.Utils.Exceptions.ExitException;
import gotcha.server.Utils.Logger.SystemLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainSystem {

    private MapsAdapter maps_adapter;

    public void init_server(String config_file_path) throws ExitException {
        SystemLogger.getInstance().add_log("Start Init Market");
        SystemLogger.getInstance().add_log("Configuration File Path: "+config_file_path);
        String[] instructions;
        instructions = read_config_file(config_file_path);
        String external_services_instruction = instructions[0];
        set_external_services(external_services_instruction);
        connect_to_external_services();
        String database_instruction = instructions[1];
        set_database(database_instruction);


    }
    /**
     * reading the data from the configuration file.
     * @param config_path the path of the configuration file.
     * @return the 2 config instructions, 1) external services 2) database
     * @throws ExitException if the format file is unmatched.
     */
    public String[] read_config_file(String config_path) throws ExitException {
        String[] to_return = new String[2];
        int counter = 0;
        try {
            File file = new File(config_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                if (!instruction.equals("")) {
                    if (counter > 1){
                        throw new ExitException("Config File - Illegal Format.");
                    }
                    to_return[counter]  = instruction;
                    counter++;
                }
            }
        }
        catch (FileNotFoundException e) {throw new ExitException("Config File - File Not Found");}
        if (counter != 2) {throw new ExitException("Config File - Format File Unmatched.");}
        return to_return;
    }

    /**
     * this method crate adapters to the external services.
     * @param config - "external_services:demo" or "external_services:real"
     * @throws ExitException if the input is illegal.
     */
    public void set_external_services(String config) throws ExitException {
        if (config.equals("external_services:tests")){
            SystemLogger.getInstance().add_log("Set Tests External Services");
            this.maps_adapter = new MapsAdapterTests();
        }
        else if (config.equals("external_services:real")){
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
     * @param config - configuration instruction - "database:demo" or "database:real".
     * @throws ExitException if the connection to DB fail OR wrong format of the config instruction.
     */
    private void set_database(String config) throws ExitException{
        if (config.equals("database:tests")){
            // no db
            SystemLogger.getInstance().add_log("Init Data For Tests: No Database");
            HibernateUtils.set_tests_mode();
        }

        else if (config.equals("database:real")){
            try
            {
                SystemLogger.getInstance().add_log("Init Data From Database");
                HibernateUtils.set_normal_use();

                UserController.get_instance().load();
                HazardController.get_instance().load();
                RidesController.get_instance().load();
                AdvertiseController.get_instance().load();
            }
            catch (Exception e){
                throw new ExitException("Cant Connect To Database.");
            }
        }
        else {
            throw new ExitException("System Config File - Illegal Database Data.");
        }
    }

}
