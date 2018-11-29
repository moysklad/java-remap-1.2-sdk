package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.lognex.api.LognexApi;
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
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new AccumulationDiscountEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.accumulationdiscount);

        String data = gson.toJson(e);
        DiscountEntity parsed = gsonCustom.fromJson(data, DiscountEntity.class);
        assertEquals(AccumulationDiscountEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializeBonusProgramDiscount() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new BonusProgramDiscountEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.bonusprogram);

        String data = gson.toJson(e);
        DiscountEntity parsed = gsonCustom.fromJson(data, DiscountEntity.class);
        assertEquals(BonusProgramDiscountEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializeDiscount() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new DiscountEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.discount);

        String data = gson.toJson(e);
        DiscountEntity parsed = gsonCustom.fromJson(data, DiscountEntity.class);
        assertEquals(DiscountEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializePersonalDiscount() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new PersonalDiscountEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.personaldiscount);

        String data = gson.toJson(e);
        DiscountEntity parsed = gsonCustom.fromJson(data, DiscountEntity.class);
        assertEquals(PersonalDiscountEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializeSpecialPriceDiscount() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new SpecialPriceDiscountEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.specialpricediscount);

        String data = gson.toJson(e);
        DiscountEntity parsed = gsonCustom.fromJson(data, DiscountEntity.class);
        assertEquals(SpecialPriceDiscountEntity.class, parsed.getClass());
    }

    @Test
    public void test_deserializeWithoutMeta() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new DiscountEntity();

        String data = gson.toJson(e);

        try {
            gsonCustom.fromJson(data, DiscountEntity.class);
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
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new DiscountEntity();
        e.setMeta(new Meta());

        String data = gson.toJson(e);

        try {
            gsonCustom.fromJson(data, DiscountEntity.class);
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
        Gson gsonCustom = LognexApi.createGson();

        DiscountEntity e = new DiscountEntity();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.product);

        String data = gson.toJson(e);

        try {
            gsonCustom.fromJson(data, DiscountEntity.class);
            fail("Ожидалось исключение JsonParseException!");
        } catch (JsonParseException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Can't parse field 'discount': meta.type must be one of [accumulationdiscount, bonusprogram, discount, personaldiscount, specialpricediscount]"
            );
        }
    }
}
