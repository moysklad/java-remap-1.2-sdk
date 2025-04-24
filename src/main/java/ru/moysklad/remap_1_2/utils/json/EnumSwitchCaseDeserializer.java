package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

public class EnumSwitchCaseDeserializer<T extends Enum<T>> extends StdDeserializer<T> {

    private final Class<T> enumClass;

    public EnumSwitchCaseDeserializer(Class<T> t) {
        super(t);
        this.enumClass = t;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().toUpperCase();
        return Enum.valueOf(enumClass, value);
    }
}