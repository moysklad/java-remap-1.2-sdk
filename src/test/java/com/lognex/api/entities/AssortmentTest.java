package com.lognex.api.entities;

import com.lognex.api.entities.products.Product;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.params.OrderParam;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AssortmentTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, ApiClientException {
        ListEntity<Assortment> assortment = api.entity().assortment().get();

        assertNotNull(assortment.getRows());
        Integer countBefore = assortment.getMeta().getSize();

        Product product = simpleEntityManager.createSimpleProduct();

        assortment = api.entity().assortment().get(OrderParam.order("updated", OrderParam.Direction.desc));
        assertEquals(Integer.valueOf(countBefore + 1), assortment.getMeta().getSize());
        assertEquals(product, assortment.getRows().get(0));
    }
}
