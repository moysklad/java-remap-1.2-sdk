package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.entities.discounts.PersonalDiscount;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PersonalDiscountTest extends EntityTestBase {

    @Test
    public void PersonalDiscountCrudTest() throws IOException, ApiClientException {
        //get all discount
        ListEntity<PersonalDiscount> personalDiscountList = api.entity().personaldiscount().get();
        assertEquals(0, personalDiscountList.getRows().size());
        //create one discount
        PersonalDiscount personalDiscount = new PersonalDiscount();
        personalDiscount.setName("test");
        personalDiscount.setActive(false);
        personalDiscount.setAgentTags(new ArrayList<>());
        personalDiscount.setAllAgents(true);
        personalDiscount.setAllProducts(true);
        personalDiscount.setAssortment(new ArrayList<>());
        personalDiscount.setProductFolders(new ArrayList<>());
        personalDiscount = api.entity().personaldiscount().create(personalDiscount);
        assertEquals("test", personalDiscount.getName());
        assertEquals(false, personalDiscount.getActive());
        assertEquals(true, personalDiscount.getAllAgents());
        assertEquals(true, personalDiscount.getAllProducts());
        assertEquals(0, personalDiscount.getAgentTags().size());
        //get all discount
        personalDiscountList = api.entity().personaldiscount().get();
        assertEquals(1, personalDiscountList.getRows().size());
        //get one
        personalDiscount = api.entity().personaldiscount().get(personalDiscount.getId());
        assertEquals("test", personalDiscount.getName());
        //update one
        personalDiscount.setName("new");
        api.entity().personaldiscount().update(personalDiscount.getId(), personalDiscount);
        personalDiscount = api.entity().personaldiscount().get(personalDiscount.getId());
        assertEquals("new", personalDiscount.getName());
        //delete
        api.entity().personaldiscount().delete(personalDiscount.getId());
        personalDiscountList = api.entity().personaldiscount().get();
        assertEquals(0, personalDiscountList.getRows().size());
    }
}
