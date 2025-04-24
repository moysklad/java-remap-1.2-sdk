package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DocumentTemplate {
    private String documentType;
    private DocumentEntity document;
    private List<DocumentEntity> documents;

    /**
     * Сериализатор шаблона для создания документов
     */
    public static class Serializer extends JsonSerializer<DocumentTemplate> {
        @Override
        public void serialize(DocumentTemplate src, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            ObjectMapper objectMapper = (ObjectMapper) jsonGenerator.getCodec();
            ObjectNode serializedDocumentTemplate = objectMapper.createObjectNode();
            if (src.getDocument() != null) {
                ObjectNode element = objectMapper.createObjectNode();

                JsonNode metaNode = objectMapper.valueToTree(src.getDocument().getMeta());

                element.set("meta", metaNode);
                serializedDocumentTemplate.set(src.getDocumentType(), element);
            } else if (src.getDocuments() != null) {
                ArrayNode metaArray = objectMapper.createArrayNode();

                for (DocumentEntity document: src.getDocuments()) {
                    ObjectNode element = objectMapper.createObjectNode();
                    JsonNode metaNode = objectMapper.valueToTree(document.getMeta());
                    element.set("meta", metaNode);
                    metaArray.add(element);
                }

                serializedDocumentTemplate.set(src.getDocumentType(), metaArray);
            }

            jsonGenerator.writeTree(serializedDocumentTemplate);
        }
    }
}
