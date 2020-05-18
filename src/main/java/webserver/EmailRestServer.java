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

private SQLConClass sqlConClass;
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
                emailMessage = new EmailMessage(false, "error scheduling email. Please try later!");
                return Response.status(WRONG_EMAIL_ADDRESS_STATUS,emailMessage.getAuthTOkenId()).entity(emailMessage).build();
            }
    }

    @GET
    @Path("/email/{id}")
    @Produces({"Application/json"})
    public Response retrieveMail(@PathParam("id") String id) throws SQLException {
        SQLConClass sqlConClass = new SQLConClass();

        final EmailMessage emailMessage = sqlConClass.getById(id);
       //sqlConClass.getById(id);

        return Response.status(STATUS_OK,EmailMessage.INSTANCE.getAuthTOkenId())
                                .entity(emailMessage).build();
    }

    /*@GET
    @Path("/email/{id}")
    @Produces({"Application/json"})
    public StreamingOutput retrieveMaill(@PathParam("id") String id) throws SQLException {
        SQLConClass sqlConClass = new SQLConClass();

        final EmailMessage emailMessage = sqlConClass.getById(id);
        if (emailMessage == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return new StreamingOutput(){
               public void write(OutputStream outpoutStream)
                   throws IOException, WebApplicationException{


               }
        };
    }*/

    @PUT
    @Path("/email/{id}")
    @Consumes("application/json")
    public Response updateEmail(@PathParam("id") String id, String updateEmail) throws SQLException {

        EmailMessage update = readEmail(updateEmail);
        EmailMessage current = sqlConClass.getById(id);

        current.setFrom(update.getFrom());
        current.setTo(update.getTo());
        current.setSubject(update.getSubject());
        current.setBody(update.getBody());

        return Response.status(STATUS_OK, current.getAuthTOkenId()).entity(id).build();
    }

    public EmailMessage readEmail(String update){

        //if-statement
        return new EmailMessage();
    }

}
