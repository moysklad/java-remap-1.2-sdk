package com.lognex.api.entities;

import com.google.gson.*;
import com.lognex.api.entities.documents.DocumentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DocumentTemplateEntity {
    private String documentType;
    private DocumentEntity document;
    private List<DocumentEntity> documents;

    /**
     * Сериализатор шаблона для создания документов
     */
    public static class Serializer implements JsonSerializer<DocumentTemplateEntity> {
        @Override
        public JsonElement serialize(DocumentTemplateEntity src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject e = new JsonObject();
            if (src.getDocument() != null) {
                JsonObject element = new JsonObject();

                element.add("meta", context.serialize(src.getDocument().getMeta()));

                e.add(src.getDocumentType(), element);
            } else if (src.getDocuments() != null) {
                JsonArray metaArray = new JsonArray();

                for (DocumentEntity document: src.getDocuments()) {
                    JsonObject element = new JsonObject();
                    element.add("meta", context.serialize(document.getMeta()));
                    metaArray.add(element);
                }

                e.add(src.getDocumentType(), metaArray);
            }

            return e;
        }
    }
}
