package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;
import ru.moysklad.remap_1_2.entities.Consignment;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProductFolder;
import ru.moysklad.remap_1_2.entities.products.Bundle;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.entities.products.markers.ProductAttributeMarker;

import java.lang.reflect.Type;

public class ProductAttributeMarkerSerializer implements JsonSerializer<ProductAttributeMarker>, JsonDeserializer<ProductAttributeMarker> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();


    @Override
    public ProductAttributeMarker deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException("Can't parse field 'product': meta is null");
        if (me.getMeta().getType() == null)
            throw new JsonParseException("Can't parse field 'product': meta.type is null");

        switch (me.getMeta().getType()) {
            case PRODUCT_FOLDER:
                return context.deserialize(json, ProductFolder.class);

            case PRODUCT:
                return context.deserialize(json, Product.class);

            case SERVICE:
                return context.deserialize(json, Service.class);

            case BUNDLE:
                return context.deserialize(json, Bundle.class);

            case VARIANT:
                return context.deserialize(json, Variant.class);

            case CONSIGNMENT:
                return context.deserialize(json, Consignment.class);

            default:
                throw new JsonParseException("Can't parse field 'product': meta.type must be one of [product, service, bundle, variant, consignment, productfolder]");
        }
    }

    @Override
    public JsonElement serialize(ProductAttributeMarker resource, Type type, JsonSerializationContext context) {
        return context.serialize(resource);
    }
}
