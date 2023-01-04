package gotcha.server.Service;

import java.util.List;

public interface INotificationHandler {

    List<String> get_user_notifications(String email);
    void reset_notifications();
    void add_notification(String email, String notification);
    boolean send_waiting_notifications(String email);
}
