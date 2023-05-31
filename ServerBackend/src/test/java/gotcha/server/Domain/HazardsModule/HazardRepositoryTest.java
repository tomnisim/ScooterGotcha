package gotcha.server.Domain.HazardsModule;

import gotcha.server.Domain.s3.S3Service;
import gotcha.server.Utils.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class HazardRepositoryTest {

    HazardRepository hazardRepository;

    @Autowired
    IHazardRepository hazardJpaRepositry;

    @Autowired
    S3Service s3Service;

    @BeforeEach
    void setUp() {
        hazardRepository = new HazardRepository(hazardJpaRepositry, s3Service);
    }

    private StationaryHazard createHazard() {
        BigDecimal lng = new BigDecimal("34.801402");
        BigDecimal lat = new BigDecimal("31.265106");
        Location origin = new Location(lng, lat);
        return new StationaryHazard(5, origin, "Tel-Aviv", HazardType.PoleTree, 16.5);
    }

//    @Test
//    void addHazard() {
//        // setup
//        var hazard = createHazard();
//        // act
//        assertDoesNotThrow(() -> hazardRepository.addHazard(hazard));
//
//        // verify
//        var savedHazardOptional = hazardJpaRepositry.findById(hazard.getId());
//        assertTrue(savedHazardOptional.isPresent());
//        var savedHazard = savedHazardOptional.get();
//        assertTrue(savedHazard != null);
//        assertTrue(savedHazard.getId() == hazard.getId());
//        assertTrue(savedHazard.getSize() == hazard.getSize());
//    }
//
//
//
//    @Test
//    void removeHazard() {
//        // setup
//        var hazard = createHazard();
//        // act
//        assertDoesNotThrow(() -> hazardRepository.addHazard(hazard));
//        var savedHazardOptional = hazardJpaRepositry.findById(hazard.getId());
//        assertTrue(savedHazardOptional.isPresent());
//        assertDoesNotThrow(() -> hazardRepository.removeHazard(hazard.getId()));
//        assertThrows(Exception.class , () -> hazardRepository.getHazardById(hazard.getId()));
//        savedHazardOptional = hazardJpaRepositry.findById(hazard.getId());
//        assertFalse(savedHazardOptional.isPresent());
//    }
//
//    @Test
//    void getHazard_hazardOnlyInDb() {
//        // setup
//        var hazard = createHazard();
//        // act
//        hazardJpaRepositry.save(hazard);
//        var savedHazardOptional = hazardJpaRepositry.findById(hazard.getId());
//        assertTrue(savedHazardOptional.isPresent());
//        try {
//            var savedHazard = hazardRepository.getHazardById(hazard.getId());
//            assertTrue(savedHazard != null);
//            assertTrue(savedHazard.getId() == hazard.getId());
//            assertTrue(savedHazard.getSize() == hazard.getSize());
//        }
//        catch (Exception e) {
//            fail("shouldn't happen");
//        }
//    }


}