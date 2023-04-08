package gotcha.server.Domain.HazardsModule;

import gotcha.server.Utils.Location;
import gotcha.server.Utils.Logger.SystemLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IHazardControllerTest {

    private HazardController hazardController;

    @Mock
    private SystemLogger systemLogger;

    //private HazardRepository hazardRepository = new HazardRepository();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // TODO: 4/8/2023 : add mocks for the JpaRepository 
        //hazardController = new HazardController(systemLogger, hazardRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateHazards_AllHazardsAreNew_AllAdded() {
        final String City = "beersheva";
        final HazardType Type = HazardType.pothole;
        var location1 = new Location(new BigDecimal(20), new BigDecimal(20));
        var location2 = new Location(new BigDecimal(40), new BigDecimal(40));
        var hazards = new ArrayList<>(Arrays.asList(
                new StationaryHazard(1,1,location1,City,Type,20),
                new StationaryHazard(2,1,location2,City,Type,20)
        ));
        assertDoesNotThrow(() -> hazardController.update_hazards(hazards,1));
        assertTrue(hazardController.view_hazards().size() == hazards.size());
    }

    @Test
    void updateHazards_LocationIsTheSame_HazardSizeUpdated() {
        final String City = "beersheva";
        final HazardType Type = HazardType.pothole;
        var location1 = new Location(new BigDecimal(20), new BigDecimal(20));
        var hazard1 = new StationaryHazard(1,1,location1,City,Type,20);
        var hazard2 = new StationaryHazard(2,1,location1,City,Type,40);
        var hazards1 = new ArrayList<>(Arrays.asList(
                hazard1
        ));
        var hazards2 = new ArrayList<>(Arrays.asList(
                hazard2
        ));
        assertDoesNotThrow(() -> hazardController.update_hazards(hazards1,1));
        assertTrue(hazardController.view_hazards().size() == hazards1.size());
        assertTrue(hazard1.getSize() == 20);
        assertDoesNotThrow(() -> hazardController.update_hazards(hazards2,1));
        assertTrue(hazardController.view_hazards().size() == hazards1.size());
        assertTrue(hazard1.getSize() == 40);
    }

    @Test
    void updateHazards_LocationsAreWithinRadiusDistance_HazardSizeUpdated() {
        final String City = "beersheva";
        final HazardType Type = HazardType.pothole;
        // Create two Location objects with coordinates within RADIOS distance.
        BigDecimal longitude1 = new BigDecimal("12.345678");
        BigDecimal latitude1 = new BigDecimal("56.789012");

        // TODO: 4/8/2023 : Need to create distance based on RADIUS
        BigDecimal longitude2 = longitude1.add(new BigDecimal("0.00001"));
        BigDecimal latitude2 = latitude1.add(new BigDecimal("0.00001"));

        Location location1 = new Location(longitude1, latitude1);
        Location location2 = new Location(longitude2, latitude2);
        var hazard1 = new StationaryHazard(1,1,location1,City,Type,20);
        var hazard2 = new StationaryHazard(2,1,location2,City,Type,40);
        var hazards1 = new ArrayList<>(Arrays.asList(
                hazard1
        ));
        var hazards2 = new ArrayList<>(Arrays.asList(
                hazard2
        ));
        assertDoesNotThrow(() -> hazardController.update_hazards(hazards1,1));
        assertTrue(hazardController.view_hazards().size() == hazards1.size());
        assertTrue(hazard1.getSize() == 20);
        assertDoesNotThrow(() -> hazardController.update_hazards(hazards2,1));
        assertTrue(hazardController.view_hazards().size() == hazards1.size());
        assertTrue(hazard1.getSize() == 40);
    }

    @Test
    void remove_hazard() {
    }
}