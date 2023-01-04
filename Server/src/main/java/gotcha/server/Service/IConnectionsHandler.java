package gotcha.server.Service;

public interface IConnectionsHandler {

    boolean has_open_connection(String email);
    void sendNotification(String email, String notification);
}
