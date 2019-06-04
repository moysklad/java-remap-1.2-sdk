package com.lognex.api.entities;

import com.google.gson.Gson;
import com.lognex.api.LognexApi;
import com.lognex.api.entities.discounts.AccumulationDiscountEntity;
import com.lognex.api.entities.discounts.DiscountEntity;
import com.lognex.api.entities.discounts.PersonalDiscountEntity;
import com.lognex.api.entities.discounts.SpecialPriceDiscountEntity;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DiscountEntityTest extends EntityTestBase {
    @Test
    public void getDiscountTest() throws IOException, LognexApiException {
        ListEntity<DiscountEntity> discountList = api.entity().discount().get();
        assertEquals(1, discountList.getRows().size());
        assertEquals(Meta.Type.discount, discountList.getRows().get(0).getMeta().getType());
        assertEquals("Округление копеек", discountList.getRows().get(0).getName());
    }

    @Test
    public void deserializeSpecialPriceDiscountWithDiscountTest() {
        Gson gson = LognexApi.createGson();

        SpecialPriceDiscountEntity e = gson.fromJson(TestUtils.getFile("discountJson/specialdiscount.json"), SpecialPriceDiscountEntity.class);

        assertEquals(Meta.Type.specialpricediscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.1/entity/specialpricediscount/96673f4d-9f4d-11e6-8af5-581e0000007b", e.getMeta().getHref());
        assertEquals("96673f4d-9f4d-11e6-8af5-581e0000007b", e.getId());
        assertEquals("Специальная процентная сидка", e.getName());
        assertTrue(e.getActive());
        assertEquals(1, e.getAgentTags().size());
        assertEquals("группа агентов", e.getAgentTags().get(0));
        assertFalse(e.getAllProducts());
        assertEquals(1, e.getAssortment().size());
        assertEquals("https://online.moysklad.ru/api/remap/1.1/entity/variant/9881531b-9a09-11e6-8af5-581e00000078",
                ((VariantEntity) e.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals((Double) 5.0, e.getDiscount());
    }

    @Test
    public void deserializeSpecialPriceDiscountWithSpecialPriceTest() {
        Gson gson = LognexApi.createGson();

        SpecialPriceDiscountEntity e = gson.fromJson(TestUtils.getFile("discountJson/specialprice.json"), SpecialPriceDiscountEntity.class);

        assertEquals(Meta.Type.specialpricediscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.1/entity/specialpricediscount/bd1235f2-9c60-11e6-8af5-581e00000009", e.getMeta().getHref());
        assertEquals("bd1235f2-9c60-11e6-8af5-581e00000009", e.getId());
        assertEquals("Скидка номер 2", e.getName());
        assertTrue(e.getActive());
        assertFalse(e.getAllProducts());
        assertEquals(1, e.getAssortment().size());
        assertEquals("https://online.moysklad.ru/api/remap/1.1/entity/variant/9881531b-9a09-11e6-8af5-581e00000078",
                ((VariantEntity) e.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals(Long.valueOf(15), e.getSpecialPrice().getValue());
        assertEquals("http://localhost/api/remap/1.2/context/companysettings/pricetype/80684824-82e0-11e9-ac17-000c00000066", e.getSpecialPrice().getPriceType().getMeta().getHref());
        assertEquals("80684824-82e0-11e9-ac17-000c00000066", e.getSpecialPrice().getPriceType().getId());
        assertEquals("Цена продажи", e.getSpecialPrice().getPriceType().getName());
        assertEquals("cbcf493b-55bc-11d9-848a-00112f43529a", e.getSpecialPrice().getPriceType().getExternalCode());
    }

    @Test
    public void deserializePersonalDiscountTest() {
        Gson gson = LognexApi.createGson();

        PersonalDiscountEntity e = gson.fromJson(TestUtils.getFile("discountJson/personaldiscount.json"), PersonalDiscountEntity.class);

        assertEquals(Meta.Type.personaldiscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.1/entity/personaldiscount/0623d6b4-9ceb-11e6-8af5-581e00000003", e.getMeta().getHref());
        assertEquals("0623d6b4-9ceb-11e6-8af5-581e00000003", e.getId());
        assertEquals("Персональная скидка", e.getName());
        assertTrue(e.getActive());
        assertTrue(e.getAllProducts());
    }

    @Test
    public void deserializeAccumulationDiscountTest() {
        Gson gson = LognexApi.createGson();

        AccumulationDiscountEntity e = gson.fromJson(TestUtils.getFile("discountJson/accumulationdiscount.json"), AccumulationDiscountEntity.class);

        assertEquals(Meta.Type.accumulationdiscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.1/entity/accumulationdiscount/dce08f7f-9a09-11e6-8af5-581e0000007e", e.getMeta().getHref());
        assertEquals("dce08f7f-9a09-11e6-8af5-581e0000007e", e.getId());
        assertEquals("Скидки на сапоги", e.getName());
        assertTrue(e.getActive());
        assertFalse(e.getAllProducts());
        assertEquals(1, e.getAssortment().size());
        assertEquals("https://online.moysklad.ru/api/remap/1.1/entity/variant/9881531b-9a09-11e6-8af5-581e00000078",
                ((VariantEntity) e.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals(2, e.getLevels().size());
        assertEquals(Long.valueOf(100000), e.getLevels().get(0).getAmount());
        assertEquals(Integer.valueOf(10), e.getLevels().get(0).getDiscount());
        assertEquals(Long.valueOf(200000), e.getLevels().get(1).getAmount());
        assertEquals(Integer.valueOf(15), e.getLevels().get(1).getDiscount());
    }
}
