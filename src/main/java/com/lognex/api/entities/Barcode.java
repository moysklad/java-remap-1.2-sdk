package com.lognex.api.entities;

import com.google.gson.*;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Barcode {
    /**
     * Тип штрихкода
     */
    private Type type;

    /**
     * Штрихкод
     */
    private String value;

    public enum Type {
        EAN13, EAN8, CODE128, GTIN, UPC
    }

    /**
     * Сериализатор/десериализатор штрихкода
     */
    public static class Serializer implements JsonSerializer<Barcode>, JsonDeserializer<Barcode> {
        @Override
        public Barcode deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Barcode e = new Barcode();
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();

            if (entries.size() != 1) {
                throw new JsonParseException("Can't parse field 'barcode': object contains more or less than 1 field");
            }

            Map.Entry<String, JsonElement> elementEntry = entries.iterator().next();
            e.type = Type.valueOf(elementEntry.getKey().toUpperCase());
            e.value =  elementEntry.getValue().getAsString();

            return e;
        }

        @Override
        public JsonElement serialize(Barcode src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject e = new JsonObject();
            e.add(src.type.toString().toLowerCase(), context.serialize(src.value));

            return e;
        }
    }
}
