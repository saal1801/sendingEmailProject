package main.java.webserver;


//import main.java.DOAService.ApplicationBeanLocal;

import main.java.DAOService.SQLConClass;
import main.java.dto.EmailMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.UUID;

import static main.java.dto.EmailMessage.STATUS_OK;
import static main.java.dto.EmailMessage.WRONG_EMAIL_ADDRESS_STATUS;

//import main.java.DAOService.StreamingOutput;
//import org.onosproject.app.ApplicationAdminService;

@Path("/rest")
public class EmailRestServer {

    @POST
    @Path("/email")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response queueEmail(EmailMessage emailMessage) {

        emailMessage.authTOkenId = UUID.randomUUID().toString();

        try {
            SQLConClass.insertEmail(emailMessage);
            return Response.status(STATUS_OK, emailMessage.getAuthTOkenId()).entity(emailMessage).build();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Response.status(WRONG_EMAIL_ADDRESS_STATUS, emailMessage.getAuthTOkenId()).entity(emailMessage).build();
        }
    }

    @GET
    @Path("/email/{id}")
    @Produces({"Application/json"})
    public Response retrieveMail(@PathParam("id") String id) throws SQLException {

        EmailMessage emailMessage = SQLConClass.getById(id);
        if(emailMessage == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(STATUS_OK, EmailMessage.INSTANCE.getAuthTOkenId()).entity(emailMessage).build();
    }


    @PUT
    @Path("/email/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateEmail(@PathParam("id") String id, @QueryParam("emailMessage") EmailMessage emailMessage) throws SQLException {

        //EmailMessage update = new EmailMessage();
        EmailMessage current = SQLConClass.UpdateEmail(id, emailMessage);

        if(current == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

     /*   assert current != null;
        current.setFrom(update.getFrom());
        current.setTo(update.getTo());
        current.setSubject(update.getSubject());
        current.setBody(update.getBody());*/

        return Response.status(STATUS_OK, current.getAuthTOkenId()).entity(current).build();
    }



    @DELETE
    @Path("/email/{id}")
    @Consumes("application/json")
    public Response deleteEmail(@PathParam("id") String id) throws SQLException {

        EmailMessage current = SQLConClass.getById(id);
        if(current == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //current.cance();
        //SQLConClass.conn.close();
        return Response.status(STATUS_OK, current.getAuthTOkenId()).entity(id).build();
    }

}
