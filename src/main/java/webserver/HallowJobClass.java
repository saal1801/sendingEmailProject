package main.java.webserver;

import main.java.DOAService.SQLConClass;
import main.java.dao.EmailMessage;
import main.java.emailServer.EmailTextClass;
import org.quartz.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.mail.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public class HallowJobClass implements Job {

    private EmailTextClass emailTextClass = new EmailTextClass();
    private EmailMessage emailMessage = new EmailMessage();
    private SQLConClass sqlConClass = new SQLConClass();

    private static final Logger _log = LoggerFactory.getLogger(HallowJobClass.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("Polling for emails to send" );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(sqlConClass.url, sqlConClass.user, sqlConClass.password);
            CallableStatement callableStatement  = conn.prepareCall("{CALL GetEmailPro()}");
            ResultSet sprs = callableStatement.executeQuery();
            System.out.println("call:::::: " + sprs);

        //for (int i = 0; i < 5; i++) {
                emailTextClass.sendEmail(emailMessage);
            } catch (MessagingException | ParseException | IOException e) {
                e.printStackTrace();
            } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            }
        //System.exit(0);
       // }

    }
}

