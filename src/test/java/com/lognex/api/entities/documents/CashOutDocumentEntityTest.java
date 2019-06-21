package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.*;
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

public class CashOutDocumentEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CashOutDocumentEntity cashOut = new CashOutDocumentEntity();
        cashOut.setName("cashout_" + randomString(3) + "_" + new Date().getTime());
        cashOut.setDescription(randomString());
        cashOut.setMoment(LocalDateTime.now());
        cashOut.setSum(randomLong(10, 10000));
        cashOut.setOrganization(simpleEntityFactory.getOwnOrganization());
        cashOut.setAgent(simpleEntityFactory.createSimpleCounterparty());
        cashOut.setExpenseItem(simpleEntityFactory.createSimpleExpenseItem());

        api.entity().cashout().post(cashOut);

        ListEntity<CashOutDocumentEntity> updatedEntitiesList = api.entity().cashout().get(filterEq("name", cashOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CashOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(cashOut.getName(), retrievedEntity.getName());
        assertEquals(cashOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(cashOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(cashOut.getSum(), retrievedEntity.getSum());
        assertEquals(cashOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(cashOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(cashOut.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().cashout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        CashOutDocumentEntity cashOut = api.entity().cashout().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(Long.valueOf(0), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);

        assertEquals(cashOut.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(cashOut.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrdersTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = simpleEntityFactory.createSimplePurchaseOrder();

        CashOutDocumentEntity cashOut = api.entity().cashout().newDocument("operations", Collections.singletonList(purchaseOrder));
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
    public void newBySalesReturnsTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity salesReturn = simpleEntityFactory.createSimpleSalesReturn();

        CashOutDocumentEntity cashOut = api.entity().cashout().newDocument("operations", Collections.singletonList(salesReturn));
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
    public void newBySuppliesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = simpleEntityFactory.createSimpleSupply();

        CashOutDocumentEntity cashOut = api.entity().cashout().newDocument("operations", Collections.singletonList(supply));
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
    public void newByInvoicesInTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = simpleEntityFactory.createSimpleInvoiceIn();

        CashOutDocumentEntity cashOut = api.entity().cashout().newDocument("operations", Collections.singletonList(invoiceIn));
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
    public void newByCommissionReportsOutTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = simpleEntityFactory.createSimpleCommissionReportOut();

        CashOutDocumentEntity cashOut = api.entity().cashout().newDocument("operations", Collections.singletonList(commissionReportOut));
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
        CashOutDocumentEntity originalCashOut = (CashOutDocumentEntity) originalEntity;
        CashOutDocumentEntity retrievedCashOut = (CashOutDocumentEntity) retrievedEntity;

        assertEquals(originalCashOut.getName(), retrievedCashOut.getName());
        assertEquals(originalCashOut.getDescription(), retrievedCashOut.getDescription());
        assertEquals(originalCashOut.getOrganization().getMeta().getHref(), retrievedCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalCashOut.getAgent().getMeta().getHref(), retrievedCashOut.getAgent().getMeta().getHref());
        assertEquals(originalCashOut.getExpenseItem().getMeta().getHref(), retrievedCashOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CashOutDocumentEntity originalCashOut = (CashOutDocumentEntity) originalEntity;
        CashOutDocumentEntity updatedCashOut = (CashOutDocumentEntity) updatedEntity;

        assertNotEquals(originalCashOut.getName(), updatedCashOut.getName());
        assertEquals(changedField, updatedCashOut.getName());
        assertEquals(originalCashOut.getDescription(), updatedCashOut.getDescription());
        assertEquals(originalCashOut.getOrganization().getMeta().getHref(), updatedCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalCashOut.getAgent().getMeta().getHref(), updatedCashOut.getAgent().getMeta().getHref());
        assertEquals(originalCashOut.getExpenseItem().getMeta().getHref(), updatedCashOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().cashout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CashOutDocumentEntity.class;
    }
}
