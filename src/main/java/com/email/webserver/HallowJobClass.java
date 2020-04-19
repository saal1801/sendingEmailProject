package main.java.com.email.webserver;

import com.sun.activation.registries.MailcapParseException;
import main.java.com.email.webserver.dao.EmailMessage;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.SimpleThreadPool;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
//import java.util.logging.Logger;

public class HallowJobClass implements Job {

    private static final Logger _log = LoggerFactory.getLogger(HallowJobClass.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("Hello fromJobDetail: What do you want sending to....");
        String message = "Hi guy, Hope you are doing well. Duke.";

        //EmailMessage emailMessage = new EmailMessage("sarko@gmail.com","Hello", message);
       // emailMessage.setTo("Tosarko@gmail.com");

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String fromEmail = jobDataMap.getString("sarko@gmail.com");
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        String recipientEmail = jobDataMap.getString("email");



        /*EmailTextClass emailTextClass = new EmailTextClass();
        try {
            emailTextClass.sendPlainTextEmail("sarko@gmail.com", "Hello", message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //Scheduler scheduler= null;
        Properties properties = new Properties();

        StdSchedulerFactory schedFactory = new StdSchedulerFactory();

        properties.setProperty("org.quartz.scheduler.skipUpdateCheck","true");
        properties.setProperty("org.quartz.threadPool.class", SimpleThreadPool.class.getName());
        properties.setProperty("org.quartz.threadPool.threadCount", "5");
        properties.setProperty("org.quartz.threadPool.threadPriority", "4");

        try {
            schedFactory.initialize(properties);
            //scheduler.shutdown();
            sendEmail(fromEmail, recipientEmail, subject, body);
        } catch (SchedulerException | MessagingException | ParseException | IOException e) {
            e.printStackTrace();
        }


    }
    private void sendEmail( String fromEmail, String toEmail, String subject, String body)throws AddressException,
            MessagingException, ParseException, IOException{

        final String userName = "ab56213dc75004";
        final String password = "4d957bd8286db4";
        String host = "smtp.mailtrap.io";
        String port = "2525";

        //Logger _log = Logger.getLogger(EmailTextClass.class.getName());

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

        msg.setFrom(new InternetAddress(fromEmail));
        InternetAddress[] toAddresses = { new InternetAddress(toEmail) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(date);
        System.out.println("Sent Date " + msg.getSentDate());
        System.out.println("Receiving date " + msg.getReceivedDate());
        // set plain text message
        msg.setText(body);
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
        if(t == null){

            _log.error("Failed to send email to: " + toEmail);
        }
        t.close();
        //attachmentDataStream.close();


    }
}
