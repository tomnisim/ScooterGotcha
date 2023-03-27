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

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void rate_hazard() {
        HazardRateCalculator hazardRateCalculator = new HazardRateCalculator();
        StationaryHazard hazard = new StationaryHazard(5, 6, location, city, type1, 16);
        double answer = hazard.getRate();
        assertEquals(answer, 85);

    }

    @Test
    void set_formulas() {
    }

    @Test
    void update_hazard_formula() {
    }
}