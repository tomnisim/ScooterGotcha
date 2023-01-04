package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Utils.Formula;

import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class UserRateCalculator implements IUserRateCalculator {

    private final String tables_txt_file_path = "C:\\Users\\Amit\\Desktop\\ScooterGotcha\\server\\src\\main\\java\\gotcha\\server\\Domain\\RatingModule\\user_rate_tables.txt";
    private Dictionary<Integer, Integer> speed_changes_per_minute;
    private Dictionary<Integer, Integer> brakes_per_minute;
    private Dictionary<Integer, Integer> sharp_turns_per_minute;
    private Dictionary<Integer, Integer> alerts_per_minute;
    private Dictionary<Integer, Integer> on_sidewalk;

    private static class SingletonHolder {
        private static UserRateCalculator instance = new UserRateCalculator();
    }
    public static UserRateCalculator get_instance() {
        return UserRateCalculator.SingletonHolder.instance;
    }

    public UserRateCalculator(){
        this.speed_changes_per_minute = new Hashtable<Integer, Integer>();
        this.brakes_per_minute = new Hashtable<Integer, Integer>();
        this.sharp_turns_per_minute = new Hashtable<Integer, Integer>();
        this.alerts_per_minute = new Hashtable<Integer, Integer>();
        this.on_sidewalk = new Hashtable<Integer, Integer>();
        this.set_tables();
    }


    /**
     * the method calculate the new rate of a user,
     * the method will consider the previous rate of user with weight of user's number of rides.
     * @param user - rider who just finish ride.
     * @param ride - the last ride.
     */
    @Override
    public void update_user_rating(User user, Ride ride){

    }

    /**
     * this method should get an EXCEL file and covert him to map of 5 tables.
     * @Precondition - legal tables.
     * @param tables - map of 5 tables.
     */
    @Override
    public void update_tables(Dictionary<String, Dictionary<Integer, Integer>> tables){

        this.speed_changes_per_minute = tables.get("speed_changes_per_minute");
        this.brakes_per_minute = tables.get("brakes_per_minute");
        this.sharp_turns_per_minute = tables.get("sharp_turns_per_minute");
        this.alerts_per_minute = tables.get("alerts_per_minute");
        this.on_sidewalk = tables.get("on_sidewalk");
    }


    @Override
    public void set_tables() {
        try {
            File file = new File(tables_txt_file_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                String[] instruction_parts = instruction.split(":");
                // TODO: 28/12/2022 : build tables from txt file.
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
