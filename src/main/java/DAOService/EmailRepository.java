package main.java.DAOService;

import main.java.dto.EmailMessage;

import java.io.IOException;
import java.sql.SQLException;

public interface EmailRepository {

    EmailMessage getEmailById(String id) throws IOException, ClassNotFoundException;

    boolean insertEmail(EmailMessage emailMessage) throws SQLException, IOException, ClassNotFoundException;

}
