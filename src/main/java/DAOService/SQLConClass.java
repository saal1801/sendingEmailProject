package main.java.DAOService;


import main.java.dto.EmailMessage;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SQLConClass {
    public static final SQLConClass INSTANCE = new SQLConClass();

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


    private EmailMessage emailMessage = new EmailMessage();

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

    public EmailMessage getById(String id) throws SQLException {

        Map<String, EmailMessage> param = new HashMap<>();

        String sql = "SELECT * FROM emaildb.emailpro where id='" + id +"'";



       /* for ( Map.Entry<String,Object> entry: param.entrySet()) {
            param.put("id", id);
          //Object object = entry.getKey();   
        if (entry.getValue() instanceof String) {
            entry.getValue().equals(entry.getKey());
        }else if (entry.getValue() instanceof Class){
            System.out.println(("Class " + ((Class)entry.getValue()).getClass()));
        }else {
            throw new IllegalStateException("Expecting either String or Class value");
        }
    }*/

        Statement stmt = SQLConClass.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            emailMessage.setFrom(rs.getString("EmailSender"));
            emailMessage.setTo(rs.getString("EmailReceiver"));
            emailMessage.setSubject(rs.getString("subject"));
            emailMessage.setBody(rs.getString("body"));

            param.put(rs.getString("id"),emailMessage);

        }
        System.out.println(param.containsValue("id"));
        for (EmailMessage email: param.values()) {
            if (email.equals(id))
            return email;
        }
    return new EmailMessage();
    }

}

