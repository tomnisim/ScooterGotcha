package gotcha.server.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotholeWidthDetectionTest {
    private PotholeWidthDetection p;
    @BeforeEach
    void setUp() {
        this.p = new PotholeWidthDetection();
    }

    @Test
    void test1() {
        p.detect1();
    }
}