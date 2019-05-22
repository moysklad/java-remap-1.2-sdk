package com.lognex.api.entities;

import com.lognex.api.entities.discounts.DiscountEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DiscountEntityTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, LognexApiException {
        ListEntity<DiscountEntity> discountList = api.entity().discount().get();
        assertEquals(1, discountList.getRows().size());
        assertEquals(Meta.Type.discount, discountList.getRows().get(0).getMeta().getType());
        assertEquals("Округление копеек", discountList.getRows().get(0).getName());
    }
}
