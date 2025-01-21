package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.discounts.*;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DiscountTest extends EntityTestBase {
    @Test
    public void getDiscountTest() throws IOException, ApiClientException {
        ListEntity<Discount> discountList = api.entity().discount().get();
        assertEquals(1, discountList.getRows().size());
        assertEquals(Meta.Type.DISCOUNT, discountList.getRows().get(0).getMeta().getType());
        assertEquals("Округление копеек", discountList.getRows().get(0).getName());
    }

    @Test
    public void deserializeSpecialPriceDiscountWithDiscountTest() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        SpecialPriceDiscount specialPriceDiscount = objectMapper.readValue(
                TestUtils.getFile("discountJson/specialdiscount.json"), SpecialPriceDiscount.class
        );

        assertEquals(Meta.Type.SPECIAL_PRICE_DISCOUNT, specialPriceDiscount.getMeta().getType());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/specialpricediscount/96673f4d-9f4d-11e6-8af5-581e0000007b",
                specialPriceDiscount.getMeta().getHref()
        );
        assertEquals("96673f4d-9f4d-11e6-8af5-581e0000007b", specialPriceDiscount.getId());
        assertEquals("Специальная процентная сидка", specialPriceDiscount.getName());
        assertTrue(specialPriceDiscount.getActive());
        assertEquals(1, specialPriceDiscount.getAgentTags().size());
        assertEquals("группа агентов", specialPriceDiscount.getAgentTags().get(0));
        assertFalse(specialPriceDiscount.getAllProducts());
        assertEquals(1, specialPriceDiscount.getAssortment().size());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/variant/9881531b-9a09-11e6-8af5-581e00000078",
                ((Variant) specialPriceDiscount.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals((Double) 5.0, specialPriceDiscount.getDiscount());
    }

    @Test
    public void deserializeSpecialPriceDiscountWithSpecialPriceTest() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        SpecialPriceDiscount specialPriceDiscount = objectMapper.readValue(
                TestUtils.getFile("discountJson/specialprice.json"), SpecialPriceDiscount.class
        );

        assertEquals(Meta.Type.SPECIAL_PRICE_DISCOUNT, specialPriceDiscount.getMeta().getType());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/specialpricediscount/bd1235f2-9c60-11e6-8af5-581e00000009",
                specialPriceDiscount.getMeta().getHref()
        );
        assertEquals("bd1235f2-9c60-11e6-8af5-581e00000009", specialPriceDiscount.getId());
        assertEquals("Скидка номер 2", specialPriceDiscount.getName());
        assertTrue(specialPriceDiscount.getActive());
        assertFalse(specialPriceDiscount.getAllProducts());
        assertEquals(1, specialPriceDiscount.getAssortment().size());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/variant/9881531b-9a09-11e6-8af5-581e00000078",
                ((Variant) specialPriceDiscount.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals(Long.valueOf(15), specialPriceDiscount.getSpecialPrice().getValue());
        assertEquals("http://localhost/api/remap/1.2/context/companysettings/pricetype/80684824-82e0-11e9-ac17-000c00000066",
                specialPriceDiscount.getSpecialPrice().getPriceType().getMeta().getHref()
        );
        assertEquals("80684824-82e0-11e9-ac17-000c00000066", specialPriceDiscount.getSpecialPrice().getPriceType().getId());
        assertEquals("Цена продажи", specialPriceDiscount.getSpecialPrice().getPriceType().getName());
        assertEquals("cbcf493b-55bc-11d9-848a-00112f43529a", specialPriceDiscount.getSpecialPrice().getPriceType().getExternalCode());
    }

    @Test
    public void deserializePersonalDiscountTest() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        PersonalDiscount personalDiscount = objectMapper.readValue(
                TestUtils.getFile("discountJson/personaldiscount.json"), PersonalDiscount.class
        );

        assertEquals(Meta.Type.PERSONAL_DISCOUNT, personalDiscount.getMeta().getType());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/personaldiscount/0623d6b4-9ceb-11e6-8af5-581e00000003",
                personalDiscount.getMeta().getHref()
        );
        assertEquals("0623d6b4-9ceb-11e6-8af5-581e00000003", personalDiscount.getId());
        assertEquals("Персональная скидка", personalDiscount.getName());
        assertTrue(personalDiscount.getActive());
        assertTrue(personalDiscount.getAllProducts());
    }

    @Test
    public void deserializeAccumulationDiscountTest() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        AccumulationDiscount accumulationDiscount = objectMapper.readValue(
                TestUtils.getFile("discountJson/accumulationdiscount.json"), AccumulationDiscount.class
        );

        assertEquals(Meta.Type.ACCUMULATION_DISCOUNT, accumulationDiscount.getMeta().getType());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/accumulationdiscount/dce08f7f-9a09-11e6-8af5-581e0000007e",
                accumulationDiscount.getMeta().getHref()
        );
        assertEquals("dce08f7f-9a09-11e6-8af5-581e0000007e", accumulationDiscount.getId());
        assertEquals("Скидки на сапоги", accumulationDiscount.getName());
        assertTrue(accumulationDiscount.getActive());
        assertFalse(accumulationDiscount.getAllProducts());
        assertEquals(1, accumulationDiscount.getAssortment().size());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/variant/9881531b-9a09-11e6-8af5-581e00000078",
                ((Variant) accumulationDiscount.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals(2, accumulationDiscount.getLevels().size());
        assertEquals(Double.valueOf(100000.0), accumulationDiscount.getLevels().get(0).getAmount());
        assertEquals(Double.valueOf(10.0), accumulationDiscount.getLevels().get(0).getDiscount());
        assertEquals(Double.valueOf(200000.0), accumulationDiscount.getLevels().get(1).getAmount());
        assertEquals(Double.valueOf(15.0), accumulationDiscount.getLevels().get(1).getDiscount());
    }

    @Test
    public void deserializeBonusProgramDiscountTest() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        BonusProgram bonusProgram = objectMapper.readValue(
                TestUtils.getFile("discountJson/bonusprogram.json"), BonusProgram.class
        );

        assertEquals(Meta.Type.BONUS_PROGRAM, bonusProgram.getMeta().getType());
        assertEquals("https://api.moysklad.ru/api/remap/1.1/entity/bonusprogram/96673f4d-9f4d-11e6-8af5-581e0000007b",
                bonusProgram.getMeta().getHref()
        );
        assertEquals("96673f4d-9f4d-11e6-8af5-581e0000007b", bonusProgram.getId());
        assertEquals("Бонусная программа", bonusProgram.getName());
        assertTrue(bonusProgram.getActive());
        assertEquals(Integer.valueOf(1), bonusProgram.getEarnRateRoublesToPoint());
        assertEquals(Integer.valueOf(10), bonusProgram.getSpendRatePointsToRouble());
        assertEquals(Integer.valueOf(100), bonusProgram.getMaxPaidRatePercents());
        assertEquals(Integer.valueOf(7), bonusProgram.getPostponedBonusesDelayDays());
    }
}
