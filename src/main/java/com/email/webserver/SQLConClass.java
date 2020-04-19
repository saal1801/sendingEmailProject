package main.java.com.email.webserver;


import main.java.com.email.webserver.dao.EmailMessage;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLConClass{

    //Singleton instance
    public static final SQLConClass INSTANCE = new SQLConClass();

    public static void getConnection( String from, String to, String subject, String body ) throws ParseException, ClassNotFoundException, SQLException {


        /*String to="hej";
        String subject = "hejj";
        String body="hahah";*/

        //EmailMessage emailMessage = new EmailMessage();

        SimpleDateFormat simplformat = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss.SSS'Z' a");
        String strDate = simplformat.format(new Date());
        Date date = simplformat.parse(strDate);

        String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=false"; //jdbc:mysql://localhost:3306/emailpro?user=root&password=root
        String user = "root";
        String password = "password";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url,user,password);
        //Statement stmt =  conn.createStatement();


        System.out.println("from:::::::::::::::" + from);
        System.out.println("to:::::::::::::::" + to);
        System.out.println("subject:::::::::::::::" + subject);
        System.out.println("body:::::::::::::::" + body);


        /*Statement stmt;
        String sql;
        int rows;

        sql = "INSERT INTO emailpro "
                + "(from_email) "
                + "VALUES "
                + "('" + from + "')";

        stmt = conn.createStatement();
        rows = stmt.executeUpdate(sql);
        conn.commit();
        System.out.println(" row updated: " + rows);*/

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO emailpro (status,from_email,to_email,subject,body,Received_Date) value ('SENT',?,?,?,?,CURRENT_TIMESTAMP)" );
        if (stmt !=null) {

            stmt.setString(1,from);
            stmt.setString(2,from);
            stmt.setString(3,subject);
            stmt.setString(4,body);
            int rowUpdate = stmt.executeUpdate();
            System.out.println(" row updated::::::: " + rowUpdate);
        }






      /* if (stmt !=null) {
           int rowUpdate = stmt.executeUpdate("insert into emailpro (from_email) value (from  ");
           conn.commit();
           System.out.println(" row updated: " + rowUpdate);
           }*/

       /*if (conn ==null)
                {
                    int rowUpdate1 = stmt.executeUpdate("update emailpro set status = ' failed-retry-policy' where id = '1'");
                    System.out.println(" row updated with  failed-retry-policy: " + rowUpdate1);
                }*/

        String sqll = "select * from emaildb.emailpro";
        ResultSet rs = stmt.executeQuery(sqll);

            //retrieving
            while (rs. next()){
                System.out.println(rs.getString("from_email") + "\n" + rs.getString( "to_email") + "\n" + rs.getString("status")+ "\n" +  "\n" + "Received :" + rs.getString("Received_Date")+ "\n" + "Nest_Attept:" + rs.getString("Next_Attempt"));
            }
        rs.close();

        CallableStatement callableStatement  = conn.prepareCall("{CALL GetEmailPro()}");
         rs = callableStatement.executeQuery();

        //retrieving SP
        while (rs. next()){
            System.out.println("Execute SP: " + "\n" + "attempts:" + rs.getString("attempts") + "\n" +"Received_Date: "+ rs.getString("Received_Date"));
        }
        conn.close();
        stmt.close();
    }

    public void run() throws ParseException, SQLException, ClassNotFoundException, InterruptedException {

        String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=false"; //jdbc:mysql://localhost:3306/emailpro?user=root&password=root
        String user = "root";
        String password = "password";
        //for (int i = 5; i > 0 ; i--) {
        String threadName = null;

            System.out.println("running SendMailSSL " + threadName );
            Thread.sleep(0);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,password);

            Statement stmt = conn.createStatement();
            int rowUpdate = stmt.executeUpdate("insert into emailpro (status,Sent_Date) value ('failed',CURRENT_TIMESTAMP()-0100 )");
            System.out.println(" row updated with  failed: " + rowUpdate);


            CallableStatement callableStatement  = conn.prepareCall("{CALL GetEmailPro()}");
            ResultSet sprs = callableStatement.executeQuery();
        System.out.println(sprs);
            //new SendMailSSL();
        conn.close();
        stmt.close();
    }


}
