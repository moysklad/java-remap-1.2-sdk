package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.discounts.*;

import java.lang.reflect.Type;

/**
 * Десериализатор поля <code>discount</code>. В зависимости от метаданных, возвращает экземпляр
 * одного из классов, наследующихся от Discount: AccumulationDiscount,
 * BonusProgram, PersonalDiscount, SpecialPriceDiscount, RoundOffDiscount
 * или сам Discount
 */
public class DiscountDeserializer implements JsonDeserializer<Discount> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public Discount deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException("Can't parse field 'discount': meta is null");
        if (me.getMeta().getType() == null)
            throw new JsonParseException("Can't parse field 'discount': meta.type is null");

        switch (me.getMeta().getType()) {
            case ACCUMULATION_DISCOUNT:
                return context.deserialize(json, AccumulationDiscount.class);

            case BONUS_PROGRAM:
                return context.deserialize(json, BonusProgram.class);

            case DISCOUNT:
                return gson.fromJson(json, Discount.class);

            case PERSONAL_DISCOUNT:
                return context.deserialize(json, PersonalDiscount.class);

            case SPECIAL_PRICE_DISCOUNT:
                return context.deserialize(json, SpecialPriceDiscount.class);

            case ROUND_OFF_DISCOUNT:
                return context.deserialize(json, RoundOffDiscount.class);

            default:
                throw new JsonParseException("Can't parse field 'discount': meta.type must be one of [accumulationdiscount, bonusprogram, discount, personaldiscount, specialpricediscount, roundoffdiscount]");
        }
    }
}
