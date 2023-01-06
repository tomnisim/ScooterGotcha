//package gotcha.server.Service;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Controller;
//
//import java.security.Principal;
//import java.util.*;
//
//
//@Controller
//public class NotificationHandler implements INotificationHandler {
//
//    private static NotificationHandler notificationHandler = null;
//    private Map<String, List<String>> users_notifications;
//    private ConnectionsHandler connectionsHandler;
//
//    private NotificationHandler(){
//        users_notifications = new HashMap<>();
//    }
//
//    public static NotificationHandler getInstance() {
//        if (notificationHandler == null)
//            notificationHandler = new NotificationHandler();
//        return notificationHandler;
//    }
//
//    @Override
//    public List<String> get_user_notifications(String email){
//        if (users_notifications.containsKey(email))
//            return this.users_notifications.get(email);
//        return new LinkedList<>();
//    }
//
//    @Override
//    public void reset_notifications() {
//        this.users_notifications = new HashMap<>();
//    }
//
//    /**
//     * this method is for add notification & try to send it.
//     * @param email of the assign user we want to notify.
//     * @param notification to send the user.
//     */
//    @Override
//    public void add_notification(String email, String notification) {
//        if (!this.users_notifications.containsKey(email)) {
//            this.users_notifications.put(email, new ArrayList<>());
//        }
//        this.users_notifications.get(email).add(notification);
//        this.send_waiting_notifications(email);
//    }
//
//    /**
//     * this method is responsible for sending await notifications when user login / after adding new notification.
//     * @param email of the assign user who just logged in / just got a new message.
//     * @return true if we send notifications, false if there is no open connection / no notifications to send.
//     */
//    @Override
//    public boolean send_waiting_notifications(String email) {
//        // step 1 : check that there is a connection
//        if (!connectionsHandler.has_open_connection(email))
//            return false;
//        // step 2 : check that there are notifications to send.
//        if (!this.users_notifications.containsKey(email))
//            return false;
//        List<String> notificationsList = this.users_notifications.get(email);
//        if (notificationsList.size() == 0)
//            return false;
//
//        // step 3 : sending messages
//        List<String> to_remove = new ArrayList<>();
//        boolean flag_to_remove = false;
//        for (String notification : notificationsList) {
//            flag_to_remove = true;
//            try{
//                this.connectionsHandler.sendNotification(email, notification);
//            }
//            catch (Exception e){
//                flag_to_remove = false;
//            }
//            if (flag_to_remove) {
//                to_remove.add(notification);
//            }
//        }
//
//        for (String noti : to_remove)
//            notificationsList.remove(noti);
//        return true;
//
//    }
//}
