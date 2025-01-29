package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

import java.io.IOException;
import java.util.Map;

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
    public static class Serializer extends JsonSerializer<Barcode> {
        @Override
        public void serialize(Barcode barcode, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            ObjectMapper objectMapper = (ObjectMapper) gen.getCodec();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put(barcode.getType().toString().toLowerCase(), barcode.getValue());
            gen.writeTree(objectNode);
        }
    }

    public static class Deserializer extends JsonDeserializer<Barcode> {
        @Override
        public Barcode deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            if (node.size() != 1) {
                throw new IOException("Can't parse field 'barcode': object contains more or less than 1 field");
            }

            Map.Entry<String, JsonNode> entry = node.fields().next();
            Barcode barcode = new Barcode();
            barcode.setType(Type.valueOf(entry.getKey().toUpperCase()));
            barcode.setValue(entry.getValue().asText());

            return barcode;
        }
    }
}
