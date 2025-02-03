package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.moysklad.remap_1_2.utils.Constants.DATE_FORMAT_PATTERN;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private final DateTimeFormatter formatter;

    public LocalDateTimeSerializer() {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formattedDate = value.format(formatter);
        gen.writeString(formattedDate);
    }
}
