
package main.java.dto;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailMapper implements RowMapper<EmailMessage> {

    @Override
    public EmailMessage mapRow(ResultSet resultSet, int i) throws SQLException {

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setAuthTOkenId(resultSet.getString("id"));
        emailMessage.setFrom(resultSet.getString("EmailSender"));
        emailMessage.setTo(resultSet.getString("EmailReceiver"));
        emailMessage.setSubject(resultSet.getString("subject"));
        emailMessage.setBody(resultSet.getString("body"));
        return emailMessage;
    }
}


