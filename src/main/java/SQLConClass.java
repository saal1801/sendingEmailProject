package main.java;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SQLConClass{
    private PreparedStatement stmt;
    private Connection conn;

    /*static String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=false";
    static String user = "root";
    static String password = "password";

    static Connection conn;
    static Statement stmt;

    {
        try {
            conn = DriverManager.getConnection(url,user,password);
            stmt =  conn.createStatement();
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    //static SQLConClass sqlConClass = new SQLConClass();;




    public void run() {
         //for (int i = 5; i > 0 ; i--) {
            String threadName = null;
            try {
                System.out.println("running SendMailSSL " + threadName );
                Thread.sleep(50);
                //PreparedStatement stmt = null;
                int rowUpdate = stmt.executeUpdate("update emailpro set status_Attempt = ' failed' where id = '1'");
                System.out.println(" row updated with  failed-retry-policy: " + rowUpdate);
                CallableStatement callableStatement  = conn.prepareCall("{CALL GetEmailPro()}");
                ResultSet sprs = callableStatement.executeQuery();
                //new SendMailSSL();
            } catch (InterruptedException | SQLException e) {
                System.out.println("Thread " + threadName + "interrupted");
                e.printStackTrace();
            }
    }

    public static void getConnection() throws ParseException, ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://localhost:3306/emaildb?autoReconnect=true&useSSL=false"; //jdbc:mysql://localhost:3306/emailpro?user=root&password=root
        String user = "root";
        String password = "password";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url,user,password);
        Statement stmt =  conn.createStatement();



       if (stmt !=null) {
           int rowUpdate = stmt.executeUpdate("update emailpro set status = 'SENT' where id = '1'");
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
                System.out.println(rs.getString("from_email") + "\n" + rs.getString( "to_email") + "\n" + rs.getString("status")+ "\n" + rs.getString("Received_Date")+ "\n");

            }

        rs.close();

        CallableStatement callableStatement  = conn.prepareCall("{CALL GetEmailPro()}");
         rs = callableStatement.executeQuery();

        //retrieving SP
        while (rs. next()){
            System.out.println("Execute SP: " + "\n" + "attempts:" + rs.getString("attempts") + "\n" +"Received_Date: "+ rs.getString("Received_Date"));

        }
        conn.close();

    }

}
