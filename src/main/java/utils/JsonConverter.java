package main.java.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.dto.EmailMessage;

import java.lang.reflect.Type;

public class JsonConverter {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:MM:ss.SSS'Z'")
            .serializeSpecialFloatingPointValues().create();

    private static EmailMessage emailMessageClass;

    public static <T> T convertFromJson(String toConvet, Class<T> clszz){

        return (T) gson.fromJson(toConvet, clszz);
    }

    public static <T> T convertFromJson(String toConvert, Type typeOfT){
        return (T) gson.fromJson(toConvert, typeOfT);
    }
    public static String convertToJson(Object toConvert){
        return gson.toJson(toConvert);
    }

    public static EmailMessage convertFromJson(Class<EmailMessage> emailMessageClass) {
        return JsonConverter.emailMessageClass;
    }



    /*public static EmailMessage convertFromJson(String emailMessage, Type emailMessageClass) {
        return (EmailMessage) gson.fromJson(emailMessage, emailMessageClass);
    }*/
}
