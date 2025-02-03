package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Optional;

public class OptionalEmptyAsNullSerializer extends JsonSerializer<Optional> {

    @Override
    public void serialize(Optional value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (!value.isPresent()) {
            gen.writeNull();
        } else {
            gen.writeObject(value.get());
        }
    }
}
