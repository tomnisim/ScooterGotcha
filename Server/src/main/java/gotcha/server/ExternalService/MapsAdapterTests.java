package gotcha.server.ExternalService;

public class MapsAdapterTests implements MapsAdapter {
    @Override
    public boolean handshake() {
        return false;
    }
}
