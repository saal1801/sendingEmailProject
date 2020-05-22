package main.java.webserver;

import main.java.DAOService.SQLConClass;
import main.java.dto.EmailMessage;
import main.java.emailServer.EmailTextClass;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import javax.mail.MessagingException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;


public class HallowJobClass implements Job {

    private final EmailTextClass emailTextClass = new EmailTextClass();
    private final EmailMessage emailMessage = new EmailMessage();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        System.out.println("Polling for emails to send");


        try {
            CallableStatement callableStatement = SQLConClass.conn.prepareCall("{CALL GetEmailPro()}");
            ResultSet sprs = callableStatement.executeQuery();
            if (sprs.next()) {

                emailMessage.setFrom(sprs.getString("EmailSender"));
                emailMessage.setTo(sprs.getString("EmailReceiver"));
                emailMessage.setSubject(sprs.getString("subject"));
                emailMessage.setBody(sprs.getString("body"));
                emailTextClass.sendEmail(emailMessage);
            }

        } catch (IllegalArgumentException | NullPointerException | MessagingException | ParseException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }

    }
}

