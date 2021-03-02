package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.moysklad.remap_1_2.entities.Context;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListEntityDeserializer implements JsonDeserializer<ListEntity> {
    private static final Logger logger = LoggerFactory.getLogger(ListEntityDeserializer.class);

    @Override
    public ListEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ListEntity le = new ListEntity();
        le.setMeta(context.deserialize(((JsonObject) json).getAsJsonObject("meta"), Meta.class));
        le.setContext(context.deserialize(((JsonObject) json).getAsJsonObject("context"), Context.class));

        JsonArray rows = ((JsonObject) json).getAsJsonArray("rows");
        if (rows != null) {
            le.setRows(new ArrayList(rows.size()));
            if (rows.size() > 0) {
                if (typeOfT instanceof ParameterizedType) {
                    Type pcl = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];

                    for (JsonElement row : rows) {
                        le.getRows().add(context.deserialize(row, pcl));
                    }
                } else {
                    for (JsonElement row : rows) {
                        Class<? extends MetaEntity> metaClass = MetaEntity.class;

                        try {
                            Meta.Type metaType = Meta.Type.find(((JsonObject) row).get("meta").getAsJsonObject().get("type").getAsString());
                            metaClass = metaType.getModelClass();
                        } catch (Exception e) {
                            logger.warn("Ошибка во время десериализации массива rows", e);
                        }

                        le.getRows().add(context.deserialize(row, metaClass));
                    }
                }
            }
        }

        return le;
    }
}
