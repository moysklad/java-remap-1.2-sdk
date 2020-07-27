package com.lognex.api.entities;

import com.lognex.api.entities.discounts.SpecialPriceDiscount;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpecialPriceDiscountTest extends EntityTestBase {

    @Test
    public void SpecialPriceDiscountCrudTest() throws IOException, ApiClientException {
        //get all discount
        ListEntity<SpecialPriceDiscount> specialPriceDiscountList = api.entity().specialpricediscount().get();
        assertEquals(0, specialPriceDiscountList.getRows().size());
        //create one discount
        SpecialPriceDiscount specialPriceDiscount = new SpecialPriceDiscount();
        specialPriceDiscount.setName("test");
        specialPriceDiscount.setActive(false);
        specialPriceDiscount.setAgentTags(new ArrayList<>());
        specialPriceDiscount.setAllAgents(true);
        specialPriceDiscount.setAllProducts(true);
        specialPriceDiscount.setAssortment(new ArrayList<>());
        specialPriceDiscount.setProductFolders(new ArrayList<>());
        specialPriceDiscount.setDiscount(10.);
        specialPriceDiscount.setUsePriceType(false);
        PriceType priceType = api.entity().pricelist().get().getRows().get(0).getPriceType();
        SpecialPriceDiscount.SpecialPriceData specialPriceData = new SpecialPriceDiscount.SpecialPriceData();
        specialPriceData.setMeta(priceType.getMeta());
        specialPriceDiscount.setSpecialPrice(specialPriceData);
        //check
        specialPriceDiscount = api.entity().specialpricediscount().create(specialPriceDiscount);
        assertEquals("test", specialPriceDiscount.getName());
        assertFalse(specialPriceDiscount.getActive());
        assertTrue(specialPriceDiscount.getAllAgents());
        assertTrue(specialPriceDiscount.getAllProducts());
        assertEquals(0, specialPriceDiscount.getAgentTags().size());
        assertEquals(10., specialPriceDiscount.getDiscount(), 0.1);
        assertFalse(specialPriceDiscount.getUsePriceType());
        //get all discount
        specialPriceDiscountList = api.entity().specialpricediscount().get();
        assertEquals(1, specialPriceDiscountList.getRows().size());
        //get one
        specialPriceDiscount = api.entity().specialpricediscount().get(specialPriceDiscount.getId());
        assertEquals("test", specialPriceDiscount.getName());
        //update one
        specialPriceDiscount.setName("new");
        specialPriceDiscount.setSpecialPrice(specialPriceData);
        api.entity().specialpricediscount().update(specialPriceDiscount.getId(), specialPriceDiscount);
        specialPriceDiscount = api.entity().specialpricediscount().get(specialPriceDiscount.getId());
        assertEquals("new", specialPriceDiscount.getName());
        //delete
        api.entity().specialpricediscount().delete(specialPriceDiscount.getId());
        specialPriceDiscountList = api.entity().specialpricediscount().get();
        assertEquals(0, specialPriceDiscountList.getRows().size());
    }
}
