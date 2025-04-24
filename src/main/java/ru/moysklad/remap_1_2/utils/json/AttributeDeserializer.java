package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.products.markers.ProductAttributeMarker;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.moysklad.remap_1_2.utils.Constants.DATE_FORMAT_PATTERN;

public class AttributeDeserializer extends JsonDeserializer<Attribute> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();
    private final DateTimeFormatter formatter;

    public AttributeDeserializer() {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    }



    @Override
    public Attribute deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = objectMapper.readTree(p);

        if (!node.has("type"))
            throw new IllegalArgumentException("В пришедшей сущности дополнительного параметра нет поля 'type'!");

        String attrType = node.get("type").asText();
        try {
            Meta.Type t = Meta.Type.find(attrType);
            ((ObjectNode) node).remove("type");
            ((ObjectNode) node).put("entityType", t.getApiName());
        } catch (IllegalArgumentException ignored) {
        }

        Attribute ae = objectMapper.treeToValue(node, Attribute.class);

        if (ae.getType() != null && ae.getValue() != null) {
            switch (ae.getType()) {
                case longValue:
                    ae.setValue(((Integer)ae.getValue()).longValue());
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
                            p.getCodec().treeToValue(node.get("value"), Agent.class)
                    );
                    break;

                case PRODUCT:
                case BUNDLE:
                case SERVICE:
                    ae.setValue(
                            p.getCodec().treeToValue(node.get("value"), ProductAttributeMarker.class)
                    );
                    break;

                case CONTRACT:
                    ae.setValue(
                            p.getCodec().treeToValue(node.get("value"), Contract.class)
                    );
                    break;

                case PROJECT:
                    ae.setValue(
                            p.getCodec().treeToValue(node.get("value"), Project.class)
                    );
                    break;

                case STORE:
                    ae.setValue(
                            p.getCodec().treeToValue(node.get("value"), Store.class)
                    );
                    break;

                case CUSTOM_ENTITY:
                    CustomEntityElement customEntity = p.getCodec().treeToValue(node.get("value"), CustomEntityElement.class);
                    if (customEntity != null) {
                        customEntity.setCustomDictionaryId(MetaHrefUtils.getCustomDictionaryIdFromHref(customEntity.getMeta().getHref()));
                    }
                    ae.setValue(customEntity);
                    break;
            }
        }

        return ae;
    }
}
