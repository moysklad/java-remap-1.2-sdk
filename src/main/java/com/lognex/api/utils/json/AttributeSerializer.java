package com.lognex.api.utils.json;

import com.google.gson.*;
import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.products.markers.ProductMarker;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.lognex.api.utils.Constants.DATE_FORMAT_PATTERN;

public class AttributeSerializer implements JsonSerializer<AttributeEntity>, JsonDeserializer<AttributeEntity> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();
    private final DateTimeFormatter formatter;

    public AttributeSerializer() {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    }

    @Override
    public JsonElement serialize(AttributeEntity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement je = null;

        if (src.getType() != null) {
            switch (src.getType()) {
                case timeValue:
                    if (src.getValue() instanceof LocalDateTime) {
                        src.setValue(context.serialize(((LocalDateTime) src.getValue()).format(formatter)));
                    } else if (src.getValue() instanceof LocalDate) {
                        src.setValue(context.serialize(((LocalDate) src.getValue()).format(formatter)));
                    } else {
                        throw new IllegalArgumentException("Неподдерживаемый тип данных для дополнительного поля с типом 'time': " + src.getValue().getClass().getSimpleName());
                    }
                    break;
            }
            je = gson.toJsonTree(src, AttributeEntity.class);
        } else if (src.getEntityType() != null) {
            je = gson.toJsonTree(src, AttributeEntity.class).getAsJsonObject();
            JsonObject jo = (JsonObject) je;
            jo.add("type", jo.get("entityType"));
            jo.remove("entityType");
        }

        return je;
    }



    @Override
    public AttributeEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = gson.toJsonTree(json).getAsJsonObject();

        if (!jo.has("type"))
            throw new IllegalArgumentException("В пришедшей сущности дополнительного параметра нет поля 'type'!");

        String attrType = jo.get("type").getAsString();

        try {
            Meta.Type t = Meta.Type.find(attrType);
            jo.remove("type");
            jo.add("entityType", new JsonPrimitive(t.getApiName()));
        } catch (IllegalArgumentException ignored) {
        }

        AttributeEntity ae = gson.fromJson(jo, AttributeEntity.class);

        if (ae.getType() != null) {
            switch (ae.getType()) {
                case longValue:
                    ae.setValue(((Double) ae.getValue()).longValue());
                    break;

                case timeValue:
                    ae.setValue(LocalDateTime.parse(String.valueOf(ae.getValue()), formatter));
                    break;
            }
        } else if (ae.getEntityType() != null) {
            switch (ae.getEntityType()) {
                case COUNTERPARTY:
                case ORGANIZATION:
                case EMPLOYEE:
                    ae.setValue(
                            context.deserialize(jo.get("value"), AgentEntity.class)
                    );
                    break;

                case PRODUCT:
                case BUNDLE:
                case SERVICE:
                    ae.setValue(
                            context.deserialize(jo.get("value"), ProductMarker.class)
                    );
                    break;

                case CONTRACT:
                    ae.setValue(
                            context.deserialize(jo.get("value"), ContractEntity.class)
                    );
                    break;

                case PROJECT:
                    ae.setValue(
                            context.deserialize(jo.get("value"), ProjectEntity.class)
                    );
                    break;

                case STORE:
                    ae.setValue(
                            context.deserialize(jo.get("value"), StoreEntity.class)
                    );
                    break;

                case CUSTOM_ENTITY:
                    ae.setValue(
                            context.deserialize(jo.get("value"), CustomEntity.class)
                    );
                    break;
            }
        }

        return ae;
    }
}
