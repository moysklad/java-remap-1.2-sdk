package ru.moysklad.remap_1_2.serializers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDateTime.now;
import static org.junit.Assert.*;

public class LocalDateTimeSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_localDateTimeSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        LocalDateTime now = now();
        String expected = "\"" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "\"";

        assertNotEquals(expected, objectMapper.writeValueAsString(now));
        assertEquals(expected, objectMapperCustom.writeValueAsString(now));
    }

    @Test
    public void test_localDateTimeDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        LocalDateTime expected = now().truncatedTo(ChronoUnit.MILLIS);
        String date = "\"" + expected.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "\"";

        try {
            objectMapper.readValue(date, LocalDateTime.class);
            fail("Ожидалось исключение JsonProcessingException!");
        } catch (JsonProcessingException ignored) {
        }

        assertEquals(
                expected,
                objectMapperCustom.readValue(date, LocalDateTime.class)
        );
    }
}
