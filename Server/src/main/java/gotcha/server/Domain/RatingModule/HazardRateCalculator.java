package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Formula;

import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class HazardRateCalculator implements IHazardRateCalculator {

    private Dictionary<HazardType, Formula> formulas;
    private final String formulas_txt_file_path = "src\\main\\java\\gotcha\\server\\Domain\\RatingModule\\hazard_rate_formulas.txt";

    private static class SingletonHolder {
        private static HazardRateCalculator instance = new HazardRateCalculator();
    }
    public static HazardRateCalculator get_instance() {
        return HazardRateCalculator.SingletonHolder.instance;
    }

    public HazardRateCalculator() {
        this.formulas = new Hashtable<HazardType, Formula>();
        //this.set_formulas();
    }


    /**
     *
     * @param hazard
     * this method diagnose the hazard's type, select the fit formula
     * and rate according the size of the hazard.
     * @return double between 0-20
     */
    @Override
    public double rate_hazard(StationaryHazard hazard){
        Formula formula = formulas.get(hazard.getType());
        //return formula.evaluate(hazard.getSize());
        return 2;
    }

    /**
     * this method reads a txt file and config formulas.
     */

    @Override
    public void set_formulas() {
        try {
            File file = new File(formulas_txt_file_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                String[] instruction_parts = instruction.split(":");
                HazardType hazard_type = HazardType.valueOf(instruction_parts[0]);
                Formula formula = new Formula(instruction_parts[1]);
                this.formulas.put(hazard_type, formula);
                }
            }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void update_hazard_formula(HazardType hazardType, Formula formula){


    }
}
