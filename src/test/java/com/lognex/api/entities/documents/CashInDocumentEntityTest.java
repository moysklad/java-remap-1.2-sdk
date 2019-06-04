package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CashInDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CashInDocumentEntity e = new CashInDocumentEntity();
        e.setName("cashin_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setMoment(LocalDateTime.now());
        e.setSum(randomLong(10, 10000));
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().cashin().post(e);

        ListEntity<CashInDocumentEntity> updatedEntitiesList = api.entity().cashin().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CashInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CashInDocumentEntity e = createSimpleCashIn();

        CashInDocumentEntity retrievedEntity = api.entity().cashin().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().cashin().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        CashInDocumentEntity e = createSimpleCashIn();

        CashInDocumentEntity retrievedOriginalEntity = api.entity().cashin().get(e.getId());
        String name = "cashin_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().cashin().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "cashin_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().cashin().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CashInDocumentEntity e = createSimpleCashIn();

        ListEntity<CashInDocumentEntity> entitiesList = api.entity().cashin().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().cashin().delete(e.getId());

        entitiesList = api.entity().cashin().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CashInDocumentEntity e = createSimpleCashIn();

        ListEntity<CashInDocumentEntity> entitiesList = api.entity().cashin().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().cashin().delete(e);

        entitiesList = api.entity().cashin().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().cashin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        CashInDocumentEntity e = api.entity().cashin().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);

        assertEquals(e.getOrganization().getMeta().getHref(), getOwnOrganization().getMeta().getHref());
        assertEquals(e.getGroup().getMeta().getHref(), getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = createSimpleCustomerOrder();

        CashInDocumentEntity e = api.entity().cashin().newDocument("operations", Collections.singletonList(customerOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(customerOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(customerOrder.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPurchaseReturnsTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = createSimplePurchaseReturn();

        CashInDocumentEntity e = api.entity().cashin().newDocument("operations", Collections.singletonList(purchaseReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(purchaseReturn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(purchaseReturn.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByDemandsTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = createSimpleDemand();

        CashInDocumentEntity e = api.entity().cashin().newDocument("operations", Collections.singletonList(demand));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(demand.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(demand.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesOutTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = createSimpleInvoiceOut();

        CashInDocumentEntity e = api.entity().cashin().newDocument("operations", Collections.singletonList(invoiceOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(invoiceOut.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(invoiceOut.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceOut.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsInTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = createSimpleCommissionReportIn();

        CashInDocumentEntity e = api.entity().cashin().newDocument("operations", Collections.singletonList(commissionReportIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(commissionReportIn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(commissionReportIn.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportIn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(commissionReportIn.getContract().getMeta().getHref(), e.getContract().getMeta().getHref());
        assertEquals(commissionReportIn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(commissionReportIn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private void getAsserts(CashInDocumentEntity e, CashInDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(CashInDocumentEntity e, CashInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CashInDocumentEntity retrievedUpdatedEntity = api.entity().cashin().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }
}
