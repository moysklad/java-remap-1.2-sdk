package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.entities.products.markers.ConsignmentParentMarker;

import java.io.IOException;

/**
 * Cериализатор классов-наследников интерфейса <code>ConsignmentParentMarker</code>. В зависимости от метаданных,
 * возвращает экземпляр одного из классов: Product, Service, Bundle, Variant
 */
public class ConsignmentParentMarkerDeserializer extends JsonDeserializer<ConsignmentParentMarker> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();

    @Override
    public ConsignmentParentMarker deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = objectMapper.readTree(p);
        MetaEntity me = objectMapper.treeToValue(node, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException(p, "Can't parse field 'product': meta is null");
        if (me.getMeta().getType() == null)
            throw new JsonParseException(p, "Can't parse field 'product': meta.type is null");

        switch (me.getMeta().getType()) {
            case PRODUCT:
                return objectMapper.treeToValue(node, Product.class);

            case VARIANT:
                return objectMapper.treeToValue(node, Variant.class);

            default:
                throw new JsonParseException(p, "Can't parse field 'product': meta.type must be one of [product, service, bundle, variant, consignment]");
        }
    }
}
