package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;
import ru.moysklad.remap_1_2.entities.Consignment;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Bundle;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.entities.products.markers.ProductMarker;

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
                throw new JsonParseException("Can't parse field 'product': meta.type must be one of [product, service, bundle, variant, consignment]");
        }
    }
}
