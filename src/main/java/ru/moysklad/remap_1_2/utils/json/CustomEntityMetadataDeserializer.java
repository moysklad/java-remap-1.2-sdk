package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.entities.CustomEntity;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.responses.metadata.CompanySettingsMetadata.CustomEntityMetadata;

import java.io.IOException;

/**
 * Десериализатор элементов поля <code>customEntities</code>. Возвращает объект CustomEntityMetadata с заполненными
 * метаданными CustomEntityMetadata.entityMeta (href, id, name, uuidHref, type, mediaType)
 */
public class CustomEntityMetadataDeserializer extends JsonDeserializer<CustomEntityMetadata> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();

    @Override
    public CustomEntityMetadata deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        CustomEntityMetadata cem = new CustomEntityMetadata();

        JsonNode node = p.getCodec().readTree(p);

        cem.setMeta(objectMapper.treeToValue(node.get("meta"), Meta.class));
        String name = node.get("name").asText();
        cem.setName(name);
        cem.setCreateShared(node.get("createShared").asBoolean());

        CustomEntity entityMeta = new CustomEntity();
        entityMeta.setName(name);
        entityMeta.setMeta(objectMapper.treeToValue(node.get("entityMeta"), Meta.class));
        if (entityMeta.getMeta().getHref() == null) {
            throw new JsonParseException(p, "Can't parse field 'entityMeta': href is null");
        }

        String[] hrefSplit = entityMeta.getMeta().getHref().split("/");
        entityMeta.setId(hrefSplit[hrefSplit.length - 1]);
        cem.setEntityMeta(entityMeta);

        return cem;
    }
}
