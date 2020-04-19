package main.java.com.email.webserver;


import main.java.com.email.webserver.dao.EmailMessage;
import org.quartz.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.UUID;
//import org.onosproject.app.ApplicationAdminService;

@Path("/rest")
public class EmailRestServer {



    @GET
    @Path("/start")
    @Produces({"text/html"})
    public Response getStartPage(){

        StringBuilder builder = new StringBuilder();

        String html = HTMLReader.INBOX_PAGE;
        builder.append(html);

        return Response.ok(builder.toString()).build();
    }




    private Scheduler scheduler;

    @POST
    @Path("/email")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response queueEmail(EmailMessage emailMessage ) throws SchedulerException {
    // Insert email into Database.
    // Return email reference from database.


        String incoming = "";
        HttpServletRequest req = null;

    try {
        EmailMessage emailMessageReq = JsonConverter.convertFromJson(incoming, EmailMessage.class);

        SQLConClass.INSTANCE.getConnection(emailMessage.getFrom(),
                                           emailMessage.getTo(),
                                           emailMessage.getSubject(),
                                           emailMessage.getBody() );


        if (emailMessage.status == EmailMessage.STATUS_OK) {
            emailMessage.authTOken = UUID.randomUUID().toString();
            emailMessageReq.sessionId = req.getSession().getId();
        }

    }catch (Throwable t){

        emailMessage = new EmailMessage(HttpURLConnection.HTTP_BAD_REQUEST, t.getMessage());
        }

    /*JobDetail jobDetail = buliderJobDetail(emailMessage);
    Trigger trigger = builderJobTrigger(jobDetail);
    scheduler.scheduleJob(jobDetail,trigger);*/

        String json = JsonConverter.convertToJson(emailMessage);

        return Response.status(200).entity(json).build();
    }

    private JobDetail buliderJobDetail(EmailMessage emailMessage){
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("address", emailMessage.from);
        jobDataMap.put("toAddress", emailMessage.to);
        jobDataMap.put("subject", emailMessage.subject);
        jobDataMap.put("body", emailMessage.body);

        return  JobBuilder.newJob(HallowJobClass.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }
    private Trigger builderJobTrigger(JobDetail jobDetail){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                //.startAt(Date.from(startAt.toInstant()))
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();

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
            //sqlConClass.getConnection();

            builder.append("\t Message sent");

        } catch (ParseException | IOException | MessagingException e) {
            e.printStackTrace();
            sqlConClass.run();
            builder.append(" \t Message failed");
        }


        String output = builder.toString();


        return Response.status(200).entity(output).build();
    }

}
