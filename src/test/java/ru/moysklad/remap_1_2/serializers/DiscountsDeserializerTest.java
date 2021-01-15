package ru.moysklad.remap_1_2.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.discounts.*;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DiscountsDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserializeAccumulationDiscount() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new AccumulationDiscount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.ACCUMULATION_DISCOUNT);

        String data = gsonCustom.toJson(e);
        Discount parsed = gsonCustom.fromJson(data, Discount.class);
        assertEquals(AccumulationDiscount.class, parsed.getClass());
    }

    @Test
    public void test_deserializeBonusProgramDiscount() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new BonusProgram();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.BONUS_PROGRAM);

        String data = gsonCustom.toJson(e);
        Discount parsed = gsonCustom.fromJson(data, Discount.class);
        assertEquals(BonusProgram.class, parsed.getClass());
    }

    @Test
    public void test_deserializeDiscount() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new Discount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.DISCOUNT);

        String data = gsonCustom.toJson(e);
        Discount parsed = gsonCustom.fromJson(data, Discount.class);
        assertEquals(Discount.class, parsed.getClass());
    }

    @Test
    public void test_deserializePersonalDiscount() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new PersonalDiscount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.PERSONAL_DISCOUNT);

        String data = gsonCustom.toJson(e);
        Discount parsed = gsonCustom.fromJson(data, Discount.class);
        assertEquals(PersonalDiscount.class, parsed.getClass());
    }

    @Test
    public void test_deserializeSpecialPriceDiscount() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new SpecialPriceDiscount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.SPECIAL_PRICE_DISCOUNT);

        String data = gsonCustom.toJson(e);
        Discount parsed = gsonCustom.fromJson(data, Discount.class);
        assertEquals(SpecialPriceDiscount.class, parsed.getClass());
    }

    @Test
    public void test_deserializeWithoutMeta() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new Discount();

        String data = gsonCustom.toJson(e);

        try {
            gsonCustom.fromJson(data, Discount.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'discount': meta is null"
            );
        }
    }

    @Test
    public void test_deserializeWithoutMetaType() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new Discount();
        e.setMeta(new Meta());

        String data = gsonCustom.toJson(e);

        try {
            gsonCustom.fromJson(data, Discount.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'discount': meta.type is null"
            );
        }
    }

    @Test
    public void test_deserializeWithIncorrectMetaType() {
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new Discount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.PRODUCT);

        String data = gsonCustom.toJson(e);

        try {
            gsonCustom.fromJson(data, Discount.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'discount': meta.type must be one of [accumulationdiscount, bonusprogram, discount, personaldiscount, specialpricediscount]"
            );
        }
    }
}
