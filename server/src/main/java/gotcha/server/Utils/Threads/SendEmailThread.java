package gotcha.server.Utils.Threads;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendEmailThread implements Runnable {

    private String send_from;
    private String send_to;
    private String message;
    private String password;
    
    public SendEmailThread(String send_from, String system_email_password, String send_to, String message) {
        this.send_from = send_from;
        this.send_to = send_to;
        this.message = message;
        this.password = system_email_password;
    }

    @Override
//    public void run(){
//        // Assuming you are sending email from through gmails smtp
//        String host = "smtp.gmail.com";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//
//        Session session = Session.getDefaultInstance(properties,
//                new javax.mail.Authenticator(){
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(
//                                send_from, "password");// Specify the Username and the PassWord
//                    }
//                });        // Get the Session object.// and pass username and password
//
//
//        // Used to debug SMTP issues
//        session.setDebug(true);
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
//            message.setText(this.message);
//
//            System.out.println("sending...");
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//    }
    public void run(){
        System.out.println(String.format("Send email! from: [%s], to: [%s], message:[%s]",send_from, send_to, message));
    }
}













