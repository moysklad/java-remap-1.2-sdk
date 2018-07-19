package com.lognex.api.utils.json;

import com.google.gson.*;
import com.lognex.api.entities.ContextEntity;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DocumentPosition;
import com.lognex.api.responses.ListEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListEntityDeserializer implements JsonDeserializer<ListEntity> {
    private static final Logger logger = LogManager.getLogger(ListEntityDeserializer.class);

    @Override
    public ListEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ListEntity le = new ListEntity();
        le.setMeta(context.deserialize(((JsonObject) json).getAsJsonObject("meta"), Meta.class));
        le.setContext(context.deserialize(((JsonObject) json).getAsJsonObject("context"), ContextEntity.class));
        le.setRows(new ArrayList());

        if (typeOfT instanceof ParameterizedType) {
            Type pcl = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];

            JsonArray rows = ((JsonObject) json).getAsJsonArray("rows");
            if (rows != null && rows.size() > 0) {
                for (JsonElement row : rows) {
                    le.getRows().add(context.deserialize(row, pcl));
                }
            }
        } else {
            JsonArray rows = ((JsonObject) json).getAsJsonArray("rows");
            if (rows != null && rows.size() > 0) {
                for (JsonElement row : rows) {
                    Class<? extends MetaEntity> metaClass = MetaEntity.class;

                    try {
                        Meta.Type metaType = Meta.Type.valueOf(((JsonObject) row).get("meta").getAsJsonObject().get("type").getAsString());

                        switch (metaType) {
                            case demandposition:
                            case supplyposition:
                                metaClass = DocumentPosition.class;
                                break;
                        }
                    } catch (Exception e) {
                        logger.warn("Ошибка во время десериализации массива rows", e);
                    }

                    le.getRows().add(context.deserialize(row, metaClass));
                }
            }
        }

        return le;
    }
}
