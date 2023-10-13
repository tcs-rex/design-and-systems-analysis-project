package model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A utility object that can be used to send emails.
 */
public class SendMail {

    // Recipient's email ID needs to be mentioned.
    private String toAddress;
    // Sender's email ID needs to be mentioned
    private final String FROM_ADDRESS = "gameremailling@gmail.com";
    // Assuming you are sending email from through gmails smtp
    private final String HOST = "smtp.gmail.com";
    // Emailer password
    private final String PASSWORD = "blcddanjbeqbhqby";

    /**
     * 
     */
    public SendMail(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * Sends an email to the toAddress with the specified messand and subject.
     * 
     * @param messageToSend
     * @param subject
     */
    public void sendMail(String messageToSend, String subject) {

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

            }

        });

        // Used to debug SMTP issues
        // session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(FROM_ADDRESS));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(messageToSend);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}