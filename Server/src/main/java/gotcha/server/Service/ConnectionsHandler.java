package gotcha.server.Service;

public class ConnectionsHandler implements IConnectionsHandler {

    public ConnectionsHandler(){

    }
    @Override
    public boolean has_open_connection(String email) {
        return true;
    }

    @Override
    public void sendNotification(String email, String notification) {
    }
}
