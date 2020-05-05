
package main.java.dto;

import main.java.DOAService.SQLConClass;

import java.net.HttpURLConnection;

public class EmailResponse {

    public static int STATUS_OK = HttpURLConnection.HTTP_ACCEPTED;
    public static int BAD_STATUS = HttpURLConnection.HTTP_UNAUTHORIZED;
    public int id;
    public String msg;
    public int status;
    public String authTOken;
    public String sessionId;

    public EmailResponse(int code, String message) {
        this.status = code;
        this.msg = message;

    }

}