package main.java.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.net.HttpURLConnection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailMessage {

    public static final EmailMessage INSTANCE = new EmailMessage();
    public String authTOkenId;
    public String from;
    public String to;
    public String subject;
    public String body;

    public EmailMessage() {
    }

    public static final int STATUS_OK = HttpURLConnection.HTTP_ACCEPTED;
    public static final int WRONG_EMAIL_ADDRESS_STATUS = HttpURLConnection.HTTP_UNAUTHORIZED;


    public String getAuthTOkenId() {
        return authTOkenId;
    }

    public void setAuthTOkenId(String authTOkenId) {
        this.authTOkenId = authTOkenId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "EmailMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
