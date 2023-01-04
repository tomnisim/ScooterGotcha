package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Formula;

public interface IHazardRateCalculator {

    double rate_hazard(StationaryHazard hazard);
    void update_hazard_formula(HazardType hazardType, Formula formula);

}
