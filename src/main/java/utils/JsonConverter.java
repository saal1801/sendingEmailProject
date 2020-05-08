package main.java.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.dto.EmailMessage;

public class JsonConverter {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:MM:ss.SSS'Z'")
            .serializeSpecialFloatingPointValues().create();

    private static EmailMessage emailMessageClass;

    public static String convertToJson(Object toConvert) {
        return gson.toJson(toConvert);
    }

    public static EmailMessage convertFromJson() {
        return JsonConverter.emailMessageClass;
    }

    public static void setEmailMessageClass(EmailMessage emailMessageClass) {
        JsonConverter.emailMessageClass = emailMessageClass;
    }
}
