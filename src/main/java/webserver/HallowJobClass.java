package main.java.webserver;

import main.java.DOAService.SQLConClass;
import main.java.dto.EmailMessage;
import main.java.emailServer.EmailTextClass;
import org.quartz.*;
import javax.mail.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public class HallowJobClass implements Job {

    private final EmailTextClass emailTextClass = new EmailTextClass();
    private final EmailMessage emailMessage = new EmailMessage();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        System.out.println("Polling for emails to send" );

        String nullSting = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(SQLConClass.url, SQLConClass.user, SQLConClass.password);
            CallableStatement callableStatement  = conn.prepareCall("{CALL GetEmailPro()}");
            ResultSet sprs = callableStatement.executeQuery();
            System.out.println("call:SP::::: " + sprs);

            nullSting.split("/");
                emailTextClass.sendEmail(emailMessage);
            } catch (IllegalArgumentException | NullPointerException  | MessagingException | ParseException | IOException | SQLException | ClassNotFoundException e) {
            System.out.println("Waiting for getting value input");
            e.printStackTrace();

        }

    }
}

