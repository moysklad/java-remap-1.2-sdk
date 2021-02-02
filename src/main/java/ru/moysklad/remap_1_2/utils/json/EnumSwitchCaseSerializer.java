package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;

public class EnumSwitchCaseSerializer<T extends Enum<T>> implements JsonSerializer<T>, JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Enum.valueOf((Class<T>) typeOfT, json.getAsString().toUpperCase());
    }

    @Override
    public JsonElement serialize(T src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString().toLowerCase());
    }
}
