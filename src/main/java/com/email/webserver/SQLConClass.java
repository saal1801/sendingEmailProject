package main.java.com.email.webserver;


import javax.mail.Message;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SQLConClass{
    public static void getConnection() throws ParseException, ClassNotFoundException, SQLException {

        EmailTextClass emailTextClass = new EmailTextClass();
       // emailTextClass = Message.class.getR

        SimpleDateFormat simplformat = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss.SSS'Z' a");
        String strDate = simplformat.format(new Date());
        Date date = simplformat.parse(strDate);

        String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=false"; //jdbc:mysql://localhost:3306/emailpro?user=root&password=root
        String user = "root";
        String password = "password";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url,user,password);
        Statement stmt =  conn.createStatement();



       if (stmt !=null) {
           int rowUpdate = stmt.executeUpdate("insert into emailpro (status,Received_Date,Sent_Date) value ('SENT',CURRENT_TIMESTAMP()+0200,CURRENT_TIMESTAMP()-0200 )");
           System.out.println(" row updated: " + rowUpdate);
           }

       /*if (conn ==null)
                {
                    int rowUpdate1 = stmt.executeUpdate("update emailpro set status = ' failed-retry-policy' where id = '1'");
                    System.out.println(" row updated with  failed-retry-policy: " + rowUpdate1);
                }*/

        String sql = "select * from emaildb.emailpro";
        ResultSet rs = stmt.executeQuery(sql);

            //retrieving
            while (rs. next()){
                System.out.println(rs.getString("from_email") + "\n" + rs.getString( "to_email") + "\n" + rs.getString("status")+ "\n" + "Sent date:" + rs.getString("Sent_Date")+ "\n" + "Received :" + rs.getString("Received_Date")+ "\n");

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
        getConnection();
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
