package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Formula;

import java.util.Dictionary;

public class HazardRateCalculator {

    private final Dictionary<HazardType, Formula> formulas;

    public HazardRateCalculator(Dictionary<HazardType, Formula> formulas) {
        this.formulas = formulas;
    }


    /**
     *
     * @param hazard
     * @return double between 0-20
     */
    public double rate_hazard(StationaryHazard hazard){
        Formula formula = formulas.get(hazard.getType());
        double answer = formula.evaluate(hazard.getSize());
        return answer;
    }


    public void update_hazard_formula(HazardType type, Formula formula){

    }
}
