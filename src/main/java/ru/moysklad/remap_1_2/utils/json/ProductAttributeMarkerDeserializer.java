package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProductFolder;
import ru.moysklad.remap_1_2.entities.products.Bundle;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.entities.products.markers.ProductAttributeMarker;

import java.io.IOException;

public class ProductAttributeMarkerDeserializer extends JsonDeserializer<ProductAttributeMarker> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();


    @Override
    public ProductAttributeMarker deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        MetaEntity me = objectMapper.treeToValue(node, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException(p, "Can't parse field 'product': meta is null");
        if (me.getMeta().getType() == null)
            throw new JsonParseException(p, "Can't parse field 'product': meta.type is null");

        switch (me.getMeta().getType()) {
            case PRODUCT_FOLDER:
                return objectMapper.treeToValue(node, ProductFolder.class);

            case PRODUCT:
                return objectMapper.treeToValue(node, Product.class);

            case SERVICE:
                return objectMapper.treeToValue(node, Service.class);

            case BUNDLE:
                return objectMapper.treeToValue(node, Bundle.class);

            case VARIANT:
                return objectMapper.treeToValue(node, Variant.class);


            default:
                throw new JsonParseException(p, "Can't parse field 'product': meta.type must be one of [product, service, bundle, variant, consignment, productfolder]");
        }
    }
}
