package main.java.emailServer;


import main.java.DOAService.DOAServerLocal;
import main.java.dto.EmailMessage;

public class EmailService {
    public static final EmailService INSTANCE = new EmailService();
    //private static final Logger _log = Logger.getLogger(EmailService.class);

    private DOAServerLocal daoServer;

    public main.java.dto.EmailResponse emailUser(EmailMessage request){
        EmailMessage sqlConClassById = daoServer.getEmailId(request.authTOken);

        if(sqlConClassById == null || !(sqlConClassById.getAuthTOken() == (request.authTOken))){
            System.out.println("Wrong email server id " + request.authTOken);
        }
        return new main.java.dto.EmailResponse(EmailMessage.WRONG_EMAIL_ADDRESS_STATUS,"Wrong Email address");

    }
}
