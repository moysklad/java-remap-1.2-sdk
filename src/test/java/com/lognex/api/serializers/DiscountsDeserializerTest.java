package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.discounts.*;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DiscountsDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserializeAccumulationDiscount() {
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        Discount e = new BonusProgramDiscount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.BONUS_PROGRAM);

        String data = gsonCustom.toJson(e);
        Discount parsed = gsonCustom.fromJson(data, Discount.class);
        assertEquals(BonusProgramDiscount.class, parsed.getClass());
    }

    @Test
    public void test_deserializeDiscount() {
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
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
        Gson gson = new GsonBuilder().create();
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
