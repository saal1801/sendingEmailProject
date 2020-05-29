package main.java.DAOService;


import main.java.dto.EmailMessage;

import java.sql.*;

public class SQLConClass {

    public static final String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=true";
    public static final String user = "root";
    public static final String password = "password";

    public static Connection conn;


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void insertEmail(EmailMessage emailMessage) throws SQLException {

        PreparedStatement stmt = SQLConClass.conn.prepareStatement("INSERT INTO emailpro (id,EmailSender,EmailReceiver,subject,body,Received_Date) value (?,?,?,?,?,CURRENT_TIMESTAMP)");
        if (stmt != null) {

            stmt.setObject(1, emailMessage.getAuthTOkenId());
            stmt.setString(2, emailMessage.getFrom());
            stmt.setString(3, emailMessage.getTo());
            stmt.setString(4, emailMessage.getSubject());
            stmt.setString(5, emailMessage.getBody());

            int rowUpdate = stmt.executeUpdate();
            System.out.println(" SQL row updated::::::: " + rowUpdate);
        }
    }

    public static EmailMessage getById(String id) throws SQLException {

        EmailMessage emailMessage;
        String sql = "SELECT id, EmailSender, EmailReceiver, subject, body" + " FROM emaildb.emailpro WHERE id=?"; //  '" + id + "'"
        PreparedStatement stmt = SQLConClass.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, id);

        ResultSet rs = stmt.executeQuery();
        emailMessage = new EmailMessage();

        if (rs.next()) {
            emailMessage.setAuthTOkenId(id);
            emailMessage.setFrom(rs.getString("EmailSender"));
            emailMessage.setTo(rs.getString("EmailReceiver"));
            emailMessage.setSubject(rs.getString("subject"));
            emailMessage.setBody(rs.getString("body"));
            return emailMessage;
        } else {
            rs.close();
            stmt.close();
            if (conn != null) conn.close();
            return null;
        }
    }

    public static EmailMessage updateEmail(String id, EmailMessage emailMessage1) throws SQLException {

        PreparedStatement stmt = SQLConClass.conn.prepareStatement("UPDATE emaildb.emailpro SET EmailSender=?, EmailReceiver=?, subject=?, body=? WHERE id=?");
        stmt.setString(1, emailMessage1.from);
        stmt.setString(2, emailMessage1.to);
        stmt.setString(3, emailMessage1.subject);
        stmt.setString(4, emailMessage1.body);
        stmt.setString(5, id);
        stmt.executeUpdate();

        emailMessage1 = SQLConClass.getById(id);

        //stmt.close();
        return emailMessage1;
    }

    public static String deleteEmail(String id) throws SQLException {

        PreparedStatement stmt = SQLConClass.conn.prepareStatement("DELETE FROM emaildb.emailpro WHERE id=?");
        stmt.setString(1, id);
        stmt.executeUpdate();

        stmt.close();
        return "Email is deleted successfully " + id;
    }
}

