package main.java.com.email.webserver;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.text.ParseException;

public class EmailService {
    private String toAddress;
    private String subject;
    private String message;

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    void sendPlainTextEmail(final String toAddress, final String subject, final String message) throws AddressException, MessagingException, ParseException, IOException {

        EmailService emailService = new EmailService();
        emailService.setToAddress(toAddress);
        emailService.setSubject(subject);
        emailService.setMessage(message);


    }
}
