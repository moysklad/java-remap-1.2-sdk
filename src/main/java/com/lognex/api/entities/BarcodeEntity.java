package com.lognex.api.entities;

import com.google.gson.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BarcodeEntity {
    /**
     * Тип штрихкода
     */
    Type type;

    /**
     * Штрихкод
     */
    String barcode;

    public enum Type {
        ean13, ean8, code128, gtin
    }

    /**
     * Сериализатор/десериализатор штрихкода
     */
    public static class Serializer implements JsonSerializer<BarcodeEntity>, JsonDeserializer<BarcodeEntity> {
        @Override
        public BarcodeEntity deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            BarcodeEntity e = new BarcodeEntity();
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();

            if (entries.size() != 1) {
                throw new JsonParseException("Can't parse field 'barcode': object contains more or less than 1 field");
            }

            Map.Entry<String, JsonElement> elementEntry = entries.iterator().next();
            e.type = Type.valueOf(elementEntry.getKey());
            e.barcode =  elementEntry.getValue().getAsString();

            return e;
        }

        @Override
        public JsonElement serialize(BarcodeEntity src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject e = new JsonObject();
            e.add(src.type.toString(), context.serialize(src.barcode));

            return e;
        }
    }
}
