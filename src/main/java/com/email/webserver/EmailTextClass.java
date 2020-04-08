package main.java.com.email.webserver;


import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailTextClass extends EmailService {
    public void sendPlainTextEmail( String toAddress, String subject, String message) throws AddressException,
                                    MessagingException, ParseException, IOException {

        final String userName = "ab56213dc75004";
        final String password = "4d957bd8286db4";
        String host = "smtp.mailtrap.io";
        String port = "2525";

        Logger _log = Logger.getLogger(EmailTextClass.class.getName());

        SimpleDateFormat simplformat = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss.SSS'Z' a");
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

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(date);
        // set plain text message
        // msg.setText(message);

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
        //attachmentDataStream.close();

    }

}