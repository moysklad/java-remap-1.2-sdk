package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;
import ru.moysklad.remap_1_2.entities.CustomEntity;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.responses.metadata.CompanySettingsMetadata.CustomEntityMetadata;

import java.lang.reflect.Type;

/**
 * Десериализатор элементов поля <code>customEntities</code>. Возвращает объект CustomEntityMetadata с заполненными
 * метаданными CustomEntityMetadata.entityMeta (href, id, name, uuidHref, type, mediaType)
 */
public class CustomEntityMetadataDeserializer implements JsonDeserializer<CustomEntityMetadata> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public CustomEntityMetadata deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        CustomEntityMetadata cem = new CustomEntityMetadata();

        JsonObject jo = json.getAsJsonObject();

        cem.setMeta(gson.fromJson(jo.get("meta"), Meta.class));
        String name = jo.get("name").getAsString();
        cem.setName(name);
        cem.setCreateShared(jo.get("createShared").getAsBoolean());

        CustomEntity entityMeta = new CustomEntity();
        entityMeta.setName(name);
        entityMeta.setMeta(gson.fromJson(jo.get("entityMeta"), Meta.class));
        if (entityMeta.getMeta().getHref() == null) {
            throw new JsonParseException("Can't parse field 'entityMeta': href is null");
        }

        String[] hrefSplit = entityMeta.getMeta().getHref().split("/");
        entityMeta.setId(hrefSplit[hrefSplit.length - 1]);
        cem.setEntityMeta(entityMeta);

        return cem;
    }
}
