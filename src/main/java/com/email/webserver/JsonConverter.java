package main.java.com.email.webserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonConverter {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:MM:ss.SSS'Z'")
            .serializeSpecialFloatingPointValues().create();

    public static <T> T convertFromJson(String toConvet, Class<T> clszz){

        return (T) gson.fromJson(toConvet, clszz);
    }

    public static <T> T convertFromJson(String toConvert, Type typeOfT){
        return (T) gson.fromJson(toConvert, typeOfT);
    }
    public static String convertToJson(Object toConvert){
        return gson.toJson(toConvert);
    }
}
