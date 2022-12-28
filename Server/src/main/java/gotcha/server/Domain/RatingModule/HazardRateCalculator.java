package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Formula;

import java.util.Dictionary;

public class HazardRateCalculator implements IHazardRateCalculator {

    private final Dictionary<HazardType, Formula> formulas;

    public HazardRateCalculator(Dictionary<HazardType, Formula> formulas) {
        this.formulas = formulas;
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
        double answer = formula.evaluate(hazard.getSize());
        return answer;
    }


    @Override
    public void update_hazard_formula(HazardType hazardType, Formula formula){

    }
}
