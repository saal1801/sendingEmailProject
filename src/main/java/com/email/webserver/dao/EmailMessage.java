package main.java.com.email.webserver.dao;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.net.HttpURLConnection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailMessage {

    public String from;
    public String to;
    public String subject;
    public  String body;

    public String msg;
    public int status;

    public String authTOken;
    public String sessionId;

    public EmailMessage(){
    }

    public EmailMessage(int code, String message) {
        this.status = code;
        this.msg = message;
    }

    public static int STATUS_OK = HttpURLConnection.HTTP_ACCEPTED;
    public static int BAD_STATUS = HttpURLConnection.HTTP_UNAUTHORIZED;

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
}
