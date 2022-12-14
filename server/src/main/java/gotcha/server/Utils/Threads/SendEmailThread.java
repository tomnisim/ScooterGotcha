//package gotcha.server.Utils.Threads;
//
//import gotcha.server.Utils.Logger.SystemLogger;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
//
//public class SendEmailThread implements Runnable {
//
//    private String send_from;
//    private String send_to;
//
//    public SendEmailThread(String send_from, String send_to) {
//        this.send_from = send_from;
//        this.send_to = send_to;
//    }
//
//
//    @Override
//    public void run() {
//        SystemLogger.getInstance().add_log("System Send Email To:" +send_to);
//        // Assuming you are sending email from localhost
//        String host = "localhost";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.setProperty("mail.smtp.host", host);
//
//        // Get the default Session object.
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(send_from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(send_to));
//
//            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
//
//            // Now set the actual message
//            message.setText("This is actual message");
//
//            // Send message
//            Transport.send(message);
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//
//    }
//
//}
