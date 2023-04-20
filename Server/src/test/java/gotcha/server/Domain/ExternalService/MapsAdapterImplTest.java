package gotcha.server.Domain.ExternalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapsAdapterImplTest {

    private MapsAdapterImpl mapsAdapter;
    @BeforeEach
    void setUp() {
        this.mapsAdapter = new MapsAdapterImpl();
    }

    @Test
    void test1(){
        String Origin =  "1 Rothschild Boulevard, Tel Aviv-Yafo, Israel";
        String Destination = "10 HaYarkon St, Tel Aviv-Yafo, Israel";
        var answer = this.mapsAdapter.get_routes(Origin, Destination, 1);
        int x = 5;
    }
}