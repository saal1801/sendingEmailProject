package main.java.emailServer;


import com.sun.mail.smtp.SMTPTransport;
import main.java.DAOService.SQLConClass;
import main.java.dto.EmailMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class EmailTextClass {

    public void sendEmail(EmailMessage emailMessage) throws
            MessagingException, ParseException, SQLException, ClassNotFoundException {

        // sqlConClass.readEmail(emailMessage);

        final String userName = "49c39ef2c1d586";
        final String password = "1af7298ecffac8";
        String host = "smtp.mailtrap.io";
        String port = "2525";

        SimpleDateFormat simplformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss k");
        String strDate = simplformat.format(new Date());
        Date date = simplformat.parse(strDate);


        Properties properties = new Properties();
        properties.put("mail.smtp.host", host); //"mail.pop3.host",pop3Host
        properties.put("mail.smtp.port", port); //"mail.pop3.port",pop3port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); //"mail.pop3.starttls.enable","true"
        properties.put("mail.smtp.user", userName);

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };


        // creates a new session, no Authenticator (will connect() later)
        Session session = Session.getInstance(properties, authenticator);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(emailMessage.getFrom()));
        InternetAddress[] toAddresses = {new InternetAddress(emailMessage.getTo())};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(emailMessage.getSubject());
        msg.setSentDate(date);
        System.out.println("Sent Date " + strDate);

        // set plain text message
        msg.setText(emailMessage.getBody());

        // sends the e-mail
        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
        t.connect(userName, password);
        //t.setReportSuccess(true);
        t.sendMessage(msg, msg.getAllRecipients());
        String response = t.getLastServerResponse().trim();
        int code = t.getLastReturnCode();


        if (code == 250) {
            PreparedStatement stmt = SQLConClass.conn.prepareStatement("UPDATE emailpro SET status = 'SENT' where id = ?");
            stmt.setString(1, emailMessage.authTOkenId);
            if (stmt != null) {

                int sent = stmt.executeUpdate();
                System.out.println("Response: " + "sent");
            }

        } else {

            //PreparedStatement stmt = SQLConClass.conn.prepareStatement("UPDATE emailpro SET (status) value ('FAILED') ORDER BY ID DESC LIMIT 1");
            PreparedStatement stmt = SQLConClass.conn.prepareStatement("UPDATE emailpro SET status = 'FAILED' where id = ?");
            stmt.setString(1, emailMessage.authTOkenId);

            int failed = stmt.executeUpdate();
            System.out.println("Response: " + "failed");
        }
        t.close();
    }
}