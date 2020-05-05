package main.java.emailServer;


import main.java.DOAService.SQLConClass;
import main.java.dao.EmailMessage;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailTextClass {
    //Singleton instance
    private SQLConClass sqlConClass = new SQLConClass();

    public void sendEmail(EmailMessage emailMessage) throws
            MessagingException, ParseException, IOException, SQLException, ClassNotFoundException {

        sqlConClass.readEmail(emailMessage);

        final String userName = "49c39ef2c1d586";
        final String password = "1af7298ecffac8";
        String host = "smtp.mailtrap.io";
        String port = "2525";

        SimpleDateFormat simplformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss k");
        String strDate = simplformat.format(new Date());
        Date date = simplformat.parse(strDate);


        Properties properties = new Properties();
        properties.put("mail.smtp.host", host); //"mail.pop3.host",pop3Host
        properties.put("mail.smtp.port", String.valueOf(port)); //"mail.pop3.port",pop3port
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
        Session session = Session.getInstance(properties,authenticator);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(emailMessage.getFrom()));
        InternetAddress[] toAddresses = { new InternetAddress(emailMessage.getTo()) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(emailMessage.getSubject());
        msg.setSentDate(date);
        System.out.println("Sent Date " + strDate);
        //System.out.println("Receiving date " + msg.getReceivedDate());
        // set plain text message
        msg.setText(emailMessage.getBody());
        msg.setContent("<div><span style=\"color:#57aaca;\">c</span><span style=\"color:#57aec5;\">o</span><span style=\"color:#57b2c0;\">l</span><span style=\"color:#57b6ba;\">o</span><span style=\"color:#57bbb5;\">r</span><span style=\"color:#56bfb0;\">f</span><span style=\"color:#56c3ab;\">u</span><span style=\"color:#56c7a5;\">l</span><span style=\"color:#56cba0;\"> </span><span style=\"color:#5ec3ab;\">m</span><span style=\"color:#65bbb6;\">e</span><span style=\"color:#6db3c1;\">s</span><span style=\"color:#75accd;\">s</span><span style=\"color:#7da4d8;\">a</span><span style=\"color:#849ce3;\">g</span><span style=\"color:#8c94ee;\">e</span></div>", "text/html");

        String HTMLmsg = "First HTML message";
        byte[] attachmentData = null;
        Multipart multipart = new MimeMultipart();

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(HTMLmsg, "text/html");
        mimeBodyPart.setText(HTMLmsg,"utf-8", "html" );
        multipart.addBodyPart(mimeBodyPart);

        MimeBodyPart attachment = new MimeBodyPart();

        String fileName = "C:\\Users\\sarko.ali\\Desktop\\text.pdf";
        //DataSource dataSource = new FileDataSource(fileName);
        //attachment.setDataHandler(new DataHandler(dataSource));
        attachment.attachFile(fileName);
        attachment.setFileName(fileName);
        attachment.setContent("<h1>Attached GIF</h1> src='https://media.giphy.com/media/l4JyQqyt9S1WTiE6c/giphy.gif'>", "text/html");
        multipart.addBodyPart(attachment);

        msg.setContent(multipart);

        // sends the e-mail
        Transport t = session.getTransport("smtp");
        t.connect(userName, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();

    }

}