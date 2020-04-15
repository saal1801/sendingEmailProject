package main.java.com.email.webserver;


import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.ResyncData;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailTextClass {
    public void sendPlainTextEmail( String toAddress, String subject, String message) throws AddressException,
                                    MessagingException, ParseException, IOException {

        final String userName = "ab56213dc75004";
        final String password = "4d957bd8286db4";
        String host = "smtp.mailtrap.io";
        String port = "25255";

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
        System.out.println("Sent Date " + msg.getSentDate());
        System.out.println("Receiving date " + msg.getReceivedDate());
        // set plain text message
         msg.setText(message);
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
        //attachmentDataStream.close();

    }
    public void receiveMail() throws MessagingException, IOException {

        Logger _log = Logger.getLogger(EmailTextClass.class.getName());

        final String userName = "ab56213dc75004";
        final String password = "4d957bd8286db4";
        String host = "pop3.mailtrap.io";
        String port = "9950";

        Properties properties = new Properties();
        //Properties properties = System.getProperties(); //för imap
        properties.put("mail.pop3.host", host); //"mail.pop3.host",pop3Host
        properties.put("mail.pop3.port", port); //"mail.pop3.port",pop3port
        properties.put("mail.pop3.starttls.enable", "true"); //"mail.pop3.starttls.enable","true"
        properties.put("mail.pop3.auth", "true");

        /*properties.setProperty("mail.store.protocol", "imaps");*/

        Session session = Session.getInstance(properties, null);

        //create pop3 story object and connect to the pop server
       /* POP3Store pop3MailStore = (POP3Store) session.getStore(storyType);
        pop3MailStore.connect(pop3Host, mailUser,password);*/

        Store store = session.getStore("pop3"); //imap
        store.connect(host, userName, password);
        session.getDebug();


        //create folder object like inbox and open it
        Folder emailFolder = store.getFolder("INBOX");//pop3MailStore.getFolder("INBOX");
        if (emailFolder == null) {
            _log.info("NO INBOX");
            System.exit(1);
        }
        emailFolder.open(Folder.READ_WRITE);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //retrieve message
        Message[] messages = emailFolder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            Message mags = messages[i];
            _log.info("Email number " + (i + 1) + ":");
            messages[i].writeTo(System.out);
            _log.info("Email subject " + mags.getSubject());
            _log.info("From " + mags.getFrom()[0]);
            _log.info("Receiving " + mags.getReceivedDate());
            _log.info("Sent Date " + mags.getSentDate());
           /* _log.info("CC: " + mags.getRecipients(Message.RecipientType.CC));
            _log.info("Text " + mags.getContent().toString());
            _log.info("messageDate " + mags.getContent());*/

            String contentType = mags.getContentType();
            String messageContent = "";

            if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                Object content = mags.getContent();
                if (content != null) {
                    messageContent = content.toString();
                } else {
                    messageContent = "Error downloading Content";
                }
            }

        }
    }

}