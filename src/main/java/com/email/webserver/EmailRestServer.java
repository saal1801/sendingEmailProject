package main.java.com.email.webserver;


import org.jboss.resteasy.client.jaxrs.internal.ClientResponse;

import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import org.onosproject.app.ApplicationAdminService;

@Path("/rest")
public class EmailRestServer {


    EmailTextClass emailTextClass = new EmailTextClass();
    EmailService emailService = new EmailService();


    @GET
    @Path("/email")
    @Produces({MediaType.TEXT_HTML})
    //@Consumes({MediaType.APPLICATION_JSON})
    public Response getEmail(@DefaultValue("sarko@gmail.com")@QueryParam("address") String address ,
                             @DefaultValue("hej")@QueryParam("subject")String subject,
                             @DefaultValue("test")@QueryParam("message")String message) throws IOException, MessagingException, ParseException {



        if(address == null || subject == null || message == null ){
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Address " + address + ", Subject " + subject + ", Message " + message)
                    .build());
        }

        String propFileName = "config.properties";
        InputStream inputFile = this.getClass().getClassLoader().getResourceAsStream(propFileName);
        Properties emailConfig = new Properties();

         if(inputFile !=null){
             emailConfig.load( inputFile );
         }else {
             throw new FileNotFoundException("Property file '" + propFileName + "' no found in the classpath" );
         }
               // System.out.println(emailConfig.getProperty("properties name" + "config.properties"));

        StringBuilder builder = new StringBuilder();
        builder.append("  address:  ").append(address)
                .append(",  subject:  ").append(subject)
                .append(",  message:  ").append(message);

        emailService.setToAddress(address);
        emailService.setSubject(subject);
        emailService.setMessage(message);
        emailTextClass.sendPlainTextEmail(address, subject, message);

        String output = builder.toString();


        return Response.status(200).entity(output).build();
    }

}
