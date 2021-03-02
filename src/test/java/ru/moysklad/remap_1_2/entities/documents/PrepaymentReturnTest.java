package ru.moysklad.remap_1_2.entities.documents;

import com.google.gson.Gson;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.EntityTestBase;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.utils.TestUtils;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PrepaymentReturnTest extends EntityTestBase {

    @Test
    public void deserializePrepaymentReturn() {
        Gson gson = ApiClient.createGson();

        PrepaymentReturn prepaymentReturn = gson.fromJson(
                TestUtils.getFile("documentsJson/prepaymentreturn.json"), PrepaymentReturn.class
        );

        assertEquals(Meta.Type.PREPAYMENT_RETURN, prepaymentReturn.getMeta().getType());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/prepaymentreturn/7944ef04-f831-11e5-7a69-971500188b19",
                prepaymentReturn.getMeta().getHref()
        );
        assertEquals("7944ef04-f831-11e5-7a69-971500188b19", prepaymentReturn.getId());
        assertEquals("00002", prepaymentReturn.getName());
        assertTrue(prepaymentReturn.getApplicable());
        assertFalse(prepaymentReturn.getPublished());
        assertFalse(prepaymentReturn.getPrinted());
        assertFalse(prepaymentReturn.getShared());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/employee/78d0453c-2e92-11e9-ac12-000e0000002f",
                prepaymentReturn.getOwner().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/group/783fd0fd-2e92-11e9-ac12-000b00000002",
                prepaymentReturn.getGroup().getMeta().getHref()
        );
        assertNotNull(prepaymentReturn.getUpdated());
        assertNotNull(prepaymentReturn.getMoment());
        assertNotNull(prepaymentReturn.getCreated());
        assertEquals("Комментарий", prepaymentReturn.getDescription());
        assertEquals("Avamj0cIjGYE6P500XlBD1", prepaymentReturn.getExternalCode());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/currency/790def39-2e92-11e9-ac12-000e00000061",
                prepaymentReturn.getRate().getCurrency().getMeta().getHref()
        );
        assertEquals(Long.valueOf(30000), prepaymentReturn.getSum());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/counterparty/790cd2d0-2e92-11e9-ac12-000e0000005f",
                prepaymentReturn.getAgent().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/organization/7906d621-2e92-11e9-ac12-000e0000005a",
                prepaymentReturn.getOrganization().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/prepaymentreturn/7944ef04-f831-11e5-7a69-971500188b19/positions",
                prepaymentReturn.getPositions().getMeta().getHref()
        );
        assertTrue(prepaymentReturn.getVatEnabled());
        assertTrue(prepaymentReturn.getVatIncluded());
        assertEquals(Long.valueOf(5000), prepaymentReturn.getVatSum());
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/retailstore/79749705-2e92-11e9-ac12-000e00000071",
                prepaymentReturn.getRetailStore().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/prepayment/857c7a16-366b-11e9-ac12-000b00000070",
                prepaymentReturn.getPrepayment().getMeta().getHref()
        );
        assertEquals("https://online.moysklad.ru/api/remap/1.2/entity/retailshift/f1e3aa91-366b-11e9-ac12-000b0000007e",
                prepaymentReturn.getRetailShift().getMeta().getHref()
        );
        assertEquals(Long.valueOf(15000), prepaymentReturn.getCashSum());
        assertEquals(Long.valueOf(15000), prepaymentReturn.getNoCashSum());
        assertEquals(Long.valueOf(0), prepaymentReturn.getQrSum());
    }
}
