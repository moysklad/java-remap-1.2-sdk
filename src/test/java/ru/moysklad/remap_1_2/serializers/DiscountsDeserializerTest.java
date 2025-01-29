package ru.moysklad.remap_1_2.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void test_deserializeAccumulationDiscount() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new AccumulationDiscount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.ACCUMULATION_DISCOUNT);

        String data = objectMapper.writeValueAsString(e);
        Discount parsed = objectMapper.readValue(data, Discount.class);
        assertEquals(AccumulationDiscount.class, parsed.getClass());
    }

    @Test
    public void test_deserializeBonusProgramDiscount() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new BonusProgram();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.BONUS_PROGRAM);

        String data = objectMapper.writeValueAsString (e);
        Discount parsed = objectMapper.readValue(data, Discount.class);
        assertEquals(BonusProgram.class, parsed.getClass());
    }

    @Test
    public void test_deserializeDiscount() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new Discount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.DISCOUNT);

        String data = objectMapper.writeValueAsString (e);
        Discount parsed = objectMapper.readValue(data, Discount.class);
        assertEquals(Discount.class, parsed.getClass());
    }

    @Test
    public void test_deserializePersonalDiscount() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new PersonalDiscount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.PERSONAL_DISCOUNT);

        String data = objectMapper.writeValueAsString (e);
        Discount parsed = objectMapper.readValue(data, Discount.class);
        assertEquals(PersonalDiscount.class, parsed.getClass());
    }

    @Test
    public void test_deserializeSpecialPriceDiscount() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new SpecialPriceDiscount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.SPECIAL_PRICE_DISCOUNT);

        String data = objectMapper.writeValueAsString (e);
        Discount parsed = objectMapper.readValue(data, Discount.class);
        assertEquals(SpecialPriceDiscount.class, parsed.getClass());
    }

    @Test
    public void test_deserializeWithoutMeta() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new Discount();

        String data = objectMapper.writeValueAsString (e);

        try {
            objectMapper.readValue(data, Discount.class);
            fail("Ожидалось исключение JsonProcessingException!");
        } catch (JsonProcessingException ex) {
            assertEquals(
                    ex.getOriginalMessage(),
                    "Can't parse field 'discount': meta is null"
            );
        }
    }

    @Test
    public void test_deserializeWithoutMetaType() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new Discount();
        e.setMeta(new Meta());

        String data = objectMapper.writeValueAsString (e);

        try {
            objectMapper.readValue(data, Discount.class);
            fail("Ожидалось исключение JsonProcessingException!");
        } catch (JsonProcessingException ex) {
            assertEquals(
                    ex.getOriginalMessage(),
                    "Can't parse field 'discount': meta.type is null"
            );
        }
    }

    @Test
    public void test_deserializeWithIncorrectMetaType() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Discount e = new Discount();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.PRODUCT);

        String data = objectMapper.writeValueAsString (e);

        try {
            objectMapper.readValue(data, Discount.class);
            fail("Ожидалось исключение JsonProcessingException!");
        } catch (JsonProcessingException ex) {
            assertEquals(
                    ex.getOriginalMessage(),
                    "Can't parse field 'discount': meta.type must be one of [accumulationdiscount, bonusprogram, discount, personaldiscount, specialpricediscount, roundoffdiscount]"
            );
        }
    }
}
