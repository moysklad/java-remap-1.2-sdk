package com.lognex.api.entities;

import com.google.gson.Gson;
import com.lognex.api.LognexApi;
import com.lognex.api.entities.discounts.AccumulationDiscountEntity;
import com.lognex.api.entities.discounts.AccumulationDiscountEntity.AccumulationLevel;
import com.lognex.api.entities.discounts.DiscountEntity;
import com.lognex.api.entities.discounts.PersonalDiscountEntity;
import com.lognex.api.entities.discounts.SpecialPriceDiscountEntity;
import com.lognex.api.entities.discounts.SpecialPriceDiscountEntity.SpecialPriceData;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String id = randomString();
        String name = randomString();
        Boolean active = true;
        String agentTag = randomString();
        Boolean allProducts = false;
        String variantId = randomString();
        Double discount = randomDouble(10, 1, 2);

        String specialPriceJson = "{\n" +
                "      \"meta\": {\n" +
                "        \"href\": \"https://online.moysklad.ru/api/remap/1.2/entity/specialpricediscount/" + id + "\",\n" +
                "        \"metadataHref\": \"https://online.moysklad.ru/api/remap/1.2/entity/specialpricediscount/metadata\",\n" +
                "        \"type\": \"specialpricediscount\",\n" +
                "        \"mediaType\": \"application/json\"\n" +
                "      },\n" +
                "      \"id\": \"" + id + "\",\n" +
                "      \"accountId\": \"9560e3e3-9609-11e6-8af5-581e00000008\",\n" +
                "      \"name\": \"" + name + "\",\n" +
                "      \"active\": " + active.toString() + ",\n" +
                "      \"agentTags\": [\n" +
                "        \"" + agentTag + "\"\n" +
                "      ],\n" +
                "      \"allProducts\": " + allProducts.toString() + ",\n" +
                "      \"assortment\": [\n" +
                "        {\n" +
                "          \"meta\": {\n" +
                "            \"href\": \"https://online.moysklad.ru/api/remap/1.2/entity/variant/" + variantId + "\",\n" +
                "            \"metadataHref\": \"https://online.moysklad.ru/api/remap/1.2/entity/variant/metadata\",\n" +
                "            \"type\": \"variant\",\n" +
                "            \"mediaType\": \"application/json\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"discount\": " + discount + "\n" +
                "    }";

        Gson gson = LognexApi.createGson();

        SpecialPriceDiscountEntity e = gson.fromJson(specialPriceJson, SpecialPriceDiscountEntity.class);

        assertEquals(Meta.Type.specialpricediscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/specialpricediscount/" + id, e.getMeta().getHref());
        assertEquals(id, e.getId());
        assertEquals(name, e.getName());
        assertEquals(active, e.getActive());
        assertEquals(1, e.getAgentTags().size());
        assertEquals(agentTag, e.getAgentTags().get(0));
        assertEquals(allProducts, e.getAllProducts());
        assertEquals(1, e.getAssortment().size());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/variant/" + variantId,
                ((VariantEntity) e.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals(discount, e.getDiscount());
    }

    @Test
    public void deserializeSpecialPriceDiscountWithSpecialPriceTest() {
        String id = randomString();
        String name = randomString();
        Boolean active = true;
        String agentTag = randomString();
        Boolean allProducts = false;
        String variantId = randomString();
        SpecialPriceData specialPrice = new SpecialPriceData();
        specialPrice.setValue(randomLong(1, 100));
        specialPrice.setPriceType(new PriceTypeEntity());
        specialPrice.getPriceType().setMeta(new Meta());
        specialPrice.getPriceType().setId(randomString());
        specialPrice.getPriceType().getMeta().setHref("https://online.moysklad.ru/api/remap/1.2/context/companysettings/pricetype/" + specialPrice.getPriceType().getId());
        specialPrice.getPriceType().setName(randomString());
        specialPrice.getPriceType().setExternalCode(randomString());

        String specialPriceJson = "{\n" +
                "      \"meta\": {\n" +
                "        \"href\": \"https://online.moysklad.ru/api/remap/1.2/entity/specialpricediscount/" + id + "\",\n" +
                "        \"metadataHref\": \"https://online.moysklad.ru/api/remap/1.2/entity/specialpricediscount/metadata\",\n" +
                "        \"type\": \"specialpricediscount\",\n" +
                "        \"mediaType\": \"application/json\"\n" +
                "      },\n" +
                "      \"id\": \"" + id + "\",\n" +
                "      \"accountId\": \"9560e3e3-9609-11e6-8af5-581e00000008\",\n" +
                "      \"name\": \"" + name + "\",\n" +
                "      \"active\": " + active.toString() + ",\n" +
                "      \"agentTags\": [\n" +
                "        \"" + agentTag + "\"\n" +
                "      ],\n" +
                "      \"allProducts\": " + allProducts.toString() + ",\n" +
                "      \"assortment\": [\n" +
                "        {\n" +
                "          \"meta\": {\n" +
                "            \"href\": \"https://online.moysklad.ru/api/remap/1.2/entity/variant/" + variantId + "\",\n" +
                "            \"metadataHref\": \"https://online.moysklad.ru/api/remap/1.2/entity/variant/metadata\",\n" +
                "            \"type\": \"variant\",\n" +
                "            \"mediaType\": \"application/json\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"specialPrice\": {\n" +
                "        \"value\": " + specialPrice.getValue() + ",\n" +
                "        \"priceType\": {\n" +
                "          \"meta\": {\n" +
                "            \"href\": \"" + specialPrice.getPriceType().getMeta().getHref() + "\",\n" +
                "            \"type\": \"pricetype\",\n" +
                "            \"mediaType\": \"application/json\"\n" +
                "          },\n" +
                "          \"id\": \"" + specialPrice.getPriceType().getId() + "\",\n" +
                "          \"name\": \"" + specialPrice.getPriceType().getName() + "\",\n" +
                "          \"externalCode\": \"" + specialPrice.getPriceType().getExternalCode() + "\"\n" +
                "        }\n" +
                "      }" +
                "    }";

        Gson gson = LognexApi.createGson();

        SpecialPriceDiscountEntity e = gson.fromJson(specialPriceJson, SpecialPriceDiscountEntity.class);

        assertEquals(Meta.Type.specialpricediscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/specialpricediscount/" + id, e.getMeta().getHref());
        assertEquals(id, e.getId());
        assertEquals(name, e.getName());
        assertEquals(active, e.getActive());
        assertEquals(1, e.getAgentTags().size());
        assertEquals(agentTag, e.getAgentTags().get(0));
        assertEquals(allProducts, e.getAllProducts());
        assertEquals(1, e.getAssortment().size());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/variant/" + variantId,
                ((VariantEntity) e.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals(specialPrice.getValue(), e.getSpecialPrice().getValue());
        assertEquals(specialPrice.getPriceType().getMeta().getHref(), e.getSpecialPrice().getPriceType().getMeta().getHref());
        assertEquals(specialPrice.getPriceType().getId(), e.getSpecialPrice().getPriceType().getId());
        assertEquals(specialPrice.getPriceType().getName(), e.getSpecialPrice().getPriceType().getName());
        assertEquals(specialPrice.getPriceType().getExternalCode(), e.getSpecialPrice().getPriceType().getExternalCode());
    }

    @Test
    public void deserializePersonalDiscountTest() {
        String id = randomString();
        String name = randomString();
        Boolean active = true;
        Boolean allProducts = true;

        String personalDiscountJson = "{\n" +
                "      \"meta\": {\n" +
                "        \"href\": \"https://online.moysklad.ru/api/remap/1.2/entity/personaldiscount/" + id + "\",\n" +
                "        \"metadataHref\": \"https://online.moysklad.ru/api/remap/1.2/entity/personaldiscount/metadata\",\n" +
                "        \"type\": \"personaldiscount\",\n" +
                "        \"mediaType\": \"application/json\"\n" +
                "      },\n" +
                "      \"id\": \"" + id + "\",\n" +
                "      \"accountId\": \"9560e3e3-9609-11e6-8af5-581e00000008\",\n" +
                "      \"name\": \"" + name + "\",\n" +
                "      \"active\": " + active.toString() + ",\n" +
                "      \"allProducts\": " + allProducts.toString() + "\n" +
                "    }";

        Gson gson = LognexApi.createGson();

        PersonalDiscountEntity e = gson.fromJson(personalDiscountJson, PersonalDiscountEntity.class);

        assertEquals(Meta.Type.personaldiscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/personaldiscount/" + id, e.getMeta().getHref());
        assertEquals(id, e.getId());
        assertEquals(name, e.getName());
        assertEquals(active, e.getActive());
        assertEquals(allProducts, e.getAllProducts());
    }

    @Test
    public void deserializeAccumulationDiscountTest() {
        String id = randomString();
        String name = randomString();
        Boolean active = true;
        Boolean allProducts = false;
        String variantId = randomString();
        List<AccumulationLevel> levels = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            AccumulationLevel level = new AccumulationLevel();
            level.setAmount(randomLong(1, 100));
            level.setDiscount(randomInteger(1, 100));
            levels.add(level);
        }

        String accumulationDiscountJson = "{\n" +
                "      \"meta\": {\n" +
                "        \"href\": \"https://online.moysklad.ru/api/remap/1.2/entity/accumulationdiscount/" + id + "\",\n" +
                "        \"metadataHref\": \"https://online.moysklad.ru/api/remap/1.2/entity/accumulationdiscount/metadata\",\n" +
                "        \"type\": \"accumulationdiscount\",\n" +
                "        \"mediaType\": \"application/json\"\n" +
                "      },\n" +
                "      \"id\": \"" + id + "\",\n" +
                "      \"accountId\": \"9560e3e3-9609-11e6-8af5-581e00000008\",\n" +
                "      \"name\": \"" + name + "\",\n" +
                "      \"active\": " + active.toString() + ",\n" +
                "      \"allProducts\": " + allProducts.toString() + ",\n" +
                "      \"assortment\": [\n" +
                "        {\n" +
                "          \"meta\": {\n" +
                "            \"href\": \"https://online.moysklad.ru/api/remap/1.2/entity/variant/" + variantId + "\",\n" +
                "            \"metadataHref\": \"https://online.moysklad.ru/api/remap/1.2/entity/variant/metadata\",\n" +
                "            \"type\": \"variant\",\n" +
                "            \"mediaType\": \"application/json\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"levels\": [\n" +
                "        {\n" +
                "          \"amount\": " + levels.get(0).getAmount() + ",\n" +
                "          \"discount\": " + levels.get(0).getDiscount() + "\n" +
                "        },\n" +
                "        {\n" +
                "          \"amount\": " + levels.get(1).getAmount() + ",\n" +
                "          \"discount\": " + levels.get(1).getDiscount() + "\n" +
                "        }\n" +
                "      ]" +
                "    }";

        Gson gson = LognexApi.createGson();

        AccumulationDiscountEntity e = gson.fromJson(accumulationDiscountJson, AccumulationDiscountEntity.class);

        assertEquals(Meta.Type.accumulationdiscount, e.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/accumulationdiscount/" + id, e.getMeta().getHref());
        assertEquals(id, e.getId());
        assertEquals(name, e.getName());
        assertEquals(active, e.getActive());
        assertEquals(allProducts, e.getAllProducts());
        assertEquals(1, e.getAssortment().size());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/variant/" + variantId,
                ((VariantEntity) e.getAssortment().get(0)).getMeta().getHref()
        );
        assertEquals(2, e.getLevels().size());
        assertEquals(levels.get(0).getAmount(), e.getLevels().get(0).getAmount());
        assertEquals(levels.get(0).getDiscount(), e.getLevels().get(0).getDiscount());
        assertEquals(levels.get(1).getAmount(), e.getLevels().get(1).getAmount());
        assertEquals(levels.get(1).getDiscount(), e.getLevels().get(1).getDiscount());
    }
}
