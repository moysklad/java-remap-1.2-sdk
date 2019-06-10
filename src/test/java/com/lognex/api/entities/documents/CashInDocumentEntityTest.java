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
        CashInDocumentEntity cashIn = new CashInDocumentEntity();
        cashIn.setName("cashin_" + randomString(3) + "_" + new Date().getTime());
        cashIn.setDescription(randomString());
        cashIn.setMoment(LocalDateTime.now());
        cashIn.setSum(randomLong(10, 10000));
        cashIn.setOrganization(simpleEntityFactory.getOwnOrganization());
        cashIn.setAgent(simpleEntityFactory.createSimpleCounterparty());

        api.entity().cashin().post(cashIn);

        ListEntity<CashInDocumentEntity> updatedEntitiesList = api.entity().cashin().get(filterEq("name", cashIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CashInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(cashIn.getName(), retrievedEntity.getName());
        assertEquals(cashIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(cashIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(cashIn.getSum(), retrievedEntity.getSum());
        assertEquals(cashIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(cashIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CashInDocumentEntity cashIn = simpleEntityFactory.createSimpleCashIn();

        CashInDocumentEntity retrievedEntity = api.entity().cashin().get(cashIn.getId());
        getAsserts(cashIn, retrievedEntity);

        retrievedEntity = api.entity().cashin().get(cashIn);
        getAsserts(cashIn, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        CashInDocumentEntity cashIn = simpleEntityFactory.createSimpleCashIn();

        CashInDocumentEntity retrievedOriginalEntity = api.entity().cashin().get(cashIn.getId());
        String name = "cashin_" + randomString(3) + "_" + new Date().getTime();
        cashIn.setName(name);
        api.entity().cashin().put(cashIn.getId(), cashIn);
        putAsserts(cashIn, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(cashIn);

        name = "cashin_" + randomString(3) + "_" + new Date().getTime();
        cashIn.setName(name);
        api.entity().cashin().put(cashIn);
        putAsserts(cashIn, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CashInDocumentEntity cashIn = simpleEntityFactory.createSimpleCashIn();

        ListEntity<CashInDocumentEntity> entitiesList = api.entity().cashin().get(filterEq("name", cashIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().cashin().delete(cashIn.getId());

        entitiesList = api.entity().cashin().get(filterEq("name", cashIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CashInDocumentEntity cashIn = simpleEntityFactory.createSimpleCashIn();

        ListEntity<CashInDocumentEntity> entitiesList = api.entity().cashin().get(filterEq("name", cashIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().cashin().delete(cashIn);

        entitiesList = api.entity().cashin().get(filterEq("name", cashIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().cashin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        CashInDocumentEntity cashIn = api.entity().cashin().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashIn.getName());
        assertEquals(Long.valueOf(0), cashIn.getSum());
        assertFalse(cashIn.getShared());
        assertTrue(cashIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashIn.getMoment()) < 1000);

        assertEquals(cashIn.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(cashIn.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        CashInDocumentEntity cashIn = api.entity().cashin().newDocument("operations", Collections.singletonList(customerOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashIn.getName());
        assertEquals(customerOrder.getSum(), cashIn.getSum());
        assertFalse(cashIn.getShared());
        assertTrue(cashIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashIn.getMoment()) < 1000);
        assertEquals(1, cashIn.getOperations().size());
        assertEquals(customerOrder.getMeta().getHref(), cashIn.getOperations().get(0).getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), cashIn.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), cashIn.getAgent().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), cashIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPurchaseReturnsTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = simpleEntityFactory.createSimplePurchaseReturn();

        CashInDocumentEntity cashIn = api.entity().cashin().newDocument("operations", Collections.singletonList(purchaseReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashIn.getName());
        assertEquals(purchaseReturn.getSum(), cashIn.getSum());
        assertFalse(cashIn.getShared());
        assertTrue(cashIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashIn.getMoment()) < 1000);
        assertEquals(1, cashIn.getOperations().size());
        assertEquals(purchaseReturn.getMeta().getHref(), cashIn.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), cashIn.getGroup().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), cashIn.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), cashIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByDemandsTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = simpleEntityFactory.createSimpleDemand();

        CashInDocumentEntity cashIn = api.entity().cashin().newDocument("operations", Collections.singletonList(demand));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashIn.getName());
        assertEquals(demand.getSum(), cashIn.getSum());
        assertFalse(cashIn.getShared());
        assertTrue(cashIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashIn.getMoment()) < 1000);
        assertEquals(1, cashIn.getOperations().size());
        assertEquals(demand.getMeta().getHref(), cashIn.getOperations().get(0).getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), cashIn.getGroup().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), cashIn.getAgent().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), cashIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesOutTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = simpleEntityFactory.createSimpleInvoiceOut();

        CashInDocumentEntity cashIn = api.entity().cashin().newDocument("operations", Collections.singletonList(invoiceOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashIn.getName());
        assertEquals(invoiceOut.getSum(), cashIn.getSum());
        assertFalse(cashIn.getShared());
        assertTrue(cashIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashIn.getMoment()) < 1000);
        assertEquals(1, cashIn.getOperations().size());
        assertEquals(invoiceOut.getMeta().getHref(), cashIn.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceOut.getGroup().getMeta().getHref(), cashIn.getGroup().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), cashIn.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), cashIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsInTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = simpleEntityFactory.createSimpleCommissionReportIn();

        CashInDocumentEntity cashIn = api.entity().cashin().newDocument("operations", Collections.singletonList(commissionReportIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashIn.getName());
        assertEquals(commissionReportIn.getSum(), cashIn.getSum());
        assertFalse(cashIn.getShared());
        assertTrue(cashIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashIn.getMoment()) < 1000);
        assertEquals(1, cashIn.getOperations().size());
        assertEquals(commissionReportIn.getMeta().getHref(), cashIn.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportIn.getGroup().getMeta().getHref(), cashIn.getGroup().getMeta().getHref());
        assertEquals(commissionReportIn.getContract().getMeta().getHref(), cashIn.getContract().getMeta().getHref());
        assertEquals(commissionReportIn.getAgent().getMeta().getHref(), cashIn.getAgent().getMeta().getHref());
        assertEquals(commissionReportIn.getOrganization().getMeta().getHref(), cashIn.getOrganization().getMeta().getHref());
    }

    private void getAsserts(CashInDocumentEntity cashIn, CashInDocumentEntity retrievedEntity) {
        assertEquals(cashIn.getName(), retrievedEntity.getName());
        assertEquals(cashIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(cashIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(cashIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(CashInDocumentEntity cashIn, CashInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CashInDocumentEntity retrievedUpdatedEntity = api.entity().cashin().get(cashIn.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }
}
