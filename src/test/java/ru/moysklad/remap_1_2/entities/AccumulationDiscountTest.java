package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.entities.discounts.AccumulationDiscount;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AccumulationDiscountTest extends EntityTestBase {

    @Test
    public void accumulationDiscountCrudTest() throws IOException, ApiClientException {
        //get all discount
        ListEntity<AccumulationDiscount> accumulationDiscountList = api.entity().accumulationdiscount().get();
        assertEquals(0, accumulationDiscountList.getRows().size());
        //create one discount
        AccumulationDiscount accumulationDiscount = new AccumulationDiscount();
        String accumulationDiscountName = "accumulationDiscount_" + randomStringTail();
        accumulationDiscount.setName(accumulationDiscountName);
        accumulationDiscount.setActive(false);
        accumulationDiscount.setAgentTags(new ArrayList<>());
        accumulationDiscount.setAllAgents(true);
        accumulationDiscount.setAllProducts(true);
        accumulationDiscount.setAssortment(new ArrayList<>());
        accumulationDiscount.setProductFolders(new ArrayList<>());
        AccumulationDiscount.AccumulationLevel accumulationLevel = new AccumulationDiscount.AccumulationLevel();
        accumulationLevel.setAmount(10.);
        accumulationLevel.setDiscount(5.);
        accumulationDiscount.setLevels(new ArrayList(Arrays.asList(accumulationLevel)));
        accumulationDiscount = api.entity().accumulationdiscount().create(accumulationDiscount);
        assertEquals(accumulationDiscountName, accumulationDiscount.getName());
        assertFalse(accumulationDiscount.getActive());
        assertTrue(accumulationDiscount.getAllAgents());
        assertTrue(accumulationDiscount.getAllProducts());
        assertEquals(0, accumulationDiscount.getAgentTags().size());
        assertEquals(1, accumulationDiscount.getLevels().size());
        assertEquals(10., accumulationDiscount.getLevels().get(0).getAmount(), 0);
        assertEquals(5., accumulationDiscount.getLevels().get(0).getDiscount(), 0);
        //get all discount
        accumulationDiscountList = api.entity().accumulationdiscount().get();
        assertEquals(1, accumulationDiscountList.getRows().size());
        //get one
        accumulationDiscount = api.entity().accumulationdiscount().get(accumulationDiscount.getId());
        assertEquals(accumulationDiscountName, accumulationDiscount.getName());
        //update one
        accumulationDiscount.setName("new");
        api.entity().accumulationdiscount().update(accumulationDiscount.getId(), accumulationDiscount);
        accumulationDiscount = api.entity().accumulationdiscount().get(accumulationDiscount.getId());
        assertEquals("new", accumulationDiscount.getName());
        //delete
        api.entity().accumulationdiscount().delete(accumulationDiscount.getId());
        accumulationDiscountList = api.entity().accumulationdiscount().get();
        assertEquals(0, accumulationDiscountList.getRows().size());
    }
}
