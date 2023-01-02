package gotcha.server.Utils;


import gotcha.server.Domain.Notifications.Notification;

public interface Observer {
    boolean notify_user(Notification notification);
}