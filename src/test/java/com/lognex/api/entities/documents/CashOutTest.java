package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.documents.DocumentMetadataClient;
import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CashOutTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CashOut cashOut = new CashOut();
        cashOut.setName("cashout_" + randomString(3) + "_" + new Date().getTime());
        cashOut.setDescription(randomString());
        cashOut.setMoment(LocalDateTime.now());
        cashOut.setSum(randomLong(10, 10000));
        cashOut.setOrganization(simpleEntityManager.getOwnOrganization());
        cashOut.setAgent(simpleEntityManager.createSimpleCounterparty());
        cashOut.setExpenseItem(simpleEntityManager.createSimpleExpenseItem());

        api.entity().cashout().create(cashOut);

        ListEntity<CashOut> updatedEntitiesList = api.entity().cashout().get(filterEq("name", cashOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CashOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(cashOut.getName(), retrievedEntity.getName());
        assertEquals(cashOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(cashOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(cashOut.getSum(), retrievedEntity.getSum());
        assertEquals(cashOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(cashOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(cashOut.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        DocumentMetadataClient<MetadataAttributeSharedStatesResponse> metadata = api.entity().cashout().metadata();
        MetadataAttributeSharedStatesResponse response = metadata.get();

        assertFalse(response.getCreateShared());

        ListEntity<Attribute> attributes = metadata.attributes();
        assertNotNull(attributes);
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        CashOut cashOut = api.entity().cashout().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(Long.valueOf(0), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);

        assertEquals(cashOut.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(cashOut.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrdersTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = simpleEntityManager.createSimple(PurchaseOrder.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(purchaseOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(purchaseOrder.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(purchaseOrder.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnsTest() throws IOException, ApiClientException {
        SalesReturn salesReturn = simpleEntityManager.createSimple(SalesReturn.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(salesReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(salesReturn.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(salesReturn.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySuppliesTest() throws IOException, ApiClientException {
        Supply supply = simpleEntityManager.createSimple(Supply.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(supply));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(supply.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(supply.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = simpleEntityManager.createSimple(InvoiceIn.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(invoiceIn.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(invoiceIn.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsOutTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = simpleEntityManager.createSimple(CommissionReportOut.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(commissionReportOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(commissionReportOut.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(commissionReportOut.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportOut.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), cashOut.getContract().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CashOut originalCashOut = (CashOut) originalEntity;
        CashOut retrievedCashOut = (CashOut) retrievedEntity;

        assertEquals(originalCashOut.getName(), retrievedCashOut.getName());
        assertEquals(originalCashOut.getDescription(), retrievedCashOut.getDescription());
        assertEquals(originalCashOut.getOrganization().getMeta().getHref(), retrievedCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalCashOut.getAgent().getMeta().getHref(), retrievedCashOut.getAgent().getMeta().getHref());
        assertEquals(originalCashOut.getExpenseItem().getMeta().getHref(), retrievedCashOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CashOut originalCashOut = (CashOut) originalEntity;
        CashOut updatedCashOut = (CashOut) updatedEntity;

        assertNotEquals(originalCashOut.getName(), updatedCashOut.getName());
        assertEquals(changedField, updatedCashOut.getName());
        assertEquals(originalCashOut.getDescription(), updatedCashOut.getDescription());
        assertEquals(originalCashOut.getOrganization().getMeta().getHref(), updatedCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalCashOut.getAgent().getMeta().getHref(), updatedCashOut.getAgent().getMeta().getHref());
        assertEquals(originalCashOut.getExpenseItem().getMeta().getHref(), updatedCashOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().cashout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CashOut.class;
    }
}
