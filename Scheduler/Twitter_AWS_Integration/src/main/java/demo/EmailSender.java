package demo;

/**
 * Created by Omkar on 5/11/2015.
 */
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSender {

    InputStream input;
    Properties properties_app = null;

     //public  static  void main(String[] args){
     public  void send_email(String to,String subject,String body){



         try {

             input = getClass().getClassLoader().getResourceAsStream("application.properties");
             properties_app = new Properties();
             properties_app.load(input);
             input.close();

         } catch (FileNotFoundException e) {
             e.printStackTrace();
             System.exit(-1);
         } catch (Exception e) {
             e.printStackTrace();
             System.exit(-1);
         }

         // Sender's email ID needs to be mentioned
         String from = "cmpe273team@gmail.com";

         // Assuming you are sending email from localhost
         String host = "smtp.gmail.com";

         // Get system properties
         Properties properties = System.getProperties();


         properties.setProperty("mail.smtp.host", host);


         properties.setProperty("mail.smtp.auth", "true");
         properties.setProperty("mail.smtp.starttls.enable", "true");
         properties.setProperty("mail.smtp.port", "587");
         properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");


         Session session = Session.getInstance(properties,
                 new javax.mail.Authenticator() {
                     protected PasswordAuthentication getPasswordAuthentication() {


                         return new PasswordAuthentication(properties_app.getProperty("email.from").toString(), properties_app.getProperty("email.pwd").toString());
                     }
                 });



         try{
             // Create a default MimeMessage object.
             MimeMessage message = new MimeMessage(session);

             // Set From: header field of the header.
             message.setFrom(new InternetAddress(from));

             // Set To: header field of the header.
             message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress(to));

             // Set Subject: header field
             message.setSubject(subject);

             // Now set the actual message
             message.setText(body);

             // Send message
             Transport.send(message);
             System.out.println("Sent message successfully....");
         }catch (MessagingException mex) {
             mex.printStackTrace();
         }



    }

}
