package gotcha.server.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotholeWidthDetectionTest {
    private PotholeWidthDetection p;
    @AfterEach
    void setUp() {
        this.p = new PotholeWidthDetection();
    }

    @Test
    void main() {
        p.detect();
    }
}