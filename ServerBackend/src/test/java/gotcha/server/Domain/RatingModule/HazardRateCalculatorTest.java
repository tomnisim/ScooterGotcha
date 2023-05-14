package gotcha.server.Domain.RatingModule;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

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
        StationaryHazard hazard = new StationaryHazard(5, location, city, type1, size, null);
        double answer = hazard.getRate();
        assertEquals(answer, 5 + (size / 2) * 10);
    }

    @Test
    //  pothole: 10+(W/2)*10
    void rate_hazard_pothole() {
        StationaryHazard hazard = new StationaryHazard(5, location, city, type2, size, null);
        double answer = hazard.getRate();
        assertEquals(answer, 10 + (size / 2) * 10);
    }

    @Test
    // RoadSign: (W/2)*10
    void rate_hazard_roadSign() {
        double size = 16;
        StationaryHazard hazard = new StationaryHazard(5, location, city, type3, size, null);
        double answer = hazard.getRate();
        assertEquals(answer, (size / 2) * 10);
    }

    @Test
    void set_formulas() {
        HazardRateCalculator hazardRateCalculator = new HazardRateCalculator();
        hazardRateCalculator.set_formulas();

    }

    @Test
    void update_hazard_formula() {
    }

    @Test
    public void HazardRateCalculator() {
        HazardRateCalculator expected = null;
        HazardRateCalculator actual = new HazardRateCalculator();

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, false, null));
    }

    @Test
    public void get_instance() {
        HazardRateCalculator expected = null;
        HazardRateCalculator actual = HazardRateCalculator.get_instance();

        assertEquals(expected, actual);
    }

    @Test
    public void rate_hazard() {
        HazardRateCalculator h = null;
        StationaryHazard hazard = null;
        double expected = 0.0D;
        double actual = h.rate_hazard(hazard);

        assertEquals(expected, actual, 0.0000001D);
    }

    @Test
    public void set_formulasTODO() {
        HazardRateCalculator h = null;
        h.set_formulas();
    }

    @Test
    public void update_hazard_formulaTODO() {
        HazardRateCalculator h = null;
        HazardType hazardType = null;
        Formula formula = null;
        h.update_hazard_formula(hazardType, formula);
    }
}
