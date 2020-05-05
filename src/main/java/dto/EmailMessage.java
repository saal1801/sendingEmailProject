package main.java.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.HttpURLConnection;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class EmailMessage {


    public String from;
    public String to;
    public String subject;
    public  String body;

    private boolean success;
    private String jobid;
    private String jobGroup;


    public String msg;
    public int status;
    public String authTOken;
    public String sessionId;

    //public LocalDateTime dateTime;
    //private ZoneId timeZone;

    public EmailMessage(){
    }

    public EmailMessage(int code, String message) {
        this.status = code;
        this.msg = message;
    }

    public EmailMessage( boolean success, String jobId, String jobGroup, String msg) {
        this.from = from;
        this.success = success;
        this.jobid = jobId;
        this.jobGroup = jobGroup;
        this.msg = msg;
    }

    public EmailMessage(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static int STATUS_OK = HttpURLConnection.HTTP_ACCEPTED;
    public static int WRONG_EMAIL_ADDRESS_STATUS = HttpURLConnection.HTTP_UNAUTHORIZED;

    public String getAuthTOken() {
        return authTOken;
    }

    public void setAuthTOken(String authTOken) {
        this.authTOken = authTOken;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", success=" + success +
                ", jobid='" + jobid + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                ", authTOken='" + authTOken + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
