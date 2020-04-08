package main.java;

import com.sun.mail.pop3.POP3Store;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/***********class EmailSmtp {
    public static void sendPlainTextEmail( String host, String port,
                                   final String userName, final String password, String toAddress,
                                   String subject, String message) throws AddressException,
            MessagingException, ParseException, IOException {

        Logger _log = Logger.getLogger(EmailSmtp.class.getName());

        //TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simplformat = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss.SSS'Z' a");
        //simplformat.setTimeZone(utc);
        String strDate = simplformat.format(new Date());
        //strDate.toString();
        Date date = simplformat.parse(strDate);


        /*Date date1 = new Date(); // need it later
        SimpleDateFormat simplformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:dd");
        String strDate1 = simplformat.format(date);*/

        // sets SMTP server properties
        /*************Properties properties = new Properties();
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
       /****** InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
        attachment.setFileName("C:\\Users\\sarko.ali\\Desktop\\text.pdf");
        attachment.setContent(attachmentDataStream, "application/pdf");
        multipart.addBodyPart(attachment);*/

      /*************** String fileName = "C:\\Users\\sarko.ali\\Desktop\\text.pdf";
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

    //receiveMail
   /*public void receiveMail(String pop3Host, String pop3port, String mailUser, String password) throws MessagingException, IOException {

        Logger _log = Logger.getLogger(EmailSmtp.class.getName());

        Properties properties = new Properties();
        //Properties properties = System.getProperties(); //för imap
        properties.put("mail.pop3.host",pop3Host); //"mail.pop3.host",pop3Host
        properties.put("mail.pop3.port",pop3port); //"mail.pop3.port",pop3port
        properties.put("mail.pop3.starttls.enable","true"); //"mail.pop3.starttls.enable","true"
        properties.put("mail.smtp.auth", "true");

        /*properties.setProperty("mail.store.protocol", "imaps");*/

       /* Session session = Session.getInstance(properties,null);

        //create pop3 story object and connect to the pop server
       /* POP3Store pop3MailStore = (POP3Store) session.getStore(storyType);
        pop3MailStore.connect(pop3Host, mailUser,password);*/

       /* Store store = session.getStore("pop3"); //imap
        store.connect(pop3Host, mailUser,password);
        session.getDebug();


        //create folder object like inbox and open it
        Folder emailFolder = store.getFolder("INBOX");//pop3MailStore.getFolder("INBOX");
        if(emailFolder == null){
            _log.info("NO INBOX");
            System.exit(1);
        }
        emailFolder.open(Folder.READ_WRITE);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //retrieve message
        Message[] messages = emailFolder.getMessages();
        for (int i = 0; i <messages.length; i++) {
            Message mags = messages[i];
            _log.info("Email number " + (i+1) + ":");
            messages[i].writeTo(System.out);
            _log.info("Email subject " + mags.getSubject());
            _log.info("From " + mags.getFrom()[0]);
            /*_log.info("TO " + mags.getRecipients(Message.RecipientType.TO));
            _log.info("CC: " + mags.getRecipients(Message.RecipientType.CC));
            _log.info("Text " + mags.getContent().toString());
            _log.info("messageDate " + mags.getContent());*/

            /*String contentType = mags.getContentType();
            String messageContent = "";

            if(contentType.contains("text/plain") || contentType.contains("text/html")){
                Object content = mags.getContent();
                    if(content !=null){
                        messageContent = content.toString();
                    }else {
                        messageContent = "Error downloding Content";
                    }
            }

           /* System.out.println("Receiving attach file");
            Multipart multipart = (Multipart) messages[i].getContent();
            for (int j = 0; j < multipart.getCount(); j++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                InputStream inputStream = bodyPart.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                while (reader.ready()){

                    System.out.println(reader.readLine());
                }
                System.out.println();
            }
            System.out.println();*/

            //delete message
           /* String subject = mags.getSubject();
            System.out.println("Do you want to DELETE this message? press 'Y' or 'N'");
            String ans = reader.readLine();
            if(ans.equalsIgnoreCase("Y")){
                mags.setFlag(Flags.Flag.DELETED, true);
                System.out.println("Message is DELETE " + subject);

            }else if(ans.equalsIgnoreCase("N")){
                break;
            }


        }
        emailFolder.close(false);
        //pop3MailStore.close();
        store.close();


    }*/

///////////}