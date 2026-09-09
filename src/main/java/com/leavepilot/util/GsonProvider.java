package com.leavepilot.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public final class GsonProvider {

    private GsonProvider() {
    }

    public static Gson create() {
        JsonSerializer<LocalDate> localDateSerializer =
                (source, type, context) -> context.serialize(source.toString());
        JsonDeserializer<LocalDate> localDateDeserializer =
                (json, type, context) -> LocalDate.parse(json.getAsString());

        JsonSerializer<LocalDateTime> localDateTimeSerializer =
                (source, type, context) -> context.serialize(source.toString());
        JsonDeserializer<LocalDateTime> localDateTimeDeserializer =
                (json, type, context) -> LocalDateTime.parse(json.getAsString());

        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, localDateSerializer)
                .registerTypeAdapter(LocalDate.class, localDateDeserializer)
                .registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer)
                .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer)
                .create();
    }
}
