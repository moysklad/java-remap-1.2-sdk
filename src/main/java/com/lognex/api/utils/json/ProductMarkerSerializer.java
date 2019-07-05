package com.lognex.api.utils.json;

import com.google.gson.*;
import com.lognex.api.entities.ConsignmentEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.BundleEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.entities.products.ServiceEntity;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.entities.products.markers.ProductMarker;

import java.lang.reflect.Type;

/**
 * Cериализатор классов-наследников интерфейса <code>ProductMarker</code>. В зависимости от метаданных,
 * возвращает экземпляр одного из классов: Product, Service, Bundle, Variant
 */
public class ProductMarkerSerializer implements JsonSerializer<ProductMarker>, JsonDeserializer<ProductMarker> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public JsonElement serialize(ProductMarker src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    @Override
    public ProductMarker deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException("Can't parse field 'product': meta is null");
        if (me.getMeta().getType() == null)
            throw new JsonParseException("Can't parse field 'product': meta.type is null");

        switch (me.getMeta().getType()) {
            case PRODUCT:
                return context.deserialize(json, ProductEntity.class);

            case SERVICE:
                return context.deserialize(json, ServiceEntity.class);

            case BUNDLE:
                return context.deserialize(json, BundleEntity.class);

            case VARIANT:
                return context.deserialize(json, VariantEntity.class);

            case CONSIGNMENT:
                return context.deserialize(json, ConsignmentEntity.class);

            default:
                throw new JsonParseException("Can't parse field 'product': meta.type must be one of [product, service, bundle, variant, consignment]");
        }
    }
}
