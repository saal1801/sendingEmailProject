package main.java.com.email.webserver;


import main.java.com.email.webserver.dao.EmailMessage;
import org.jboss.resteasy.client.jaxrs.internal.ClientResponse;

import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import org.onosproject.app.ApplicationAdminService;

@Path("/rest")
public class EmailRestServer {



    @GET
    @Path("/start")
    @Produces({MediaType.TEXT_HTML})
    public Response getStartPage(){

        StringBuilder builder = new StringBuilder();

        String HTML = HTMLReader.INBOX_PAGE;
        builder.append(HTML);

        return Response.ok(builder.toString()).build();
    }






    @POST
    @Path("/email")
    //@Produces({MediaType.APPLICATION_JSON})
    @Consumes("application/json")
    public Response queueEmail(EmailMessage emailMessage) throws ParseException, SQLException, ClassNotFoundException, InterruptedException {
// Insert email into Database.
// Return email reference from database.


        return Response.status(200).entity("post").build();
    }

    @GET
    @Path("/getEmail")
    @Produces({MediaType.TEXT_HTML})
    //@Consumes({MediaType.APPLICATION_JSON})
    public Response getEmail(@DefaultValue("sarko@gmail.com")@QueryParam("address") String address ,
                             @DefaultValue("hej")@QueryParam("subject")String subject,
                             @DefaultValue("test")@QueryParam("message")String message) throws ParseException, SQLException, ClassNotFoundException, InterruptedException {

// For reference XXX get status from database.

        if(address == null || subject == null || message == null ){
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Address " + address + ", Subject " + subject + ", Message " + message + " \\n" )
                    .build());
        }
        /*else {
            throw new WebApplicationException(Response.status(Response.Status.OK).entity("Message sent").build());
        }*/

        /*String propFileName = "config.properties";
        InputStream inputFile = this.getClass().getClassLoader().getResourceAsStream(propFileName);
        Properties emailConfig = new Properties();

         if(inputFile !=null){
             emailConfig.load( inputFile );
         }else {
             throw new FileNotFoundException("Property file '" + propFileName + "' no found in the classpath" );
         }*/
               // System.out.println(emailConfig.getProperty("properties name" + "config.properties"));

        StringBuilder builder = new StringBuilder();
        builder.append("  address:  ").append(address)
                .append(",  subject:  ").append(subject)
                .append(",  message:  ").append(message);


        /*emailService.setToAddress(address);
        emailService.setSubject(subject);
        emailService.setMessage(message);*/

        EmailTextClass emailTextClass = new EmailTextClass();
        SQLConClass sqlConClass = new SQLConClass();
        try {
            emailTextClass.sendPlainTextEmail(address, subject, message);
            emailTextClass.receiveMail();
            sqlConClass.getConnection();

            builder.append("\t Message sent");

        } catch (ParseException | IOException | MessagingException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            sqlConClass.run();
            builder.append(" \t Message failed");
        }


        String output = builder.toString();


        return Response.status(200).entity(output).build();
    }

}
