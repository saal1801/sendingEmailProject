import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/*class SendMailSSL{
    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException, IOException, MessagingException {
        // SMTP server information
        String host = "smtp.mailtrap.io"; //"imap.gmail.com";
        String port = "2525";
        String mailUser = "ab56213dc75004";
        String password = "4d957bd8286db4";

        // outgoing message information
        String mailTo = "Tosarko@gmail.com";
        String subject = "Hello";
        String message = "Hi guy, Hope you are doing well. Duke.";

        //receiving
        String pop3Host = "pop3.mailtrap.io"; //"imap.gmail.com";
        String pop3Port = "9950";

       SQLConClass sqlConClass = new SQLConClass();
        sqlConClass.getConnection();
        System.out.println("from SQL " + sqlConClass);


        EmailSmtp mailer = new EmailSmtp();

        try {
            Thread waitThread = new Thread();
            mailer.sendPlainTextEmail( host, port, mailUser, password, mailTo,
                    subject, message);
            System.out.println("Email sent successfully....");

            if(mailer !=null) {
                waitThread.sleep(1000);
                mailer.receiveMail(pop3Host, pop3Port, mailUser, password);
            }
        } catch (Exception ex) {
            System.out.println("Failed to sending this email.");
            sqlConClass.run();

            ex.printStackTrace();
        }




         /*ReceiveMail recMail = new ReceiveMail();
        recMail.receiveMail(pop3Host, storyType, pop3Port,mailUserName, userPassword);*/


   // }
//}

