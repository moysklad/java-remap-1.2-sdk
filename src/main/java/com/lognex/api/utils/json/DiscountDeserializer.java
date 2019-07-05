package com.lognex.api.utils.json;

import com.google.gson.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.*;

import java.lang.reflect.Type;

/**
 * Десериализатор поля <code>discount</code>. В зависимости от метаданных, возвращает экземпляр
 * одного из классов, наследующихся от DiscountEntity: AccumulationDiscountEntity,
 * BonusProgramDiscountEntity, PersonalDiscountEntity, SpecialPriceDiscountEntity,
 * или сам DiscountEntity
 */
public class DiscountDeserializer implements JsonDeserializer<DiscountEntity> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public DiscountEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException("Can't parse field 'discount': meta is null");
        if (me.getMeta().getType() == null)
            throw new JsonParseException("Can't parse field 'discount': meta.type is null");

        switch (me.getMeta().getType()) {
            case ACCUMULATION_DISCOUNT:
                return context.deserialize(json, AccumulationDiscountEntity.class);

            case BONUS_PROGRAM:
                return context.deserialize(json, BonusProgramDiscountEntity.class);

            case DISCOUNT:
                return gson.fromJson(json, DiscountEntity.class);

            case PERSONAL_DISCOUNT:
                return context.deserialize(json, PersonalDiscountEntity.class);

            case SPECIAL_PRICE_DISCOUNT:
                return context.deserialize(json, SpecialPriceDiscountEntity.class);

            default:
                throw new JsonParseException("Can't parse field 'discount': meta.type must be one of [accumulationdiscount, bonusprogram, discount, personaldiscount, specialpricediscount]");
        }
    }
}
