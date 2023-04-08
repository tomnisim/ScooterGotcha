package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.RidesModule.*;
import gotcha.server.Domain.UserModule.Rider;

import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class UserRateCalculator implements IUserRateCalculator {

    private final String tables_txt_file_path = "src\\main\\java\\gotcha\\server\\Domain\\RatingModule\\user_rate_tables.txt";

    private Dictionary<Integer, Integer> speed_changes_per_minuteTable;
    private Dictionary<Integer, Integer> brakes_per_minuteTable;
    private Dictionary<Integer, Integer> sharp_turns_per_minuteTable;
    private Dictionary<Integer, Integer> alerts_per_minuteTable;
    private Dictionary<Integer, Integer> on_sidewalkTable;

    private static class SingletonHolder {
        private static UserRateCalculator instance = new UserRateCalculator();
    }
    public static UserRateCalculator get_instance() {
        return UserRateCalculator.SingletonHolder.instance;
    }

    public UserRateCalculator(){
        this.speed_changes_per_minuteTable = new Hashtable<>();
        this.brakes_per_minuteTable = new Hashtable<>();
        this.sharp_turns_per_minuteTable = new Hashtable<>();
        this.alerts_per_minuteTable = new Hashtable<>();
        this.on_sidewalkTable = new Hashtable<>();
        this.set_tables();
    }


    /**
     * the method calculate the new rate of a user,
     * the method will consider the previous rate of user with weight of user's number of rides.
     * @param rider - rider who just finish ride.
     * @param ride - the last ride.
     */
    @Override
    public void update_user_rating(Rider rider, Ride ride, int number_of_rides){
        double new_rate;
        double user_previous_rate = rider.getRating();
        double current_ride_rate = this.calculate_rate_for_ride(ride);
        new_rate = user_previous_rate * number_of_rides;
        new_rate = new_rate + current_ride_rate;
        new_rate = new_rate / number_of_rides;
        rider.setRating(new_rate);

    }

    @Override
    public double calculate_rate_for_ride(Ride ride) {
        double rate = 0;

        int speed_changes_per_minuteCounter = 0;
        int brakes_per_minuteCounter = 0;
        int sharp_turns_per_minuteCounter = 0;
        int alerts_per_minuteCounter = 0;
        int on_sidewalkCounter = 0;

        for (RidingAction action : ride.getActions()){
            if (action instanceof Brake)
            {
                brakes_per_minuteCounter++;
            }
            if (action instanceof SharpTurn){
                sharp_turns_per_minuteCounter++;
            }
            if (action instanceof Sidewalk)
            {
                on_sidewalkCounter++;
            }
            if (action instanceof Alert){
                alerts_per_minuteCounter++;
            }
            if (action instanceof SpeedChange)
            {
                speed_changes_per_minuteCounter++;
            }


        }

        speed_changes_per_minuteCounter = Math.min(speed_changes_per_minuteCounter, 6);
        brakes_per_minuteCounter = Math.min(brakes_per_minuteCounter, 5);
        sharp_turns_per_minuteCounter = Math.min(sharp_turns_per_minuteCounter, 4);
        alerts_per_minuteCounter = Math.min(alerts_per_minuteCounter, 6);
        on_sidewalkCounter = Math.min(on_sidewalkCounter, 40);

        rate = alerts_per_minuteTable.get(alerts_per_minuteCounter) + brakes_per_minuteTable.get(brakes_per_minuteCounter) +
               speed_changes_per_minuteTable.get(speed_changes_per_minuteCounter) + sharp_turns_per_minuteTable.get(sharp_turns_per_minuteCounter)
               + on_sidewalkTable.get(on_sidewalkCounter);

        rate = rate / 10;

        return rate;
    }

    /**
     * this method should get an EXCEL file and covert him to map of 5 tables.
     * @Precondition - legal tables.
     * @param tables - map of 5 tables.
     */
    @Override
    public void update_tables(Dictionary<String, Dictionary<Integer, Integer>> tables){

        this.speed_changes_per_minuteTable = tables.get("speed_changes_per_minute");
        this.brakes_per_minuteTable = tables.get("brakes_per_minute");
        this.sharp_turns_per_minuteTable = tables.get("sharp_turns_per_minute");
        this.alerts_per_minuteTable = tables.get("alerts_per_minute");
        this.on_sidewalkTable = tables.get("on_sidewalk");
    }


    @Override
    public void set_tables() {
        boolean speed_changes_per_minuteFlag = false;
        boolean brakes_per_minuteFlag = false;
        boolean sharp_turns_per_minuteFlag = false;
        boolean alerts_per_minuteFlag = false;
        boolean on_sidewalkFlag = false;

        try {
            File file = new File(tables_txt_file_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                String[] instruction_parts = instruction.split(":");
                if (instruction_parts[0].equals(""))
                    continue;
                if (instruction_parts[0].equals("speed_changes_per_minute")){
                    speed_changes_per_minuteFlag = true;
                    brakes_per_minuteFlag = false;
                    sharp_turns_per_minuteFlag = false;
                    alerts_per_minuteFlag = false;
                    on_sidewalkFlag = false;
                    continue;
                }
                if (instruction_parts[0].equals("brakes_per_minute")){
                    brakes_per_minuteFlag = true;
                    speed_changes_per_minuteFlag = false;
                    sharp_turns_per_minuteFlag = false;
                    alerts_per_minuteFlag = false;
                    on_sidewalkFlag = false;
                    continue;
                }
                if (instruction_parts[0].equals("sharp_turns_per_minute")){
                    sharp_turns_per_minuteFlag = true;
                    speed_changes_per_minuteFlag = false;
                    brakes_per_minuteFlag = false;
                    alerts_per_minuteFlag = false;
                    on_sidewalkFlag = false;
                    continue;
                }
                if (instruction_parts[0].equals("alerts_per_minute")){
                    alerts_per_minuteFlag = true;
                    speed_changes_per_minuteFlag = false;
                    brakes_per_minuteFlag = false;
                    sharp_turns_per_minuteFlag = false;
                    on_sidewalkFlag = false;
                    continue;
                }
                if (instruction_parts[0].equals("on_sidewalk")){
                    on_sidewalkFlag = true;
                    speed_changes_per_minuteFlag = false;
                    brakes_per_minuteFlag = false;
                    sharp_turns_per_minuteFlag = false;
                    alerts_per_minuteFlag = false;
                    continue;
                }

                if (speed_changes_per_minuteFlag){
                    this.speed_changes_per_minuteTable.put(Integer.parseInt(instruction_parts[0]), Integer.parseInt(instruction_parts[1]));
                }
                if (brakes_per_minuteFlag){
                    this.brakes_per_minuteTable.put(Integer.parseInt(instruction_parts[0]), Integer.parseInt(instruction_parts[1]));
                }
                if (sharp_turns_per_minuteFlag){
                    this.sharp_turns_per_minuteTable.put(Integer.parseInt(instruction_parts[0]), Integer.parseInt(instruction_parts[1]));
                }
                if (alerts_per_minuteFlag){
                    this.alerts_per_minuteTable.put(Integer.parseInt(instruction_parts[0]), Integer.parseInt(instruction_parts[1]));
                }
                if (on_sidewalkFlag){
                    this.on_sidewalkTable.put(Integer.parseInt(instruction_parts[0]), Integer.parseInt(instruction_parts[1]));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
