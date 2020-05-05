package main.java.DOAService;


import main.java.dto.EmailMessage;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLConClass {

    //Singleton instance
    public static final SQLConClass INSTANCE = new SQLConClass();

    public static final String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=false";
    public static final String user = "root";
    public static final String password = "password";

    public static void insertEmail(EmailMessage emailMessage) throws ParseException, ClassNotFoundException, SQLException {


        SimpleDateFormat simplformat = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss.SSS'Z' a");
        String strDate = simplformat.format(new Date());
        Date date = simplformat.parse(strDate);

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);


        PreparedStatement stmt = conn.prepareStatement("INSERT INTO emailpro (id,EmailSender,EmailReceiver,subject,body,Received_Date) value (?,?,?,?,?,CURRENT_TIMESTAMP)");
        if (stmt != null) {

            stmt.setObject(1, emailMessage.getAuthTOken());
            stmt.setString(2, emailMessage.getFrom());
            stmt.setString(3, emailMessage.getTo());
            stmt.setString(4, emailMessage.getSubject());
            stmt.setString(5, emailMessage.getBody());

            // emailMessage.getAuthToken...

            int rowUpdate = stmt.executeUpdate();
            System.out.println(" SQL row updated::::::: " + rowUpdate);

        }

        conn.close();
        stmt.close();
    }

    public void readEmail(EmailMessage emailMessage) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);

        String sql = "SELECT * FROM emaildb.emailpro WHERE Received_Date >= NOW() - INTERVAL 2 MINUTE";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            emailMessage.setFrom(rs.getString("EmailSender"));
            emailMessage.setTo(rs.getString("EmailReceiver"));
            emailMessage.setSubject(rs.getString("subject"));
            emailMessage.setBody(rs.getString("body"));

            System.out.println(rs.getString("EmailSender") +
            rs.getString("EmailReceiver") +
            rs.getString("subject") +
            rs.getString("body"));

        }
        conn.close();
        stmt.close();
    }

}
