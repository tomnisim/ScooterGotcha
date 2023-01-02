package gotcha.server.Utils;


import gotcha.server.Domain.UserModule.Notification;

public interface Observer {
    boolean notify_user(Notification notification);
}