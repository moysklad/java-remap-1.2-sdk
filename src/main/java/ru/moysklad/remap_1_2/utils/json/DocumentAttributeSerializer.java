package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.moysklad.remap_1_2.utils.Constants.DATE_FORMAT_PATTERN;

public class DocumentAttributeSerializer extends JsonSerializer<DocumentAttribute> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();
    private final DateTimeFormatter formatter;

    public DocumentAttributeSerializer() {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    }

    @Override
    public void serialize(DocumentAttribute src, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (src.getType() != null) {
            switch (src.getType()) {
                case timeValue:
                    if (src.getValue() != null) {
                        if (src.getValue() instanceof LocalDateTime) {
                            src.setValue(((LocalDateTime) src.getValue()).format(formatter));
                        } else if (src.getValue() instanceof LocalDate) {
                            src.setValue(((LocalDate) src.getValue()).format(formatter));
                        } else {
                            throw new IllegalArgumentException("Неподдерживаемый тип данных для дополнительного поля с типом 'time': " + src
                                    .getValue().getClass().getSimpleName());
                        }
                    }
                    break;
            }
            TypeFactory typeFactory = TypeFactory.defaultInstance();
            JavaType javaType = typeFactory.constructType(DocumentAttribute.class);
            BeanSerializerFactory.instance.createSerializer(serializers, javaType).serialize(src, gen, serializers);
        }
        else if (src.getEntityType() != null) {
            JsonNode node = objectMapper.valueToTree(src);
            ObjectNode objectNode = (ObjectNode) node;
            JsonNode entityTypeNode = objectNode.get("entityType");
            objectNode.set("type", entityTypeNode);
            objectNode.remove("entityType");
            ((ObjectMapper)gen.getCodec()).writeTree(gen, node);
        }
    }
}
