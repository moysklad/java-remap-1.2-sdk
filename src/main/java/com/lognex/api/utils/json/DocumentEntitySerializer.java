package com.lognex.api.utils.json;

import com.google.gson.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DocumentEntity;

import java.lang.reflect.Type;


public class DocumentEntitySerializer implements JsonSerializer<DocumentEntity>, JsonDeserializer<DocumentEntity> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public JsonElement serialize(DocumentEntity src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    @Override
    public DocumentEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) {
            throw new JsonParseException("Can't parse field 'operation': meta is null");
        } else if (me.getMeta().getType() == null) {
            throw new JsonParseException("Can't parse field 'operation': meta.type is null");
        }

        if (!DocumentEntity.class.isAssignableFrom(me.getMeta().getType().getModelClass())) {
            throw new JsonParseException("Can't parse field 'operation': meta.type is not a valid document type");
        }

        return context.deserialize(json, me.getMeta().getType().getModelClass());
    }
}
