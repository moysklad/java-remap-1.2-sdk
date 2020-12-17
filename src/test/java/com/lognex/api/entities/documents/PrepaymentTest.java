package com.lognex.api.entities.documents;

import com.google.gson.Gson;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.Meta;
import com.lognex.api.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PrepaymentTest extends EntityTestBase {

    @Test
    public void deserializePrepayment() {
        Gson gson = ApiClient.createGson();

        Prepayment prepayment = gson.fromJson(
                TestUtils.getFile("documentsJson/prepayment.json"), Prepayment.class
        );

        assertEquals(Meta.Type.PREPAYMENT, prepayment.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/prepayment/7944ef04-f831-11e5-7a69-971500188b19",
                prepayment.getMeta().getHref()
        );
        assertEquals("7944ef04-f831-11e5-7a69-971500188b19", prepayment.getId());
        assertEquals("00004", prepayment.getName());
        assertTrue(prepayment.getApplicable());
        assertFalse(prepayment.getPublished());
        assertFalse(prepayment.getPrinted());
        assertFalse(prepayment.getShared());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/employee/78d0453c-2e92-11e9-ac12-000e0000002f",
                prepayment.getOwner().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/group/783fd0fd-2e92-11e9-ac12-000b00000002",
                prepayment.getGroup().getMeta().getHref()
        );
        assertNotNull(prepayment.getUpdated());
        assertNotNull(prepayment.getMoment());
        assertNotNull(prepayment.getCreated());
        assertEquals("Комментарий", prepayment.getDescription());
        assertEquals("wkOsJvdDguUeVJOB-g1LN1", prepayment.getExternalCode());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/currency/790def39-2e92-11e9-ac12-000e00000061",
                prepayment.getRate().getCurrency().getMeta().getHref()
        );
        assertEquals(Long.valueOf(30000), prepayment.getSum());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/counterparty/790cd2d0-2e92-11e9-ac12-000e0000005f",
                prepayment.getAgent().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/organization/7906d621-2e92-11e9-ac12-000e0000005a",
                prepayment.getOrganization().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/prepayment/7944ef04-f831-11e5-7a69-971500188b19/positions",
                prepayment.getPositions().getMeta().getHref()
        );
        assertTrue(prepayment.getVatEnabled());
        assertTrue(prepayment.getVatIncluded());
        assertEquals(Long.valueOf(5000), prepayment.getVatSum());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/retailstore/79749705-2e92-11e9-ac12-000e00000071",
                prepayment.getRetailStore().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/customerorder/81ff1592-366b-11e9-ac12-000b00000069",
                prepayment.getCustomerOrder().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/retailshift/49734306-366a-11e9-ac12-000b0000002a",
                prepayment.getRetailShift().getMeta().getHref()
        );
        assertEquals(Long.valueOf(15000), prepayment.getCashSum());
        assertEquals(Long.valueOf(15000), prepayment.getNoCashSum());
        assertEquals(Long.valueOf(0), prepayment.getQrSum());
    }
}
