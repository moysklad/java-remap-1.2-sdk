package ru.moysklad.remap_1_2.utils.json;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.moysklad.remap_1_2.utils.Constants.DATE_FORMAT_PATTERN;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private final DateTimeFormatter formatter;

    public LocalDateTimeDeserializer() {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String dateString = jp.getText();
        return LocalDateTime.parse(dateString, formatter);
    }
}
