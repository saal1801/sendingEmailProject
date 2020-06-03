package main.java.webserver;


//import main.java.DOAService.ApplicationBeanLocal;

import main.java.DAOService.SQLConClass;
import main.java.dto.EmailMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static main.java.dto.EmailMessage.STATUS_OK;
import static main.java.dto.EmailMessage.WRONG_EMAIL_ADDRESS_STATUS;


@Path("/rest")
public class EmailRestServer {
    private SQLConClass sqlConClass = new SQLConClass();

    @POST
    @Path("/email")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response queueEmail(EmailMessage emailMessage) {

        emailMessage.authTOkenId = UUID.randomUUID().toString();

        try {
            sqlConClass.insertEmail(emailMessage);
            return Response.status(STATUS_OK, emailMessage.getAuthTOkenId()).entity(emailMessage).build();

        } catch (IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return Response.status(WRONG_EMAIL_ADDRESS_STATUS, emailMessage.getAuthTOkenId()).entity(emailMessage).build();
        }
    }

    @GET
    @Path("/email/{id}")
    @Produces({"Application/json"})
    public Response retrieveMail(@PathParam("id") String id) throws IOException, ClassNotFoundException {

        SQLConClass sqlConClass = new SQLConClass();
        EmailMessage emailMessage = sqlConClass.getEmailById(id);
        if (emailMessage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(STATUS_OK, EmailMessage.INSTANCE.getAuthTOkenId()).entity(emailMessage).build();
    }


}
