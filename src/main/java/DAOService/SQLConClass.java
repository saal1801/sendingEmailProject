package main.java.DAOService;


import main.java.dto.EmailMapper;
import main.java.dto.EmailMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class SQLConClass implements EmailRepository {
    public static JdbcTemplate jdbcTemplate;
    private static final String SQL_FIND_EMAIL = "SELECT id, EmailSender, EmailReceiver, subject, body" + " FROM emaildb.emailpro WHERE id=?";
    private static final String SQL_INSERT_EMAIL = "INSERT INTO emailpro (id,EmailSender,EmailReceiver,subject,body,Received_Date) value (?,?,?,?,?,CURRENT_TIMESTAMP)";
    private static final String SQL_UPDATE_EMAIL = "UPDATE emaildb.emailpro SET EmailSender=?, EmailReceiver=?, subject=?, body=? WHERE id=?";
    private static final String SQL_DELETE_EMAIL = "DELETE FROM emaildb.emailpro WHERE id=?";

    /*public static final String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=true";
    public static final String user = "root";
    public static final String password = "password";
*/
    public static Connection conn;


   /* static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }*/


    public static void proP() throws IOException, ClassNotFoundException {
        String propFileName = "config.properties";
        InputStream inputFile = SQLConClass.class.getClassLoader().getResourceAsStream(propFileName); // this = SQLConClass.class
        Properties emailConfig = new Properties();
        if (inputFile != null) {
            emailConfig.load(inputFile);
        } else {
            throw new FileNotFoundException("Property file '" + propFileName + "' no found in the classpath");
        }

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(emailConfig.getProperty("jdbc.driver")));//Class<Driver>
        dataSource.setUrl(emailConfig.getProperty("jdbc.url"));
        dataSource.setUsername(emailConfig.getProperty("jdbc.username"));
        dataSource.setPassword(emailConfig.getProperty("jdbc.password"));
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public boolean insertEmail(EmailMessage emailMessage) throws IOException, ClassNotFoundException {
        proP();
        return jdbcTemplate.update(SQL_INSERT_EMAIL, emailMessage.getAuthTOkenId(), emailMessage.getFrom(),
                emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getBody()) > 0;
    }

    @Override
    public EmailMessage getEmailById(String id) throws IOException, ClassNotFoundException {
        proP();
        return jdbcTemplate.queryForObject(SQL_FIND_EMAIL, new Object[]{id}, new EmailMapper());
    }

}

