package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;

import java.io.IOException;

public class DocumentEntityDeserializer extends JsonDeserializer<DocumentEntity> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();



    @Override
    public DocumentEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        MetaEntity me = objectMapper.treeToValue(node, MetaEntity.class);

        if (me.getMeta() == null) {
            throw new JsonParseException(p, "Can't parse field 'operation': meta is null");
        } else if (me.getMeta().getType() == null) {
            throw new JsonParseException(p, "Can't parse field 'operation': meta.type is null");
        }

        if (!DocumentEntity.class.isAssignableFrom(me.getMeta().getType().getModelClass())) {
            throw new JsonParseException(p, "Can't parse field 'operation': meta.type is not a valid document type");
        }

        return (DocumentEntity) p.getCodec().treeToValue(node, me.getMeta().getType().getModelClass());
    }
}
