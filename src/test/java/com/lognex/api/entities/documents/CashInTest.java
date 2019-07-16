package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
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

public class CashInTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CashIn cashIn = new CashIn();
        cashIn.setName("cashin_" + randomString(3) + "_" + new Date().getTime());
        cashIn.setDescription(randomString());
        cashIn.setMoment(LocalDateTime.now());
        cashIn.setSum(randomLong(10, 10000));
        cashIn.setOrganization(simpleEntityManager.getOwnOrganization());
        cashIn.setAgent(simpleEntityManager.createSimpleCounterparty());

        api.entity().cashin().create(cashIn);

        ListEntity<CashIn> updatedEntitiesList = api.entity().cashin().get(filterEq("name", cashIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CashIn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(cashIn.getName(), retrievedEntity.getName());
        assertEquals(cashIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(cashIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(cashIn.getSum(), retrievedEntity.getSum());
        assertEquals(cashIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(cashIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().cashin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        CashIn cashIn = api.entity().cashin().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashIn.getName());
        assertEquals(Long.valueOf(0), cashIn.getSum());
        assertFalse(cashIn.getShared());
        assertTrue(cashIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashIn.getMoment()) < 1000);

        assertEquals(cashIn.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(cashIn.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrder customerOrder = simpleEntityManager.createSimple(CustomerOrder.class);

        CashIn cashIn = api.entity().cashin().templateDocument("operations", Collections.singletonList(customerOrder));
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
        PurchaseReturn purchaseReturn = simpleEntityManager.createSimple(PurchaseReturn.class);

        CashIn cashIn = api.entity().cashin().templateDocument("operations", Collections.singletonList(purchaseReturn));
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
        Demand demand = simpleEntityManager.createSimple(Demand.class);

        CashIn cashIn = api.entity().cashin().templateDocument("operations", Collections.singletonList(demand));
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
        InvoiceOut invoiceOut = simpleEntityManager.createSimple(InvoiceOut.class);

        CashIn cashIn = api.entity().cashin().templateDocument("operations", Collections.singletonList(invoiceOut));
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
        CommissionReportIn commissionReportIn = simpleEntityManager.createSimple(CommissionReportIn.class);

        CashIn cashIn = api.entity().cashin().templateDocument("operations", Collections.singletonList(commissionReportIn));
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

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CashIn originalCashIn = (CashIn) originalEntity;
        CashIn retrievedCashIn = (CashIn) retrievedEntity;

        assertEquals(originalCashIn.getName(), retrievedCashIn.getName());
        assertEquals(originalCashIn.getDescription(), retrievedCashIn.getDescription());
        assertEquals(originalCashIn.getOrganization().getMeta().getHref(), retrievedCashIn.getOrganization().getMeta().getHref());
        assertEquals(originalCashIn.getAgent().getMeta().getHref(), retrievedCashIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CashIn originalCashIn = (CashIn) originalEntity;
        CashIn updatedCashIn = (CashIn) updatedEntity;

        assertNotEquals(originalCashIn.getName(), updatedCashIn.getName());
        assertEquals(changedField, updatedCashIn.getName());
        assertEquals(originalCashIn.getDescription(), updatedCashIn.getDescription());
        assertEquals(originalCashIn.getOrganization().getMeta().getHref(), updatedCashIn.getOrganization().getMeta().getHref());
        assertEquals(originalCashIn.getAgent().getMeta().getHref(), updatedCashIn.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().cashin();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CashIn.class;
    }
}
