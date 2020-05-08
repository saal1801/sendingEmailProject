package main.java.webserver;


import main.java.DOAService.SQLConClass;
import main.java.dto.EmailMessage;
import main.java.utils.JsonConverter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.UUID;

import static main.java.dto.EmailMessage.STATUS_OK;
import static main.java.dto.EmailMessage.WRONG_EMAIL_ADDRESS_STATUS;
//import org.onosproject.app.ApplicationAdminService;

@Path("/rest")
public class EmailRestServer {


    @POST
    @Path("/email")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response queueEmail(EmailMessage emailMessage) throws ClassNotFoundException, SQLException {

        emailMessage.authTOken = UUID.randomUUID().toString();

        if (emailMessage != null) {

            SQLConClass.insertEmail(emailMessage);

            String json = JsonConverter.convertToJson(emailMessage);
            return Response.status(STATUS_OK, emailMessage.getAuthTOken()).entity(json).build();
        } else {
            emailMessage = new EmailMessage(false, "error scheduling email. Please try later!");
            return Response.status(WRONG_EMAIL_ADDRESS_STATUS).entity(emailMessage).build();
        }

    }

}
