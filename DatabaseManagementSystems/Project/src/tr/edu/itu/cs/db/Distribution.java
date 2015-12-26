package tr.edu.itu.cs.db;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import tr.edu.itu.cs.db.Class.User;

import com.sun.mail.smtp.SMTPTransport;


public class Distribution {

    public static void sendMail(User user) throws Exception {
        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(user.getEmail(), false));
        msg.setSubject("Theautomata Email Confirmation");
        msg.setText("Your Confirmation Code: " + user.getConfirmationCode());
        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
        t.connect("smtp.gmail.com", "theautomatadb@gmail.com", "EmailPassword");
        t.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Response: " + t.getLastServerResponse());
        t.close();
    }
}
