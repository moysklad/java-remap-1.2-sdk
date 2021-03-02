package ru.moysklad.remap_1_2.entities;

import com.google.gson.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;

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
    public static class Serializer implements JsonSerializer<DocumentTemplate> {
        @Override
        public JsonElement serialize(DocumentTemplate src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject serializedDocumentTemplate = new JsonObject();
            if (src.getDocument() != null) {
                JsonObject element = new JsonObject();

                element.add("meta", context.serialize(src.getDocument().getMeta()));

                serializedDocumentTemplate.add(src.getDocumentType(), element);
            } else if (src.getDocuments() != null) {
                JsonArray metaArray = new JsonArray();

                for (DocumentEntity document: src.getDocuments()) {
                    JsonObject element = new JsonObject();
                    element.add("meta", context.serialize(document.getMeta()));
                    metaArray.add(element);
                }

                serializedDocumentTemplate.add(src.getDocumentType(), metaArray);
            }

            return serializedDocumentTemplate;
        }
    }
}
