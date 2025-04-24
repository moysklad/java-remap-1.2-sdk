package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.moysklad.remap_1_2.entities.Context;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.io.IOException;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
public class ListEntityDeserializer extends JsonDeserializer<ListEntity> implements ContextualDeserializer {
    private static final Logger logger = LoggerFactory.getLogger(ListEntityDeserializer.class);

    private JavaType type;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) {
        if (beanProperty != null) {
            JavaType wrapperType = beanProperty.getType();
            JavaType valueType = wrapperType.containedType(0);
            return new ListEntityDeserializer(valueType);
        }
        return this;
    }

    @Override
    public ListEntity deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        ListEntity le = new ListEntity();

        le.setMeta(jp.getCodec().treeToValue(node.get("meta"), Meta.class));
        le.setContext(jp.getCodec().treeToValue(node.get("context"), Context.class));

        JsonNode rows = node.get("rows");
        if (rows != null && rows.isArray()) {
            le.setRows(new ArrayList<>());

            if (type != null) {
                for (JsonNode row : rows) {
                    le.getRows().add(((ObjectMapper)jp.getCodec()).readValue(row.toString(), type));
                }
            } else {
                for (JsonNode row : rows) {
                    try {
                        JsonNode metaNode = row.get("meta");
                        String metaType = metaNode != null ? metaNode.get("type").asText() : null;

                        Class<? extends MetaEntity> metaClass = MetaEntity.class;
                        if (metaType != null) {
                            Meta.Type metaEnum = Meta.Type.find(metaType);
                            metaClass = metaEnum.getModelClass();
                        }

                        le.getRows().add(jp.getCodec().treeToValue(row, metaClass));
                    } catch (Exception e) {
                        logger.warn("Ошибка во время десериализации элемента массива rows", e);
                    }
                }
            }
        }

        return le;
    }
}
