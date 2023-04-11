package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class HazardRateCalculatorTest {

    final String city = "Tel Aviv";
    final HazardType type1 = HazardType.PoleTree;
    final HazardType type2 = HazardType.pothole;
    final HazardType type3 = HazardType.RoadSign;
    BigDecimal bigDecimal = new BigDecimal("0.0");
    final Location location = new Location(bigDecimal, bigDecimal);
    double size = 16;


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    // PoleTree: 5+(W/2)*10
    void rate_hazard_poleTree() {
        StationaryHazard hazard = new StationaryHazard(5, location, city, type1, size);
        double answer = hazard.getRate();
        assertEquals(answer, 5+(size/2)*10);
    }

    @Test
    //  pothole: 10+(W/2)*10
    void rate_hazard_pothole() {
        StationaryHazard hazard = new StationaryHazard(5, location, city, type2, size);
        double answer = hazard.getRate();
        assertEquals(answer, 10+(size/2)*10);
    }

    @Test
    // RoadSign: (W/2)*10
    void rate_hazard_roadSign() {
        double size = 16;
        StationaryHazard hazard = new StationaryHazard(5, location, city, type3, size);
        double answer = hazard.getRate();
        assertEquals(answer, (size/2)*10);
    }

    @Test
    void set_formulas() {
        HazardRateCalculator hazardRateCalculator = new HazardRateCalculator();
        hazardRateCalculator.set_formulas();

    }

    @Test
    void update_hazard_formula() {
    }
}