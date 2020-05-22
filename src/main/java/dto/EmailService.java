package main.java.dto;

import java.util.ArrayList;
import java.util.List;

public class EmailService extends EmailMessage {
    private static List<EmailService> emailServiceList = new ArrayList<>();

    public EmailMessage getEmailById(String id){
        for (EmailMessage emailMessage: emailServiceList) {
            if(emailMessage.getAuthTOkenId() == id){
                return emailMessage;

            }

        }
        return null;
    }

}
