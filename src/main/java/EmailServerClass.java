/*package main.java;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


    //@WebServlet("/EmailServlet")
    public class EmailServerClass extends HttpServlet {
        private String host;
        private String port;
        private String userName;
        private String password;

    public void init(){
        //reads SMTP server sitting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("post");
        userName = context.getInitParameter("userName");
        password = context.getInitParameter("password");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String host = "smtp.mailtrap.io";
        String port = "25255";


        //reads from fields
        String toAddress = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");


        SQLConClass sqlConClass = null;
        String sendingResultMessage = "";

        try {
            sqlConClass = new SQLConClass();

        sqlConClass.getConnection();
        System.out.println("from SQL " + sqlConClass);


            EmailSmtp.sendPlainTextEmail( host,  port,
            userName, password,  toAddress, subject,  content);

            sendingResultMessage = "Email sent successfully....";

        } catch (SQLException | MessagingException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
            sendingResultMessage ="Failed to sending this email.... " + e.getMessage();
            sqlConClass.run();

        } finally {
            request.setAttribute("Message ", sendingResultMessage);
            getServletContext().getRequestDispatcher("/StatusPage.html").forward(request, response);
        }
    }
}*/
