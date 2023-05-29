package gotcha.server.Utils;

import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

class PotholeWidthDetectionTest {
    private PotholeWidthDetection p;
    @BeforeEach
    void setUp() {
        this.p = new PotholeWidthDetection();
    }
    String my_path = "C:\\Users\\amitm\\Desktop\\Workspace\\Seminar\\Iteration 3\\1234.jpg";

    @Test
    void test1() throws IOException, JSONException {
//        p.detect(my_path);
    }
}